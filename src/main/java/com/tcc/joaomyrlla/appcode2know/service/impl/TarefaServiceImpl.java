package com.tcc.joaomyrlla.appcode2know.service.impl;

import com.tcc.joaomyrlla.appcode2know.dto.SubmissaoDTO;
import com.tcc.joaomyrlla.appcode2know.dto.TarefaDTO;
import com.tcc.joaomyrlla.appcode2know.exceptions.*;
import com.tcc.joaomyrlla.appcode2know.model.Problema;
import com.tcc.joaomyrlla.appcode2know.model.Tarefa;
import com.tcc.joaomyrlla.appcode2know.model.Turma;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.repository.ProblemaRepository;
import com.tcc.joaomyrlla.appcode2know.repository.TarefaRepository;
import com.tcc.joaomyrlla.appcode2know.repository.TurmaRepository;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;
import com.tcc.joaomyrlla.appcode2know.service.ISubmissaoService;
import com.tcc.joaomyrlla.appcode2know.service.ITarefaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TarefaServiceImpl implements ITarefaService {

    @Autowired
    TarefaRepository tarefaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TurmaRepository turmaRepository;

    @Autowired
    ProblemaRepository problemaRepository;

    @Autowired
    ISubmissaoService submissaoService;

    @Override
    public Map<String, List<TarefaDTO>> findByAluno(Long alunoId) {
        Usuario usuario = usuarioRepository.findById(alunoId).orElseThrow(UsuarioNotFoundException::new);

        List<Tarefa> listaTarefas = new ArrayList<>();

        List<TarefaDTO> provas = new ArrayList<>();

        List<TarefaDTO> roteiros = new ArrayList<>();

        usuario.getTurmasAluno().forEach(turma -> roteiros.addAll(turma.getTarefas().stream().filter(tarefa -> !tarefa.isEhProva()).map(TarefaDTO::toTarefaDTO).toList()));

        usuario.getTurmasAluno().forEach(turma -> provas.addAll(turma.getTarefas().stream().filter(Tarefa::isEhProva).map(TarefaDTO::toTarefaDTO).toList()));



        Map<String, List<TarefaDTO>> tarefas = new HashMap<>();
        tarefas.put("provas", provas);
        tarefas.put("roteiros", roteiros);

        return tarefas;
    }

    @Override
    public Map<String, List<TarefaDTO>> findByTurma(Long turmaId) {
        Turma turma = turmaRepository.findById(turmaId).orElseThrow(TurmaNotFoundException::new);

        List<TarefaDTO> provas = turma.getTarefas().stream()
                .filter(Tarefa::isEhProva)
                .map(TarefaDTO::toTarefaDTO)
                .toList();

        List<TarefaDTO> roteiros = turma.getTarefas().stream()
                .filter(tarefa -> !tarefa.isEhProva())
                .map(TarefaDTO::toTarefaDTO)
                .toList();

        Map<String, List<TarefaDTO>> tarefas = new HashMap<>();
        tarefas.put("provas", provas);
        tarefas.put("roteiros", roteiros);

        return tarefas;
    }

    @Override
    @Transactional
    public TarefaDTO add(TarefaDTO tarefaDTO) {
        Usuario usuario = usuarioRepository.findById(tarefaDTO.getCriadorId()).orElseThrow(UsuarioNotFoundException::new);
        Turma turma = turmaRepository.findById(tarefaDTO.getTurmaId()).orElseThrow(TurmaNotFoundException::new);

        if (!usuario.isEhProfessor()) {
            throw new InsufficientPrivilegeException("O usuário não tem permissão para adicionar tarefa");
        }

        Tarefa tarefa = Tarefa.toTarefa(tarefaDTO);
        turma.getTarefas().add(tarefa);
        tarefa.setTurma(turma);
        tarefa.setCriador(usuario);

        tarefaRepository.save(tarefa);
        turmaRepository.save(turma);

        tarefaDTO.setId(tarefa.getId());

        return tarefaDTO;
    }

    @Override
    public TarefaDTO edit(TarefaDTO tarefaDTO, Long professorId) {
        Usuario usuario = usuarioRepository.findById(professorId).orElseThrow(UsuarioNotFoundException::new);
        Turma turma = turmaRepository.findById(tarefaDTO.getTurmaId()).orElseThrow(TurmaNotFoundException::new);
        Tarefa tarefa = tarefaRepository.findById(tarefaDTO.getId()).orElseThrow(TarefaNotFoundException::new);

        if (!(usuario.isEhProfessor()) || !(tarefaDTO.getCriadorId().equals(professorId))) {
            throw new RuntimeException("O usuário não tem permissão para editar a tarefa");
        }

        Tarefa tarefaEditada = Tarefa.toTarefa(tarefaDTO);
        tarefaEditada.setTurma(turma);
        tarefaEditada.setCriador(usuario);

        List<Problema> problemas = tarefaDTO.getProblemas().stream().map(problemaId -> problemaRepository.findById(problemaId).orElseThrow(ProblemaNotFoundException::new)).toList();

        tarefaEditada.setProblemas(problemas);

        tarefaRepository.save(tarefaEditada);

        return tarefaDTO;
    }

    @Override
    @Transactional
    public void delete(Long id, Long professorId) {
        Usuario usuario = usuarioRepository.findById(professorId).orElseThrow(UsuarioNotFoundException::new);
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(TarefaNotFoundException::new);
        Turma turma = turmaRepository.findById(tarefa.getTurma().getId()).orElseThrow(TurmaNotFoundException::new);


        if (!(usuario.isEhProfessor()) || !(tarefa.getCriador().getId().equals(professorId))) {
            throw new InsufficientPrivilegeException("O usuário não tem permissão para deletar a tarefa");
        }

        turma.getTarefas().remove(tarefa);
        tarefaRepository.deleteById(id);
    }

    @Override
    public void addProblemaEmTarefa(Long problemaId, Long tarefaId, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNotFoundException::new);
        Tarefa tarefa = tarefaRepository.findById(tarefaId).orElseThrow(TarefaNotFoundException::new);
        Problema problema = problemaRepository.findById(problemaId).orElseThrow(ProblemaNotFoundException::new);

        if (!(usuario.isEhProfessor()) || !(tarefa.getCriador().getId().equals(usuarioId))) {
            throw new InsufficientPrivilegeException("O usuário não tem permissão para adicionar a tarefa");
        }

        if (!tarefa.getProblemas().contains(problema)){
            tarefa.getProblemas().add(problema);
        }

        tarefaRepository.save(tarefa);
    }

    @Override
    public void removerProblemaEmTarefa(Long problemaId, Long tarefaId, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNotFoundException::new);
        Tarefa tarefa = tarefaRepository.findById(tarefaId).orElseThrow(TarefaNotFoundException::new);
        Problema problema = problemaRepository.findById(problemaId).orElseThrow(ProblemaNotFoundException::new);

        if (!(usuario.isEhProfessor()) || !(tarefa.getCriador().getId().equals(usuarioId))) {
            throw new InsufficientPrivilegeException("O usuário não tem permissão para deletar a tarefa");
        }

        tarefa.getProblemas().remove(problema);
        tarefaRepository.save(tarefa);
    }

    @Override
    public TarefaDTO findById(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(InstituicaoNotFoundException::new);

        return TarefaDTO.toTarefaDTO(tarefa);
    }

    public String statusTarefa(Long tarefaId, Long usuarioId) {
        Tarefa tarefa = tarefaRepository.findById(tarefaId).orElseThrow(TarefaNotFoundException::new);
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNotFoundException::new);


        Set<Long> problemasNaLista = tarefa.getProblemas().stream().map(Problema::getId).collect(Collectors.toSet());

        Set<Long> problemasResolvidos = submissaoService.findByAluno(usuarioId).stream()
                .filter(submissaoDTO -> (submissaoDTO.getStatus().equals("OK") && problemasNaLista.contains(submissaoDTO.getProblemaId())))
                .map(SubmissaoDTO::getProblemaId)
                .collect(Collectors.toSet());

        return String.format("%d/%d", problemasResolvidos.size(), problemasNaLista.size());
    }
}

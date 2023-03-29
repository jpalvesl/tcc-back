package com.tcc.joaomyrlla.appcode2know.serviceImpl;

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
import com.tcc.joaomyrlla.appcode2know.service.ITarefaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<TarefaDTO> findByAluno(Long alunoId) {
        Usuario usuario = usuarioRepository.findById(alunoId).orElseThrow(UsuarioNotFoundException::new);

        List<Tarefa> listaTarefas = new ArrayList<>();

        usuario.getTurmasAluno().forEach(turma -> listaTarefas.addAll(turma.getTarefas()));

        return listaTarefas.stream()
                .map(TarefaDTO::toTarefaDTO)
                .toList();
    }

    @Override
    public List<TarefaDTO> findByTurma(Long turmaId) {
        Turma turma = turmaRepository.findById(turmaId).orElseThrow(TurmaNotFoundException::new);

        return turma.getTarefas().stream()
                .map(TarefaDTO::toTarefaDTO)
                .toList();
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

        if (!(usuario.isEhProfessor()) || !(tarefaDTO.getCriadorId().equals(professorId))) {
            throw new RuntimeException("O usuário não tem permissão para editar a tarefa");
        }

        Tarefa tarefa = Tarefa.toTarefa(tarefaDTO);
        tarefa.setTurma(turma);
        tarefa.setCriador(usuario);

        tarefaRepository.save(tarefa);

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
            throw new InsufficientPrivilegeException("O usuário não tem permissão para deletar a tarefa");
        }

        tarefa.getProblemas().add(problema);
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
}

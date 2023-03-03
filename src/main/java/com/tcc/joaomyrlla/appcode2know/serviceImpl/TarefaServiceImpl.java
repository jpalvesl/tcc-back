package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.dto.TarefaDTO;
import com.tcc.joaomyrlla.appcode2know.model.Tarefa;
import com.tcc.joaomyrlla.appcode2know.model.Turma;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
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
import java.util.Optional;

@Service
public class TarefaServiceImpl implements ITarefaService {

    @Autowired
    TarefaRepository tarefaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TurmaRepository turmaRepository;

    @Override
    public List<TarefaDTO> findByAluno(Long alunoId) {
        Optional<Usuario> usuario = usuarioRepository.findById(alunoId);

        List<Tarefa> listaTarefas = new ArrayList<>();

        List<Turma> listTurma = usuario.get().getTurmasAluno();
        listTurma.forEach(turma -> {
            listaTarefas.addAll(turma.getTarefas());
        });

        return listaTarefas.stream()
                .map(tarefa -> {
                    TarefaDTO tarefaDto = new TarefaDTO();
                    BeanUtils.copyProperties(tarefa, tarefaDto);

                    tarefaDto.setDtAbertura((Date) tarefa.getDtAbertura());
                    tarefaDto.setDtEncerramento((Date) tarefa.getDtEncerramento());
                    tarefaDto.setTurmaId(tarefa.getTurma().getId());
                    tarefaDto.setCriadorId(tarefa.getCriador().getId());

                    return tarefaDto;
                })
                .toList();

    }

    @Override
    public List<TarefaDTO> findByTurma(Long turmaId) {

        Optional<Turma> turma = turmaRepository.findById(turmaId);

        List<Tarefa> listTarefas = turma.get().getTarefas();

        return listTarefas.stream()
                .map(tarefa -> {
                    TarefaDTO tarefaDto = new TarefaDTO();
                    BeanUtils.copyProperties(tarefa,tarefaDto);

                    tarefaDto.setDtAbertura((Date) tarefa.getDtAbertura());
                    tarefaDto.setDtEncerramento((Date) tarefa.getDtEncerramento());
                    tarefaDto.setTurmaId(tarefa.getTurma().getId());
                    tarefaDto.setCriadorId(tarefa.getCriador().getId());

                    return tarefaDto;
                })
                .toList();
    }

    @Override
    @Transactional
    public TarefaDTO add(TarefaDTO tarefa) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(tarefa.getCriadorId());
        Optional<Turma> turmaOptional = turmaRepository.findById(tarefa.getTurmaId());

        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("Usuario com o id " + tarefa.getCriadorId() + " não existe");
        }

        if (!usuarioOptional.get().isEhProfessor()){
            throw new RuntimeException("O usuário não tem permissão para adicionar tarefa");
        }

        if (turmaOptional.isEmpty()) {
            throw new RuntimeException("Turma com o id " + tarefa.getTurmaId() + " não existe");
        }

        Turma turma = turmaOptional.get();

        Usuario usuario = usuarioOptional.get();

        Tarefa novaTarefa = new Tarefa();
        novaTarefa.setTurma(turma);
        novaTarefa.setCriador(usuario);

        BeanUtils.copyProperties(tarefa, novaTarefa);
        turma.getTarefas().add(novaTarefa);

        tarefaRepository.save(novaTarefa);
        turmaRepository.save(turma);


        

        tarefa.setId(novaTarefa.getId());

        return tarefa;
    }

    @Override
    public TarefaDTO edit(TarefaDTO tarefa, Long professorId) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(professorId);
        Optional<Turma> turmaOptional = turmaRepository.findById(tarefa.getTurmaId());


        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("O usuário não existe");
        }

        if (!(usuarioOptional.get().isEhProfessor()) || !(tarefa.getCriadorId().equals(professorId))) {
            throw new RuntimeException("O usuário não tem permissão para editar a tarefa");
        }

        if (turmaOptional.isEmpty()) {
            throw new RuntimeException("Turma com o id " + tarefa.getTurmaId() + " não existe");
        }



        Tarefa tarefaEditada = new Tarefa();
        Turma turma = turmaOptional.get();
        Usuario usuario = usuarioOptional.get();
        tarefaEditada.setTurma(turma);
        tarefaEditada.setCriador(usuario);

        BeanUtils.copyProperties(tarefa,tarefaEditada);



        tarefaRepository.save(tarefaEditada);

        return tarefa;
    }

    @Override
    public void delete(Long id, Long professorId) {

        Optional<Usuario> usuario = usuarioRepository.findById(professorId);
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        Optional<Turma> turmaOptional = turmaRepository.findById(tarefa.get().getTurma().getId());

        if (usuario.isEmpty()) {
            throw new RuntimeException("O usuário não existe");
        }

        if(tarefa.isEmpty()){
            throw new RuntimeException("Tarefa com o ID informado não existe");
        }

        if (!(usuario.get().isEhProfessor()) || !(tarefa.get().getCriador().getId().equals(professorId))) {
            throw new RuntimeException("O usuário não tem permissão para deletar a tarefa");
        }



        Turma turma = turmaOptional.get();
        turma.getTarefas().remove(tarefa.get());
        tarefaRepository.deleteById(id);

    }
}

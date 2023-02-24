package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.model.Tarefa;
import com.tcc.joaomyrlla.appcode2know.service.ITarefaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaServiceImpl implements ITarefaService {
    @Override
    public List<Tarefa> findByAluno(Long alunoId) {
        return null;
    }

    @Override
    public List<Tarefa> findByTurma(Long turmaId) {
        return null;
    }

    @Override
    public Tarefa add(Tarefa tarefa) {
        return null;
    }

    @Override
    public Tarefa edit(Tarefa tarefa, Long professorId) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}

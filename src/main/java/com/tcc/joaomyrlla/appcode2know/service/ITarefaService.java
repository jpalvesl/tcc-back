package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.model.Tarefa;

import java.util.List;

public interface ITarefaService {
    List<Tarefa> findByAluno(Long alunoId);

    List<Tarefa> findByTurma(Long turmaId);

    Tarefa add(Tarefa tarefa);

    Tarefa edit(Tarefa tarefa, Long professorId);

    void delete(Long id);
}

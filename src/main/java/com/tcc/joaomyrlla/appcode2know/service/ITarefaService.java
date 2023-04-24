package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.dto.TarefaDTO;

import java.util.List;
import java.util.Map;

public interface ITarefaService {
    Map<String, List<TarefaDTO>> findByAluno(Long alunoId);

    Map<String, List<TarefaDTO>> findByTurma(Long turmaId);

    TarefaDTO add(TarefaDTO tarefaDTO);

    TarefaDTO edit(TarefaDTO tarefaDTO, Long professorId);

    void delete(Long id, Long professorId);

    void addProblemaEmTarefa(Long problemaId, Long tarefaId, Long usuarioId);

    void removerProblemaEmTarefa(Long problemaId, Long tarefaId, Long usuarioId);

    TarefaDTO findById(Long id);
}

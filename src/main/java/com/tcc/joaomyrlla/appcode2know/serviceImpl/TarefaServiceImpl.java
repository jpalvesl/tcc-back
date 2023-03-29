package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.dto.TarefaDTO;
import com.tcc.joaomyrlla.appcode2know.service.ITarefaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaServiceImpl implements ITarefaService {
    @Override
    public List<TarefaDTO> findByAluno(Long alunoId) {
        return null;
    }

    @Override
    public List<TarefaDTO> findByTurma(Long turmaId) {
        return null;
    }

    @Override
    public TarefaDTO add(TarefaDTO tarefa) {
        return null;
    }

    @Override
    public TarefaDTO edit(TarefaDTO tarefa, Long professorId) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}

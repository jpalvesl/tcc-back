package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.model.Turma;
import com.tcc.joaomyrlla.appcode2know.service.ITurmaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmaServiceImpl implements ITurmaService {
    @Override
    public List<Turma> findByInstituicao(Long instituicaoId) {
        return null;
    }

    @Override
    public List<Turma> findByUsuario(Long usuarioId) {
        return null;
    }

    @Override
    public Turma add(Turma turma) {
        return null;
    }

    @Override
    public Turma edit(Turma turma, Long professorId) {
        return null;
    }

    @Override
    public void delete(Long turmaId) {

    }
}

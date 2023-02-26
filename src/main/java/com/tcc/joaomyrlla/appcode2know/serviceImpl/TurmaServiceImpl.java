package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.dto.TurmaDTO;
import com.tcc.joaomyrlla.appcode2know.service.ITurmaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmaServiceImpl implements ITurmaService {
    @Override
    public List<TurmaDTO> findByInstituicao(Long instituicaoId) {
        return null;
    }

    @Override
    public List<TurmaDTO> findByUsuario(Long usuarioId) {
        return null;
    }

    @Override
    public TurmaDTO add(TurmaDTO turma) {
        return null;
    }

    @Override
    public TurmaDTO edit(TurmaDTO turma, Long professorId) {
        return null;
    }

    @Override
    public void delete(Long turmaId) {

    }
}

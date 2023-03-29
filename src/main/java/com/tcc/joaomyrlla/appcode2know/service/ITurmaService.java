package com.tcc.joaomyrlla.appcode2know.service;


import com.tcc.joaomyrlla.appcode2know.dto.TurmaDTO;

import java.util.List;

public interface ITurmaService {
    List<TurmaDTO> findByInstituicao(Long instituicaoId);

    List<TurmaDTO> findByUsuario(Long usuarioId);

    TurmaDTO add(TurmaDTO turma);

    TurmaDTO edit(TurmaDTO turma, Long professorId);

    void delete(Long turmaId);
}

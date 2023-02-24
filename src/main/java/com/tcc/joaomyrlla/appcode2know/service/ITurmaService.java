package com.tcc.joaomyrlla.appcode2know.service;


import com.tcc.joaomyrlla.appcode2know.model.Turma;

import java.util.List;

public interface ITurmaService {
    List<Turma> findByInstituicao(Long instituicaoId);

    List<Turma> findByUsuario(Long usuarioId);

    Turma add(Turma turma);

    Turma edit(Turma turma, Long professorId);

    void delete(Long turmaId);
}

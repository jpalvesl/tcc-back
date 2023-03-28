package com.tcc.joaomyrlla.appcode2know.service;


import com.tcc.joaomyrlla.appcode2know.dto.TurmaDTO;

import java.util.List;

public interface ITurmaService {
    List<TurmaDTO> findByInstituicao(Long instituicaoId);

    List<TurmaDTO> findByUsuario(Long usuarioId);

    TurmaDTO add(TurmaDTO turma, Long criadorId);

    TurmaDTO edit(TurmaDTO turma, Long professorId);

    void delete(Long turmaId, Long professorId);

    void addAlunoEmTurma (Long turmaId, Long alunoId, Long criadorId);

    void removerAlunoDaTurma (Long turmaId, Long alunoId, Long professorId);

    void addProfessorEmTurma(Long turmaId, Long professorId, Long professorAdicionadoId);

    void removerProfessorDaTurma(Long turmaId, Long professorId, Long professorAdicionadoId);
}

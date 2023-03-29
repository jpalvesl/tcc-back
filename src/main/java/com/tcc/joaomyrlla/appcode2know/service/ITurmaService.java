package com.tcc.joaomyrlla.appcode2know.service;


import com.tcc.joaomyrlla.appcode2know.dto.TurmaDTO;

import java.util.List;
import java.util.Map;

public interface ITurmaService {
    TurmaDTO findById(Long id);

    List<TurmaDTO> findByInstituicao(Long instituicaoId);

    Map<String, List<TurmaDTO>> findByUsuario(Long usuarioId);

    TurmaDTO add(TurmaDTO turmaDTO, Long criadorId);

    TurmaDTO edit(TurmaDTO turmaDTO, Long professorId);

    void delete(Long turmaId, Long professorId);

    void addAlunoEmTurma (Long turmaId, Long alunoId, Long criadorId);

    void removerAlunoDaTurma (Long turmaId, Long alunoId, Long professorId);

    void addProfessorEmTurma(Long turmaId, Long professorId, Long professorAdicionadoId);

    void removerProfessorDaTurma(Long turmaId, Long professorId, Long professorAdicionadoId);
}

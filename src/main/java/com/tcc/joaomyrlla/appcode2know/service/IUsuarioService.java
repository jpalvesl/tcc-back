package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.dto.UsuarioDTO;

import java.util.List;

public interface IUsuarioService {
    List<UsuarioDTO> findByInstituicao(Long instituicaoId);

    List<UsuarioDTO> findAlunoByInstituicao(Long instituicaoId);

    List<UsuarioDTO> findProfessorByInstituicao(Long instituicaoId);

    UsuarioDTO findById(Long id);

    List<UsuarioDTO> findByTurma(Long turmaId);

    UsuarioDTO add(UsuarioDTO usuarioDTO);

    UsuarioDTO edit(UsuarioDTO usuarioDTO);

    public void delete(Long id);
}

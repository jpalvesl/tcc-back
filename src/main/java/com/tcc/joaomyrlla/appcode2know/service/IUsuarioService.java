package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.dto.UsuarioDTO;

import java.util.List;

public interface IUsuarioService {
    public List<UsuarioDTO> findByInstituicao(Long instituicaoId);

    public UsuarioDTO findById(Long id);

    public List<UsuarioDTO> findByTurma(Long turmaId);

    public UsuarioDTO add(UsuarioDTO usuarioDTO);

    public UsuarioDTO edit(UsuarioDTO usuarioDTO);

    public void delete(Long id);
}

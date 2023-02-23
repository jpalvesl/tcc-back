package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.model.Usuario;

import java.util.List;

public interface IUsuarioService {
    public List<Usuario> findByInstituicao();

    public Usuario findById(Long id);

    public List<Usuario> findByTurma(Long turmaId);

    public Usuario add(Usuario usuario);

    public Usuario edit(Usuario usuario);

    public void delete(Long id);
}

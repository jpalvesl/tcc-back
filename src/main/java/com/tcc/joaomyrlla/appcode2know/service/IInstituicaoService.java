package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.model.Instituicao;

import java.util.List;

public interface IInstituicaoService {
    public List<Instituicao> findAll();

    public Instituicao findById(Long id);

    public Instituicao add(Instituicao instituicao);

    public void delete(Long id, Long usuarioId);

    public Instituicao edit(Instituicao instituicao, Long usuarioId);
}

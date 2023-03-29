package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.dto.InstituicaoDTO;

import java.util.List;

public interface IInstituicaoService {
    public List<InstituicaoDTO> findAll();

    public InstituicaoDTO findById(Long id);

    public InstituicaoDTO add(InstituicaoDTO instituicaoDTO);

    public void delete(Long id, Long usuarioId);

    public InstituicaoDTO edit(InstituicaoDTO instituicaoDTO, Long usuarioId);
}

package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.dto.TopicoDTO;

import java.util.List;

public interface ITopicoService {
    List<TopicoDTO> findAll();

    List<TopicoDTO> findByProblema(Long problemaId);

    TopicoDTO add(TopicoDTO topicoDTO);

    TopicoDTO edit(TopicoDTO topicoDTO);

    void delete(Long id);
}

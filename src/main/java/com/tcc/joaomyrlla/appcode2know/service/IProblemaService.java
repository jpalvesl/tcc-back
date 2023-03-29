package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.dto.ProblemaDTO;

import java.util.List;

public interface IProblemaService {
    public List<ProblemaDTO> findAll();

    public ProblemaDTO findById(Long id);

    public ProblemaDTO add(ProblemaDTO problemaDTO);

    public void delete(Long id, Long usuarioId);

    public ProblemaDTO edit(ProblemaDTO problemaDTO, Long usuarioId);
}

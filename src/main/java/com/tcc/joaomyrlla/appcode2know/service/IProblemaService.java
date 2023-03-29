package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.dto.ProblemaDTO;

import java.util.List;

public interface IProblemaService {
    List<ProblemaDTO> findAll();

    ProblemaDTO findById(Long id);

    List<ProblemaDTO> findByTarefa(Long tarefaId);

    ProblemaDTO add(ProblemaDTO problemaDTO);

    void delete(Long id, Long usuarioId);

    ProblemaDTO edit(ProblemaDTO problemaDTO, Long usuarioId);

    void addTopicoEmProblema(Long topicoId, Long problemaId);

    void removerTopicoEmProblema(Long topicoId, Long problemaId);
}

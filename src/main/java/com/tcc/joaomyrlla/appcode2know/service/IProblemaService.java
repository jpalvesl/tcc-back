package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.dto.ProblemaDTO;

import java.util.List;
import java.util.Map;

public interface IProblemaService {
    List<ProblemaDTO> findAll();

    ProblemaDTO findById(Long id);

    List<ProblemaDTO> findByTarefa(Long tarefaId);

    List<ProblemaDTO> findByTarefaAndUsusario(Long tarefaId, Long usuarioId);

    ProblemaDTO add(ProblemaDTO problemaDTO);

    void delete(Long id, Long usuarioId);

    ProblemaDTO edit(ProblemaDTO problemaDTO, Long usuarioId);

    void addTopicoEmProblema(Long topicoId, Long problemaId);

    void removerTopicoEmProblema(Long topicoId, Long problemaId);

    Map<String, Object> findProblemasTentadosEResolvidos(Long usuarioId);

    List<ProblemaDTO> findProblemasTentados(Long usuarioId);
}

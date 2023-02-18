package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.model.Problema;
import com.tcc.joaomyrlla.appcode2know.repository.ProblemaRepository;

import java.util.List;

public interface IProblemaService {
    public List<Problema> findAll();

    public Problema findById(Long id);

    public Problema add(Problema problema);

    public void delete(Long id, Long usuarioId);

    public Problema edit(Problema problema, Long usuarioId);
}

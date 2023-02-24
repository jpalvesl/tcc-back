package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.model.CasoDeTeste;

import java.util.List;

public interface ICasoDeTesteService {
    List<CasoDeTeste> findBySubmissao(Long submissaoId);

    CasoDeTeste add(CasoDeTeste casoDeTeste);

    CasoDeTeste edit(CasoDeTeste casoDeTeste);

    void delete(Long id);
}

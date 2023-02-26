package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.dto.CasoDeTesteDTO;

import java.util.List;

public interface ICasoDeTesteService {
    List<CasoDeTesteDTO> findBySubmissao(Long submissaoId);

    CasoDeTesteDTO add(CasoDeTesteDTO casoDeTeste);

    CasoDeTesteDTO edit(CasoDeTesteDTO casoDeTeste);

    void delete(Long id);
}

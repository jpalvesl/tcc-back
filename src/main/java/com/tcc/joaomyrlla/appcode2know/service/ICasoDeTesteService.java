package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.dto.CasoDeTesteDTO;

import java.util.List;

public interface ICasoDeTesteService {
    List<CasoDeTesteDTO> findBySubmissao(Long submissaoId);

    CasoDeTesteDTO add(CasoDeTesteDTO casoDeTeste, Long problemaId, Long criadorId);

    CasoDeTesteDTO edit(CasoDeTesteDTO casoDeTeste, Long criadorId);

    void delete(Long id, Long criadorId);
}

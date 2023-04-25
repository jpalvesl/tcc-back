package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.dto.CasoDeTesteDTO;
import com.tcc.joaomyrlla.appcode2know.model.CasoDeTeste;

import java.util.List;

public interface ICasoDeTesteService {
    List<CasoDeTesteDTO> findBySubmissao(Long submissaoId);

    List<CasoDeTesteDTO> findByProblema(Long problemaId);

    CasoDeTesteDTO add(CasoDeTesteDTO casoDeTesteDTO, Long problemaId, Long criadorId);

    CasoDeTesteDTO edit(CasoDeTesteDTO casoDeTesteDTO, Long criadorId);

    List<CasoDeTesteDTO> editEmLote(List<CasoDeTesteDTO> casosDeTesteDTO, Long problemaId, Long criadorId);

    void delete(Long id, Long criadorId);
}

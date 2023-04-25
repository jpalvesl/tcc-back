package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.dto.CasoDeTesteDTO;
import com.tcc.joaomyrlla.appcode2know.model.CasoDeTeste;

import java.util.List;
import java.util.Map;

public interface ICasoDeTesteService {
    Map<String, Object> findBySubmissao(Long submissaoId);

    List<CasoDeTesteDTO> findByProblema(Long problemaId);

    CasoDeTesteDTO add(CasoDeTesteDTO casoDeTesteDTO, Long problemaId, Long criadorId);

    CasoDeTesteDTO edit(CasoDeTesteDTO casoDeTesteDTO, Long criadorId);

    List<CasoDeTesteDTO> editEmLote(List<CasoDeTesteDTO> casosDeTesteDTO, Long problemaId, Long criadorId);

    void delete(Long id, Long criadorId);
}

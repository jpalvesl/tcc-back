package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.dto.CasoDeTesteDTO;
import com.tcc.joaomyrlla.appcode2know.service.ICasoDeTesteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CasoDeTesteServiceImpl implements ICasoDeTesteService {
    @Override
    public List<CasoDeTesteDTO> findBySubmissao(Long submissaoId) {
        return null;
    }

    @Override
    public CasoDeTesteDTO add(CasoDeTesteDTO casoDeTeste) {
        return null;
    }

    @Override
    public CasoDeTesteDTO edit(CasoDeTesteDTO casoDeTeste) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}

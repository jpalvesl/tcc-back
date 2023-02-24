package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.model.CasoDeTeste;
import com.tcc.joaomyrlla.appcode2know.service.ICasoDeTesteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CasoDeTesteServiceImpl implements ICasoDeTesteService {
    @Override
    public List<CasoDeTeste> findBySubmissao(Long submissaoId) {
        return null;
    }

    @Override
    public CasoDeTeste add(CasoDeTeste casoDeTeste) {
        return null;
    }

    @Override
    public CasoDeTeste edit(CasoDeTeste casoDeTeste) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}

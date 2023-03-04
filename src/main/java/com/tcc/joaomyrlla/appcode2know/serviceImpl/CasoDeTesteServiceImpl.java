package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.dto.CasoDeTesteDTO;
import com.tcc.joaomyrlla.appcode2know.model.Problema;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.repository.CasoDeTesteRepository;
import com.tcc.joaomyrlla.appcode2know.repository.ProblemaRepository;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;
import com.tcc.joaomyrlla.appcode2know.service.ICasoDeTesteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CasoDeTesteServiceImpl implements ICasoDeTesteService {
    @Autowired
    CasoDeTesteRepository casoDeTesteRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ProblemaRepository problemaRepository;

    @Override
    public List<CasoDeTesteDTO> findBySubmissao(Long submissaoId) {
        return null;
    }

    @Override
    public CasoDeTesteDTO add(CasoDeTesteDTO casoDeTeste, Long problemaId, Long criadorId) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(criadorId);
        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("O usuário de Id" + criadorId + " não existe");
        }
        Optional<Problema> problemaOptional = problemaRepository.findById(problemaId);
        if (problemaOptional.isEmpty()) {
            throw new RuntimeException("O problema de Id" + problemaId + " não existe");
        }

        return null;
    }

    @Override
    public CasoDeTesteDTO edit(CasoDeTesteDTO casoDeTeste) {
        // verificar se o usuario que fez a edicao existe

        // verificar se eh um professor e o criador da questao

        // logica de negocio

        return null;
    }

    @Override
    public void delete(Long id) {

    }
}

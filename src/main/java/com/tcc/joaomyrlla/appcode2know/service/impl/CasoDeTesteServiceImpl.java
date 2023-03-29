package com.tcc.joaomyrlla.appcode2know.service.impl;

import com.tcc.joaomyrlla.appcode2know.dto.CasoDeTesteDTO;
import com.tcc.joaomyrlla.appcode2know.exceptions.CasoDeTesteNotFoundException;
import com.tcc.joaomyrlla.appcode2know.exceptions.InsufficientPrivilegeException;
import com.tcc.joaomyrlla.appcode2know.exceptions.ProblemaNotFoundException;
import com.tcc.joaomyrlla.appcode2know.exceptions.UsuarioNotFoundException;
import com.tcc.joaomyrlla.appcode2know.model.CasoDeTeste;
import com.tcc.joaomyrlla.appcode2know.model.Problema;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.repository.CasoDeTesteRepository;
import com.tcc.joaomyrlla.appcode2know.repository.ProblemaRepository;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;
import com.tcc.joaomyrlla.appcode2know.service.ICasoDeTesteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<CasoDeTesteDTO> findByProblema(Long problemaId) {
        return casoDeTesteRepository.findAll().stream()
                .filter(casoDeTeste -> problemaId.equals(casoDeTeste.getProblema().getId()))
                .map(CasoDeTesteDTO::toCasoDeTesteDTO)
                .toList();
    }

    @Override
    public CasoDeTesteDTO add(CasoDeTesteDTO casoDeTesteDTO, Long problemaId, Long criadorId) {
        Usuario usuario = usuarioRepository.findById(criadorId).orElseThrow(UsuarioNotFoundException::new);
        Problema problema = problemaRepository.findById(problemaId).orElseThrow(ProblemaNotFoundException::new);

        if (!criadorId.equals(problema.getCriador().getId()) || !(usuario.isEhProfessor())) {
            throw new InsufficientPrivilegeException("O usuário de Id " + criadorId + " não tem permissão para adicionar caso de teste");
        }

        CasoDeTeste casoDeTeste = CasoDeTeste.toCasoDeTeste(casoDeTesteDTO);
        casoDeTeste.setProblema(problema);
        casoDeTesteRepository.save(casoDeTeste);

        casoDeTesteDTO.setId(casoDeTeste.getId());


        return casoDeTesteDTO;
    }

    @Override
    public CasoDeTesteDTO edit(CasoDeTesteDTO casoDeTesteDTO, Long criadorId) {
        Usuario usuario = usuarioRepository.findById(criadorId).orElseThrow(UsuarioNotFoundException::new);
        Problema problema = problemaRepository.findById(casoDeTesteDTO.getProblemaId()).orElseThrow(ProblemaNotFoundException::new);

        if (!casoDeTesteRepository.existsById(casoDeTesteDTO.getId())) {
            throw new CasoDeTesteNotFoundException(String.format("Caso de teste com id %d não existe", casoDeTesteDTO.getId()));
        }

        if (!criadorId.equals(problema.getCriador().getId()) || !(usuario.isEhProfessor())) {
            throw new InsufficientPrivilegeException(String.format("O usuario de id %d não tem permissão para editar caso de teste", criadorId));
        }

        CasoDeTeste casoDeTeste = CasoDeTeste.toCasoDeTeste(casoDeTesteDTO);
        casoDeTeste.setProblema(problema);

        casoDeTesteRepository.save(casoDeTeste);

        casoDeTesteDTO.setId(casoDeTeste.getId());

        return casoDeTesteDTO;
    }

    @Override
    public void delete(Long id, Long criadorId) {
        CasoDeTeste casoDeTeste = casoDeTesteRepository.findById(id).orElseThrow(CasoDeTesteNotFoundException::new);
        Usuario usuario = usuarioRepository.findById(criadorId).orElseThrow(UsuarioNotFoundException::new);
        Problema problema = problemaRepository.findById(casoDeTeste.getProblema().getId()).orElseThrow(ProblemaNotFoundException::new);

        if (!criadorId.equals(problema.getCriador().getId()) || !(usuario.isEhProfessor())) {
            throw new InsufficientPrivilegeException("O usuário de Id " + criadorId + " não tem permissão para remover caso de teste");
        }

        problema.getCasosDeTeste().remove(casoDeTeste);

        casoDeTesteRepository.deleteById(id);
    }
}

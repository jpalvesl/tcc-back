
package com.tcc.joaomyrlla.appcode2know.service.impl;

import com.tcc.joaomyrlla.appcode2know.dto.CasoDeTesteDTO;
import com.tcc.joaomyrlla.appcode2know.dto.RespostaDeCasoTesteDTO;
import com.tcc.joaomyrlla.appcode2know.exceptions.*;
import com.tcc.joaomyrlla.appcode2know.model.*;
import com.tcc.joaomyrlla.appcode2know.repository.CasoDeTesteRepository;
import com.tcc.joaomyrlla.appcode2know.repository.ProblemaRepository;
import com.tcc.joaomyrlla.appcode2know.repository.SubmissaoRepository;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;
import com.tcc.joaomyrlla.appcode2know.service.ICasoDeTesteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CasoDeTesteServiceImpl implements ICasoDeTesteService {
    @Autowired
    CasoDeTesteRepository casoDeTesteRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ProblemaRepository problemaRepository;

    @Autowired
    SubmissaoRepository submissaoRepository;

    @Override
    public Map<String, Object> findBySubmissao(Long submissaoId) {
        Submissao submissao = submissaoRepository.findById(submissaoId).orElseThrow(SubmissaoNotFoundException::new);

        Map<String, Object> mapaRespostasECasos = new HashMap<>();
        mapaRespostasECasos.put("respostas", submissao.getRespostasCasoTeste().stream().map(RespostaDeCasoTesteDTO::toRespostaCasoDeTesteDTO).toList());
        mapaRespostasECasos.put("casos", submissao.getProblema().getCasosDeTeste().stream().map(CasoDeTesteDTO::toCasoDeTesteDTO).toList());

        return mapaRespostasECasos;
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
    @Transactional
    public List<CasoDeTesteDTO> editEmLote(List<CasoDeTesteDTO> casosDeTesteDTO, Long problemaId, Long criadorId) {
        Usuario usuario = usuarioRepository.findById(criadorId).orElseThrow(UsuarioNotFoundException::new);
        Problema problema = problemaRepository.findById(problemaId).orElseThrow(ProblemaNotFoundException::new);

        if (!criadorId.equals(problema.getCriador().getId()) || !(usuario.isEhProfessor())) {
            throw new InsufficientPrivilegeException(String.format("O usuario de id %d não tem permissão para editar caso de teste", criadorId));
        }

        problema.getCasosDeTeste().forEach(casoDeTeste -> {
            casoDeTesteRepository.deleteById(casoDeTeste.getId());
        });

        return casosDeTesteDTO.stream().map(casoDeTesteDTO -> {
            CasoDeTeste casoDeTeste = CasoDeTeste.toCasoDeTeste(casoDeTesteDTO);
            casoDeTeste.getProblema().setId(problemaId);

            problema.getCasosDeTeste().add(casoDeTeste);
            casoDeTesteRepository.save(casoDeTeste);

            return CasoDeTesteDTO.toCasoDeTesteDTO(casoDeTeste);
        }).toList();
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

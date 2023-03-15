package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.dto.CasoDeTesteDTO;
import com.tcc.joaomyrlla.appcode2know.model.CasoDeTeste;
import com.tcc.joaomyrlla.appcode2know.model.Problema;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.repository.CasoDeTesteRepository;
import com.tcc.joaomyrlla.appcode2know.repository.ProblemaRepository;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;
import com.tcc.joaomyrlla.appcode2know.service.ICasoDeTesteService;
import org.springframework.beans.BeanUtils;
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
    public List<CasoDeTesteDTO> findByProblema(Long problemaId) {
        List<CasoDeTeste> casosDeTeste = casoDeTesteRepository.findAll();

        return casosDeTeste.stream()
                .filter(caso -> problemaId.equals(caso.getProblema().getId()))
                .map(caso -> {
                    CasoDeTesteDTO casoDeTesteDto = new CasoDeTesteDTO();
                    BeanUtils.copyProperties(caso, casoDeTesteDto);
                    casoDeTesteDto.setProblemaId(caso.getProblema().getId());

                    return casoDeTesteDto;
                })
                .toList();
    }

    @Override
    public CasoDeTesteDTO add(CasoDeTesteDTO casoDeTeste, Long problemaId, Long criadorId) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(criadorId);
        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("O usuário de Id " + criadorId + " não existe");
        }
        Optional<Problema> problemaOptional = problemaRepository.findById(problemaId);
        if (problemaOptional.isEmpty()) {
            throw new RuntimeException("O problema de Id " + problemaId + " não existe");
        }

        if(problemaOptional.get().getCriador().getId()!= criadorId || !(usuarioOptional.get().isEhProfessor())){
            throw new RuntimeException("O usuário de Id " + criadorId + " não tem permissão para adicionar caso de teste");
        }

        CasoDeTeste novoCasoDeTeste = new CasoDeTeste();
        Problema problema = new Problema();
        problema.setId(casoDeTeste.getProblemaId());
        BeanUtils.copyProperties(casoDeTeste, novoCasoDeTeste);
        novoCasoDeTeste.setProblema(problema);
        casoDeTesteRepository.save(novoCasoDeTeste);

        return casoDeTeste;
    }

    @Override
    public CasoDeTesteDTO edit(CasoDeTesteDTO casoDeTeste, Long criadorId) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(criadorId);
        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("O usuário de Id " + criadorId + " não existe");
        }

        Optional<Problema> problemaOptional = problemaRepository.findById(casoDeTeste.getProblemaId());
        if (problemaOptional.isEmpty()) {
            throw new RuntimeException("O problema de Id não existe");
        }

        if (!casoDeTesteRepository.existsById(casoDeTeste.getId())) {
            throw new RuntimeException(String.format("Caso de teste com id %d não existe", casoDeTeste.getId()));
        }

        Usuario usuario = usuarioOptional.get();
        Problema problema = problemaOptional.get();

        if(problema.getCriador().getId() != criadorId || !(usuario.isEhProfessor())){
            throw new RuntimeException("O usuário de Id " + criadorId + " não tem permissão para editar caso de teste");
        }

        CasoDeTeste casoDeTesteEditado = new CasoDeTeste();
        BeanUtils.copyProperties(casoDeTeste,casoDeTesteEditado);
        casoDeTesteEditado.setProblema(problema);

        casoDeTesteRepository.save(casoDeTesteEditado);

        return casoDeTeste;
    }

    @Override
    public void delete(Long id, Long criadorId) {

        Optional<CasoDeTeste> casoDeTeste = casoDeTesteRepository.findById(id);

        // verificar se o usuario que fez a exclusão existe
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(criadorId);
        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("O usuário de Id " + criadorId + " não existe");
        }

        Optional<Problema> problemaOptional = problemaRepository.findById(casoDeTeste.get().getProblema().getId());

        // verificar se eh um professor e o criador da questao

        if (problemaOptional.isEmpty()) {
            throw new RuntimeException("O problema de Id não existe");
        }

        if(problemaOptional.get().getCriador().getId()!= criadorId || !(usuarioOptional.get().isEhProfessor())){
            throw new RuntimeException("O usuário de Id " + criadorId + " não tem permissão para remover caso de teste");
        }

        Problema problema = problemaOptional.get();
        problema.getCasosDeTeste().remove(casoDeTeste.get());

        casoDeTesteRepository.deleteById(id);

    }
}

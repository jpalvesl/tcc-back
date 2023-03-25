package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.dto.ProblemaDTO;
import com.tcc.joaomyrlla.appcode2know.exceptions.InsufficientPrivilegeException;
import com.tcc.joaomyrlla.appcode2know.exceptions.ProblemaNotFoundException;
import com.tcc.joaomyrlla.appcode2know.exceptions.UsuarioNotFoundException;
import com.tcc.joaomyrlla.appcode2know.model.Problema;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.repository.ProblemaRepository;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;
import com.tcc.joaomyrlla.appcode2know.service.IProblemaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProblemaServiceImpl implements IProblemaService {
    final
    ProblemaRepository problemaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public ProblemaServiceImpl(ProblemaRepository problemaRepository) {
        this.problemaRepository = problemaRepository;
    }

    public List<ProblemaDTO> findAll() {
        List<Problema> result = problemaRepository.findAll();
        List<ProblemaDTO> problemaDTOs = new ArrayList<>();

        result.forEach(problema -> {
            ProblemaDTO problemaDTO = new ProblemaDTO();
            BeanUtils.copyProperties(problema, problemaDTO);

            problemaDTO.setCriadorId(problema.getCriador().getId());
            problemaDTOs.add(problemaDTO);
        });

        return problemaDTOs;
    }

    public ProblemaDTO findById(Long id) {
        Problema problema = Optional.of(problemaRepository.findById(id).orElseThrow(ProblemaNotFoundException::new)).get();

        ProblemaDTO problemaDTO = new ProblemaDTO();

        BeanUtils.copyProperties(problema, problemaDTO);
        problemaDTO.setCriadorId(problema.getCriador().getId());

        return problemaDTO;
    }

    public ProblemaDTO add(ProblemaDTO problema) {
        Usuario usuario = usuarioRepository.findById(problema.getCriadorId()).orElseThrow(UsuarioNotFoundException::new);

        if (!usuario.isEhProfessor()) {
            throw new InsufficientPrivilegeException("O usuário de Id" + problema.getCriadorId() + " não é professor");
        }

        Problema novoProblema = new Problema();
        BeanUtils.copyProperties(problema, novoProblema);
        novoProblema.setCriador(usuario);

        problemaRepository.save(novoProblema);
        problema.setId(novoProblema.getId());

        return problema;
    }

    @Override
    public void delete(Long id, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNotFoundException::new);
        Problema problema = problemaRepository.findById(id).orElseThrow(ProblemaNotFoundException::new);


        if (!(usuario.isEhProfessor()) || !(problema.getCriador().getId().equals(usuarioId))) {
            throw new InsufficientPrivilegeException("O usuário não tem permissão para deletar a tarefa");
        }


        problema.getTarefas().forEach(tarefa -> tarefa.getProblemas().remove(problema));
        problemaRepository.deleteById(id);
    }

    @Override
    public ProblemaDTO edit(ProblemaDTO problemaDTO, Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNotFoundException::new);
        Problema problema = problemaRepository.findById(problemaDTO.getId()).orElseThrow(ProblemaNotFoundException::new);

        if (!(usuario.isEhProfessor()) || !(problema.getCriador().getId().equals(usuarioId))) {
            throw new InsufficientPrivilegeException("O usuário não tem permissão para deletar a tarefa");
        }

        Problema problemaEditado = new Problema();
        BeanUtils.copyProperties(problemaDTO, problemaEditado);
        problemaEditado.setCriador(usuario);

        problemaRepository.save(problemaEditado);

        return problemaDTO;
    }
}

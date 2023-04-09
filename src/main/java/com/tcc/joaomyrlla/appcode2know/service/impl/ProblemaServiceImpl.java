package com.tcc.joaomyrlla.appcode2know.service.impl;

import com.tcc.joaomyrlla.appcode2know.dto.ProblemaDTO;
import com.tcc.joaomyrlla.appcode2know.exceptions.*;
import com.tcc.joaomyrlla.appcode2know.model.Problema;
import com.tcc.joaomyrlla.appcode2know.model.Tarefa;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.model.Topico;
import com.tcc.joaomyrlla.appcode2know.repository.ProblemaRepository;
import com.tcc.joaomyrlla.appcode2know.repository.TarefaRepository;
import com.tcc.joaomyrlla.appcode2know.repository.TopicoRepository;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;
import com.tcc.joaomyrlla.appcode2know.service.IProblemaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemaServiceImpl implements IProblemaService {
    final
    ProblemaRepository problemaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TarefaRepository tarefaRepository;

    @Autowired
    TopicoRepository topicoRepository;

    public ProblemaServiceImpl(ProblemaRepository problemaRepository) {
        this.problemaRepository = problemaRepository;
    }

    public List<ProblemaDTO> findAll() {
        return problemaRepository.findAll().stream()
                .map(ProblemaDTO::toProblemaDTO)
                .toList();
    }

    public ProblemaDTO findById(Long id) {
        Problema problema = problemaRepository.findById(id).orElseThrow(ProblemaNotFoundException::new);

        return ProblemaDTO.toProblemaDTO(problema);
    }

    @Override
    public List<ProblemaDTO> findByTarefa(Long tarefaId) {
        Tarefa tarefa = tarefaRepository.findById(tarefaId).orElseThrow(TarefaNotFoundException::new);

        return tarefa.getProblemas().stream()
                .map(ProblemaDTO::toProblemaDTO)
                .toList();
    }


    public ProblemaDTO add(ProblemaDTO problemaDTO) {
        Usuario usuario = usuarioRepository.findById(problemaDTO.getCriadorId()).orElseThrow(UsuarioNotFoundException::new);

        if (!usuario.isEhProfessor()) {
            throw new InsufficientPrivilegeException(String.format("O usuario de Id %d não é professor", problemaDTO.getCriadorId()));
        }

        Problema problema = Problema.toProblema(problemaDTO);
        problema.setCriador(usuario);

        problemaRepository.save(problema);
        problemaDTO.setId(problema.getId());

        return problemaDTO;
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

        BeanUtils.copyProperties(problemaDTO, problema);
        problemaRepository.save(problema);

        return problemaDTO;
    }

    @Override
    public void addTopicoEmProblema(Long topicoId, Long problemaId) {
        Topico topico = topicoRepository.findById(topicoId).orElseThrow(TopicoNotFoundException::new);
        Problema problema = problemaRepository.findById(problemaId).orElseThrow(ProblemaNotFoundException::new);

        topico.getProblemas().add(problema);
        topicoRepository.save(topico);
    }

    @Override
    public void removerTopicoEmProblema(Long topicoId, Long problemaId) {
        Topico topico = topicoRepository.findById(topicoId).orElseThrow(TopicoNotFoundException::new);
        Problema problema = problemaRepository.findById(problemaId).orElseThrow(ProblemaNotFoundException::new);

        topico.getProblemas().remove(problema);
        topicoRepository.save(topico);
    }
}
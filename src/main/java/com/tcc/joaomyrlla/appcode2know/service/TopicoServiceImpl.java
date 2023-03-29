package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.dto.TopicoDTO;
import com.tcc.joaomyrlla.appcode2know.exceptions.ProblemaNotFoundException;
import com.tcc.joaomyrlla.appcode2know.exceptions.TopicoNotFoundException;
import com.tcc.joaomyrlla.appcode2know.model.Problema;
import com.tcc.joaomyrlla.appcode2know.model.Topico;
import com.tcc.joaomyrlla.appcode2know.repository.ProblemaRepository;
import com.tcc.joaomyrlla.appcode2know.repository.TopicoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicoServiceImpl implements ITopicoService {
    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    ProblemaRepository problemaRepository;

    @Override
    public List<TopicoDTO> findAll() {
        return topicoRepository.findAll().stream()
                .map(TopicoDTO::toTopicoDTO)
                .toList();
    }

    @Override
    public List<TopicoDTO> findByProblema(Long problemaId) {
        Problema problema = problemaRepository.findById(problemaId).orElseThrow(ProblemaNotFoundException::new);

        return problema.getTopicos().stream()
                .map(TopicoDTO::toTopicoDTO)
                .toList();
    }

    @Override
    public TopicoDTO add(TopicoDTO topicoDTO) {
        Topico topico = Topico.toTopico(topicoDTO);

        topicoRepository.save(topico);
        topicoDTO.setId(topico.getId());

        return topicoDTO;
    }

    @Override
    public TopicoDTO edit(TopicoDTO topicoDTO) {
        Topico topico = topicoRepository.findById(topicoDTO.getId()).orElseThrow(TopicoNotFoundException::new);
        BeanUtils.copyProperties(topicoDTO, topico);

        topicoRepository.save(topico);

        return topicoDTO;
    }

    @Override
    public void delete(Long id) {
        Topico topico = topicoRepository.findById(id).orElseThrow(TopicoNotFoundException::new);

        topico.getProblemas().clear();
        topicoRepository.save(topico);
        topicoRepository.delete(topico);
    }
}

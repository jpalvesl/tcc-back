package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.dto.ProblemaDTO;
import com.tcc.joaomyrlla.appcode2know.model.Problema;
import com.tcc.joaomyrlla.appcode2know.repository.ProblemaRepository;
import com.tcc.joaomyrlla.appcode2know.service.IProblemaService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProblemaServiceImpl implements IProblemaService {
    final
    ProblemaRepository problemaRepository;

    public ProblemaServiceImpl(ProblemaRepository problemaRepository) {
        this.problemaRepository = problemaRepository;
    }

    public List<ProblemaDTO> findAll() {
        List<Problema> result = problemaRepository.findAll();
        List<ProblemaDTO> problemaDTOs = new ArrayList<>();

        result.forEach(problema -> {
            ProblemaDTO problemaDTO = new ProblemaDTO();
            BeanUtils.copyProperties(problema, problemaDTO);

            problemaDTOs.add(problemaDTO);
        });

        return problemaDTOs;
    }

    public ProblemaDTO findById(Long id) {
        Optional<Problema> result = problemaRepository.findById(id);

        ProblemaDTO problema = new ProblemaDTO();

        if (result.isEmpty()) return null;
        BeanUtils.copyProperties(result.get(), problema);

        return problema;
    }

    public ProblemaDTO add(ProblemaDTO problema) {
        Problema novoProblema = new Problema() ;
        BeanUtils.copyProperties(problema, novoProblema);

        problemaRepository.save(novoProblema);

        return problema;
    }

    @Override
    public void delete(Long id, Long usuarioId) {
//         TODO: Verificar lista de usuarios em busca do usuario com id informado
//
//         TODO: Verificar se o usuario buscado tem o atribuito eh_professor como true
//
//         TODO: Caso sim deletar
//
//         TODO: Caso nao entregar uma excecao
        problemaRepository.deleteById(id);
    }

    @Override
    public ProblemaDTO edit(ProblemaDTO problema, Long usuarioId) {
//         TODO: Verificar lista de usuarios em busca do usuario com id informado
//
//         TODO: Verificar se o usuario buscado tem o atribuito eh_professor como true
//
//         TODO: Caso sim editar
//
//         TODO: Caso nao entregar uma excecao
        Problema novoProblema = new Problema() ;
        BeanUtils.copyProperties(problema, novoProblema);

        problemaRepository.save(novoProblema);

        return problema;
    }
}

package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.dto.ProblemaDTO;
import com.tcc.joaomyrlla.appcode2know.dto.UsuarioDTO;
import com.tcc.joaomyrlla.appcode2know.model.Problema;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.repository.ProblemaRepository;
import com.tcc.joaomyrlla.appcode2know.service.IProblemaService;
import com.tcc.joaomyrlla.appcode2know.service.IUsuarioService;
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
    IUsuarioService usuarioService;

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
        Optional<Problema> result = problemaRepository.findById(id);

        ProblemaDTO problema = new ProblemaDTO();

        if (result.isEmpty()) return null;
        BeanUtils.copyProperties(result.get(), problema);
        problema.setCriadorId(result.get().getCriador().getId());

        return problema;
    }

    public ProblemaDTO add(ProblemaDTO problema) {
        Problema novoProblema = new Problema() ;
        BeanUtils.copyProperties(problema, novoProblema);

        problemaRepository.save(novoProblema);
        problema.setId(novoProblema.getId());

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
        Problema problemaEditado = new Problema();
        BeanUtils.copyProperties(problema, problemaEditado);

        Usuario criador = new Usuario();
        criador.setId(problema.getCriadorId());

        problemaRepository.save(problemaEditado);

        return problema;
    }
}

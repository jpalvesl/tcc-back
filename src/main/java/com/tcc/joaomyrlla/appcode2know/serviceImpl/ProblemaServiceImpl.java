package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.model.Problema;
import com.tcc.joaomyrlla.appcode2know.repository.ProblemaRepository;
import com.tcc.joaomyrlla.appcode2know.service.IProblemaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProblemaServiceImpl implements IProblemaService {
    final
    ProblemaRepository problemaRepository;

    public ProblemaServiceImpl(ProblemaRepository problemaRepository) {
        this.problemaRepository = problemaRepository;
    }

    public List<Problema> findAll() {
        return problemaRepository.findAll();
    }

    public Problema findById(Long id) {
        Optional<Problema> result = problemaRepository.findById(id);

        if (result.isEmpty()) return null;
        return result.get();
    }

    public Problema add(Problema problema) {
        return problemaRepository.save(problema);
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
    public Problema edit(Problema problema, Long usuarioId) {
//         TODO: Verificar lista de usuarios em busca do usuario com id informado
//
//         TODO: Verificar se o usuario buscado tem o atribuito eh_professor como true
//
//         TODO: Caso sim editar
//
//         TODO: Caso nao entregar uma excecao
        return problemaRepository.save(problema);
    }
}

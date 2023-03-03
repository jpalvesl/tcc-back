package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.dto.ProblemaDTO;
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
        Optional<Problema> result = problemaRepository.findById(id);

        ProblemaDTO problema = new ProblemaDTO();

        if (result.isEmpty()) return null;
        BeanUtils.copyProperties(result.get(), problema);
        problema.setCriadorId(result.get().getCriador().getId());

        return problema;
    }

    public ProblemaDTO add(ProblemaDTO problema) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(problema.getCriadorId());

        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("O usuário de Id" + problema.getCriadorId() + " não existe");
        }

        Usuario usuario = usuarioOptional.get();
        if (!usuario.isEhProfessor()) {
            throw new RuntimeException("O usuário de Id" + problema.getCriadorId() + " não é professor");
        }

        Problema novoProblema = new Problema() ;
        BeanUtils.copyProperties(problema, novoProblema);
        novoProblema.setCriador(usuario);

        problemaRepository.save(novoProblema);
        problema.setId(novoProblema.getId());

        return problema;
    }

    @Override
    public void delete(Long id, Long usuarioId) {
            Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
            Optional<Problema> problema = problemaRepository.findById(id);
            if (usuario.isEmpty()){
                throw new RuntimeException("O usuário de Id" + usuarioId + " não existe");
            }

            if(problema.isEmpty()){
                throw new RuntimeException("Problema com o ID informado não existe");
            }

            if (!(usuario.get().isEhProfessor()) || !(problema.get().getCriador().getId().equals(usuarioId))) {
                    throw new RuntimeException("O usuário não tem permissão para deletar a tarefa");
            }



            if(!(problema.get().getTarefas().isEmpty())){
                problema.get().getTarefas().forEach(tarefa -> {
                    tarefa.getProblemas().remove(problema.get());
                });
            }
            problemaRepository.deleteById(id);

    }

    @Override
    public ProblemaDTO edit(ProblemaDTO problema, Long usuarioId) {

        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
        Optional<Problema> problemaOptional = problemaRepository.findById(problema.getId());

        if (usuario.isEmpty()){
            throw new RuntimeException("O usuário de Id" + usuarioId + " não existe");
        }

        if(problemaOptional.isEmpty()){
            throw new RuntimeException("Problema com o ID informado não existe");
        }

        if (!(usuario.get().isEhProfessor()) || !(problemaOptional.get().getCriador().getId().equals(usuarioId))) {
            throw new RuntimeException("O usuário não tem permissão para deletar a tarefa");
        }

        Problema problemaEditado = new Problema();
        BeanUtils.copyProperties(problema, problemaEditado);
        problemaEditado.setCriador(usuario.get());

        problemaRepository.save(problemaEditado);

        return problema;
    }
}

package com.tcc.joaomyrlla.appcode2know.service.impl;

import com.tcc.joaomyrlla.appcode2know.dto.ProblemaDTO;
import com.tcc.joaomyrlla.appcode2know.dto.SubmissaoDTO;
import com.tcc.joaomyrlla.appcode2know.exceptions.*;
import com.tcc.joaomyrlla.appcode2know.model.Problema;
import com.tcc.joaomyrlla.appcode2know.model.Tarefa;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.model.Topico;
import com.tcc.joaomyrlla.appcode2know.repository.*;
import com.tcc.joaomyrlla.appcode2know.service.IProblemaService;
import com.tcc.joaomyrlla.appcode2know.service.ISubmissaoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    ISubmissaoService submissaoService;

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
        problemaDTO.setAutor(usuario.getNome());

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
        problema.getTopicos().clear();
        problemaDTO.getTopicos().forEach(mapTopico -> {
            Topico topico = new Topico();
            Integer topicoId = (Integer) mapTopico.get("id");
            String topicoNome = (String) mapTopico.get("nome");

            topico.setId(topicoId.longValue());
            topico.setNome(topicoNome);
            problema.getTopicos().add(topico);
        });

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

    @Override
    public Map<String, Object> findProblemasTentadosEResolvidos(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNotFoundException::new);

        Set<Long> problemasTentados = submissaoService.findByAluno(usuarioId).stream().map(SubmissaoDTO::getProblemaId).collect(Collectors.toSet());
        Set<Long> problemasResolvidos = submissaoService.findByAluno(usuarioId).stream().filter(submissaoDTO -> submissaoDTO.getStatus().equals("OK")).map(SubmissaoDTO::getProblemaId).collect(Collectors.toSet());

        Map<String, Object> mapProblemas = new HashMap<>();
        mapProblemas.put("tentados", problemasTentados.size());
        mapProblemas.put("resolvidos", problemasResolvidos.size());

        return mapProblemas;
    }
}

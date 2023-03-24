package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.exceptions.InstituicaoNotFoundException;
import com.tcc.joaomyrlla.appcode2know.exceptions.UsuarioNotFoundException;
import com.tcc.joaomyrlla.appcode2know.model.Instituicao;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.dto.InstituicaoDTO;
import com.tcc.joaomyrlla.appcode2know.repository.InstituicaoRespository;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;
import com.tcc.joaomyrlla.appcode2know.service.IInstituicaoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.ArrayList;

@Service
public class InstituicaoServiceImpl implements IInstituicaoService {
    @Autowired
    InstituicaoRespository instituicaoRespository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<InstituicaoDTO> findAll() {
        List<Instituicao> result = instituicaoRespository.findAll();

        List<InstituicaoDTO> instituicaoDTOs = new ArrayList<>();
        result.forEach(instituicao -> {
            InstituicaoDTO instituicaoDTO = new InstituicaoDTO();
            BeanUtils.copyProperties(instituicao, instituicaoDTO);

            instituicaoDTOs.add(instituicaoDTO);
        });

        return instituicaoDTOs;
    }

    @Override
    public InstituicaoDTO findById(Long id) {
        Instituicao instituicao = instituicaoRespository.findById(id).orElseThrow(InstituicaoNotFoundException::new);

        InstituicaoDTO instituicaoDTO = new InstituicaoDTO();

        BeanUtils.copyProperties(instituicao, instituicaoDTO);
        return instituicaoDTO;
    }

    @Override
    public InstituicaoDTO add(InstituicaoDTO instituicao) {
        Instituicao novaInstituicao = new Instituicao();
        BeanUtils.copyProperties(instituicao, novaInstituicao);
        
        instituicaoRespository.save(novaInstituicao);

        instituicao.setId(novaInstituicao.getId());

        return instituicao;
    }

    @Override
    @Transactional
    public void delete(Long id, Long usuarioId) {
        
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNotFoundException::new);

        if (!usuario.isEhAdm()) {
            throw new RuntimeException("O usuário não tem permissão para deletar a instituição");
        }

        if (!instituicaoRespository.existsById(id)) {
            throw new InstituicaoNotFoundException(String.format("A instituição com id %d não pode ser encontrada", id));
        }

        List<Usuario> result = usuarioRepository.findAll();

        result.stream()
                .filter(user -> user.getInstituicaoAtual() != null && user.getInstituicaoAtual().getId().equals(id))
                .forEach(user -> {
                    user.setInstituicaoAtual(null);
                    Usuario usuarioSemInstituicao = new Usuario();
                    BeanUtils.copyProperties(user, usuarioSemInstituicao);
                    usuarioRepository.save(usuarioSemInstituicao);
                });

        instituicaoRespository.deleteById(id);
    }

    @Override
    public InstituicaoDTO edit(InstituicaoDTO instituicao, Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNotFoundException::new);

        if (!usuario.isEhAdm()) {
            throw new RuntimeException("O usuário não tem permissão para editar a instituição");
        }

        if (!instituicaoRespository.existsById(instituicao.getId())) {
            throw new InstituicaoNotFoundException(String.format("Instituicao com o Id %d não foi encontrada", instituicao.getId()));
        }

        Instituicao instituicaoEditada = new Instituicao();
        BeanUtils.copyProperties(instituicao, instituicaoEditada);

        instituicaoRespository.save(instituicaoEditada);

        return instituicao;
    }
}

package com.tcc.joaomyrlla.appcode2know.service.impl;

import com.tcc.joaomyrlla.appcode2know.exceptions.InstituicaoNotFoundException;
import com.tcc.joaomyrlla.appcode2know.exceptions.InsufficientPrivilegeException;
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

@Service
public class InstituicaoServiceImpl implements IInstituicaoService {
    @Autowired
    InstituicaoRespository instituicaoRespository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<InstituicaoDTO> findAll() {
        return instituicaoRespository.findAll().stream()
        .map(instituicao -> InstituicaoDTO.toInstituicaoDTO(instituicao))
        .toList();
    }

    @Override
    public InstituicaoDTO findById(Long id) {
        Instituicao instituicao = instituicaoRespository.findById(id).orElseThrow(InstituicaoNotFoundException::new);

        return InstituicaoDTO.toInstituicaoDTO(instituicao);
    }

    @Override
    public InstituicaoDTO add(InstituicaoDTO instituicaoDTO) {
        Instituicao instituicao = Instituicao.toInstituicao(instituicaoDTO);
        
        instituicaoRespository.save(instituicao);
        instituicaoDTO.setId(instituicao.getId());

        return instituicaoDTO;
    }

    @Override
    @Transactional
    public void delete(Long id, Long usuarioId) {
        
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNotFoundException::new);

        if (!usuario.isEhAdm()) {
            throw new InsufficientPrivilegeException("O usuário não tem permissão para deletar a instituição");
        }

        if (!instituicaoRespository.existsById(id)) {
            throw new InstituicaoNotFoundException(String.format("A instituição com id %d não pode ser encontrada", id));
        }

        usuarioRepository.findAll().stream()
                .filter(user -> user.getInstituicaoAtual() != null && user.getInstituicaoAtual().getId().equals(id))
                .forEach(user -> {
                    user.setInstituicaoAtual(null);

                    usuarioRepository.save(user);
                });

        instituicaoRespository.deleteById(id);
    }

    @Override
    public InstituicaoDTO edit(InstituicaoDTO instituicaoDTO, Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNotFoundException::new);

        if (!usuario.isEhAdm()) {
            throw new InsufficientPrivilegeException("O usuário não tem permissão para editar a instituição");
        }

        if (!instituicaoRespository.existsById(instituicaoDTO.getId())) {
            throw new InstituicaoNotFoundException(String.format("Instituicao com o Id %d não foi encontrada", instituicaoDTO.getId()));
        }

        Instituicao instituicaoEditada = Instituicao.toInstituicao(instituicaoDTO);

        instituicaoRespository.save(instituicaoEditada);

        return instituicaoDTO;
    }
}

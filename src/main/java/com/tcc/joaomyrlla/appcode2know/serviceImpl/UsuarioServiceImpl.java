package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.dto.UsuarioDTO;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;
import com.tcc.joaomyrlla.appcode2know.service.IUsuarioService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<UsuarioDTO> findByInstituicao() {
        var result = usuarioRepository.findAll();
        List<UsuarioDTO> usuariosDTOs = new ArrayList<>();

        result.forEach(usuario -> {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            BeanUtils.copyProperties(usuario, usuariosDTOs);


            usuariosDTOs.add(usuarioDTO);
        });

        return usuariosDTOs;
    }

    @Override
    public UsuarioDTO findById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        
        if (usuario.isEmpty()) return null;

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        BeanUtils.copyProperties(usuario, usuarioDTO);
        
        return usuarioDTO;
    }

    @Override
    public List<UsuarioDTO> findByTurma(Long turmaId) {
        // TODO: implementar turma antes de implementar essa parte
        return null;
    }

    @Override
    public UsuarioDTO add(UsuarioDTO usuario) {
        // TODO: Implementar DTO para ter a instituicao_id e com ele fazer a busca e setar o model para salvar corretamente
        Usuario novoUsusario = new Usuario();
        BeanUtils.copyProperties(usuario, novoUsusario);

        Usuario usuarioSalvo = usuarioRepository.save(novoUsusario);

        UsuarioDTO dto = new UsuarioDTO();
        BeanUtils.copyProperties(usuarioSalvo, dto);

        return dto;
    }

    @Override
    public UsuarioDTO edit(UsuarioDTO usuario) {
        Usuario novoUsusario = new Usuario();
        BeanUtils.copyProperties(usuario, novoUsusario);

        Usuario usuarioSalvo = usuarioRepository.save(novoUsusario);

        UsuarioDTO dto = new UsuarioDTO();
        BeanUtils.copyProperties(usuarioSalvo, dto);

        return dto;
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}

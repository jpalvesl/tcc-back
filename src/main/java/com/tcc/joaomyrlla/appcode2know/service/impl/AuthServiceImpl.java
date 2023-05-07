package com.tcc.joaomyrlla.appcode2know.service.impl;

import com.tcc.joaomyrlla.appcode2know.dto.AuthDTO;
import com.tcc.joaomyrlla.appcode2know.dto.UsuarioDTO;
import com.tcc.joaomyrlla.appcode2know.exceptions.AuthenticationNotAuthorizedException;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;
import com.tcc.joaomyrlla.appcode2know.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDTO login(AuthDTO authDTO) {
        List<UsuarioDTO> usuarios = usuarioRepository.findAll().stream().filter(usuario -> usernameOrEmailAndPasswordIsValid(authDTO, usuario)).map(UsuarioDTO::toUsuarioDTO).toList();

        if (usuarios.isEmpty()) throw new AuthenticationNotAuthorizedException();

        return usuarios.get(0);
    }

    private boolean usernameOrEmailAndPasswordIsValid(AuthDTO authDTO, Usuario usuario) {
        return (authDTO.getUsernameOrEmail().equals(usuario.getUsuario()) ||
                authDTO.getUsernameOrEmail().equals(usuario.getEmail())) &&
                BCrypt.checkpw(authDTO.getPassword(), usuario.getSenha());
    }
}

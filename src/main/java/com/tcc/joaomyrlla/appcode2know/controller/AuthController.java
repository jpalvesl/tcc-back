package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.dto.AuthDTO;
import com.tcc.joaomyrlla.appcode2know.dto.UsuarioDTO;
import com.tcc.joaomyrlla.appcode2know.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/login")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody AuthDTO authDTO) {
        UsuarioDTO usuario = authService.login(authDTO);


        Map<String, Object> mapUsuario = new HashMap<>();
        mapUsuario.put("id", usuario.getId());
        mapUsuario.put("nome", usuario.getNome());
        mapUsuario.put("ehProfessor", usuario.isEhProfessor());
        mapUsuario.put("ehAdministrador", usuario.isEhAdm());

        return ResponseEntity.ok().body(mapUsuario);
    }
}

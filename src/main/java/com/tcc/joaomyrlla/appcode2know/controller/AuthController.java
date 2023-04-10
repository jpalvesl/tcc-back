package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.dto.AuthDTO;
import com.tcc.joaomyrlla.appcode2know.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping
    public ResponseEntity login(@Valid @RequestBody AuthDTO authDTO) {
        return ResponseEntity.ok().body(authService.login(authDTO));
    }
}

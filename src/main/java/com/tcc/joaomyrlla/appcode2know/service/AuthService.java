package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.dto.AuthDTO;
import com.tcc.joaomyrlla.appcode2know.dto.UsuarioDTO;

public interface AuthService {
    UsuarioDTO login(AuthDTO authDTO);
}

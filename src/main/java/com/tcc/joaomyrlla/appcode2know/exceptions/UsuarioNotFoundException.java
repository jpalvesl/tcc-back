package com.tcc.joaomyrlla.appcode2know.exceptions;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(String message) {
        super(message);
    }

    public UsuarioNotFoundException() {
        super("O usuário não pode ser encontrado");
    }
}

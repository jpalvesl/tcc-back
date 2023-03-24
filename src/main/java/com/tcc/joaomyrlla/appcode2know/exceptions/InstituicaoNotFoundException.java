package com.tcc.joaomyrlla.appcode2know.exceptions;

public class InstituicaoNotFoundException extends RuntimeException {
    public InstituicaoNotFoundException() {
        super("A Instituição procurada não foi encontrada");
    }

    public InstituicaoNotFoundException(String message) {
        super(message);
    }
}

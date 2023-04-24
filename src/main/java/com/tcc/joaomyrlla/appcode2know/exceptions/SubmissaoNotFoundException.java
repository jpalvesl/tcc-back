package com.tcc.joaomyrlla.appcode2know.exceptions;

public class SubmissaoNotFoundException extends RuntimeException {
    public SubmissaoNotFoundException() {
        super("A Submissao procurada não foi encontrada");
    }

    public SubmissaoNotFoundException(String message) {
        super(message);
    }
}
package com.tcc.joaomyrlla.appcode2know.exceptions;

public class TopicoNotFoundException extends RuntimeException {
    public TopicoNotFoundException() {
        super("O tópico buscado não foi encontrado");
    }

    public TopicoNotFoundException(String message) {
        super(message);
    }
}

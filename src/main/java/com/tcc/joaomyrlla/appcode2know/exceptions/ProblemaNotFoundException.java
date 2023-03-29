package com.tcc.joaomyrlla.appcode2know.exceptions;

public class ProblemaNotFoundException extends RuntimeException {
    public ProblemaNotFoundException() {
        super("O problema procurado não foi encontrado");
    }

    public ProblemaNotFoundException(String message) {
        super(message);
    }
}

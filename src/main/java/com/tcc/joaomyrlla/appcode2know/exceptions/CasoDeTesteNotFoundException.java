package com.tcc.joaomyrlla.appcode2know.exceptions;

public class CasoDeTesteNotFoundException extends RuntimeException {
    public CasoDeTesteNotFoundException() {
        super("O Caso de teste não foi encontrado");
    }

    public CasoDeTesteNotFoundException(String message) {
        super(message);
    }
}

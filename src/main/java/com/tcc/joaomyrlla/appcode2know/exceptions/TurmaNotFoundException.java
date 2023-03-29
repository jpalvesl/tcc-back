package com.tcc.joaomyrlla.appcode2know.exceptions;

public class TurmaNotFoundException extends RuntimeException {
    public TurmaNotFoundException(String message) {
        super(message);
    }

    public TurmaNotFoundException() {
        super("A Turma não pode ser encontrado");
    }
}

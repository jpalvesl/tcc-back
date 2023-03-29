package com.tcc.joaomyrlla.appcode2know.exceptions;

public class TarefaNotFoundException extends RuntimeException {
    public TarefaNotFoundException() {
        super("A Tarefa procurada não foi encontrada");
    }

    public TarefaNotFoundException(String message) {
        super(message);
    }
}

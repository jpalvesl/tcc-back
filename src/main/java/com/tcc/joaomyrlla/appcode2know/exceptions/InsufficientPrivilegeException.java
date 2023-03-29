package com.tcc.joaomyrlla.appcode2know.exceptions;

public class InsufficientPrivilegeException extends RuntimeException {
    public InsufficientPrivilegeException(String format, Long professorId) {
        super("O usuário não tem privilégios para realizar essa ação");
    }

    public InsufficientPrivilegeException(String message) {
        super(message);
    }
}

package com.tcc.joaomyrlla.appcode2know.exceptions;

public class PasswordNotMatch extends RuntimeException {
    public PasswordNotMatch() {
        super("A senha fornecida não está de correta");
    }

    public PasswordNotMatch(String message) {
        super(message);
    }
}

package com.tcc.joaomyrlla.appcode2know.exceptions;

public class AuthenticationNotAuthorizedException extends RuntimeException {
    public AuthenticationNotAuthorizedException() {
        super("Ocorreu um problema no momento de realizar a autenticação");
    }

    public AuthenticationNotAuthorizedException(String message) {
        super(message);
    }
}
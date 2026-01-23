package br.com.taskhub_api.exception;

public class CredentialAlreadyExists extends RuntimeException {
    public CredentialAlreadyExists() {
        super("Credencial já existente");
    }
}

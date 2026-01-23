package br.com.taskhub_api.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Credencial inválidas");
    }
}

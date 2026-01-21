package br.com.taskhub_api.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String email) {
        super("Usuário já existente com email: " + email);
    }
}

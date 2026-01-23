package br.com.taskhub_api.exception;

public class UserNotMemberOfGroupException extends RuntimeException {
    public UserNotMemberOfGroupException() {
        super("Usuário não é um membro do grupo");
    }
}

package br.com.taskhub_api.exception;

public class GroupAlreadyExistsException extends RuntimeException {
    public GroupAlreadyExistsException() {
        super("Grupo com esse nome já existente");
    }
}

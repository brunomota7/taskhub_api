package br.com.taskhub_api.exception;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException() {
        super("Grupo não encontrado");
    }
}

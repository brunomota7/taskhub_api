package br.com.taskhub_api.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException() {
        super("Atividade não encontrada");
    }
}

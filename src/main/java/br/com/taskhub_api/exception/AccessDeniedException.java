package br.com.taskhub_api.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super("Acesso negado");
    }
}

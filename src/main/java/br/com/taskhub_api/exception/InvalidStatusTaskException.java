package br.com.taskhub_api.exception;

public class InvalidStatusTaskException extends RuntimeException {
    public InvalidStatusTaskException() {
        super("Status não é válido");
    }
}

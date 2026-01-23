package br.com.taskhub_api.exception;

public class AdminUserDeletionException extends RuntimeException {
    public AdminUserDeletionException() {
        super("Usuário ADMIN não pode ser excluído");
    }
}

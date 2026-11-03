package dev.amigoes.starlighttimer.domain.exceptions;

public class UsuarioNaoAutenticadoException extends RuntimeException {
    public UsuarioNaoAutenticadoException(String message) {
        super(message);
    }
}

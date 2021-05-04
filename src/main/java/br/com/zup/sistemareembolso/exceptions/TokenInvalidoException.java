package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class TokenInvalidoException extends ErroDoSistemaException {
    public TokenInvalidoException(String mensagem) {
        super(HttpStatus.BAD_REQUEST, "token", mensagem);
    }
}

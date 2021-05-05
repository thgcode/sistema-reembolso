package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class LocalidadeNaoExistenteException extends ErroDoSistemaException {
    public LocalidadeNaoExistenteException() {
        super(HttpStatus.NOT_FOUND, "Localidade", "Localidade não existente!");
    }
}

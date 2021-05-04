package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class ProjetoNaoExistenteException extends ErroDoSistemaException {
    public ProjetoNaoExistenteException() {
        super(HttpStatus.NOT_FOUND, "Projeto", "Projeto não existente!");
    }
}

package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class PermissaoNegadaParaCriarProjetoException extends ErroDoSistemaException {
    public PermissaoNegadaParaCriarProjetoException() {
        super(HttpStatus.FORBIDDEN, "Projeto", "Permissão negada para criar o projeto");
    }
}

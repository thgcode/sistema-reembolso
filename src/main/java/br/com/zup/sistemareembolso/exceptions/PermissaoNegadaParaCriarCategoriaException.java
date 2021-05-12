package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class PermissaoNegadaParaCriarCategoriaException extends ErroDoSistemaException {
    public PermissaoNegadaParaCriarCategoriaException() {
        super(HttpStatus.FORBIDDEN, "Categoria", "Permissão negada para criar categoria!");
    }
}

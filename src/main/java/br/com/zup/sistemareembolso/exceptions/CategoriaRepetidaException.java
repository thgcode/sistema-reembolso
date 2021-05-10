package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class CategoriaRepetidaException extends ErroDoSistemaException {
    public CategoriaRepetidaException() {
        super(HttpStatus.PRECONDITION_FAILED, "Categoria", "Categoria repetida!");
    }
}

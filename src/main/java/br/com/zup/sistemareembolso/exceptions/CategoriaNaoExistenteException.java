package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class CategoriaNaoExistenteException extends ErroDoSistemaException{

    public CategoriaNaoExistenteException() {
        super(HttpStatus.NOT_FOUND, "Categoria", "Categoria não existente!");
    }
}

package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class LocalidadeRepetidaException extends ErroDoSistemaException{
    public LocalidadeRepetidaException() {
        super(HttpStatus.PRECONDITION_FAILED, "Localidade", "Localidade repetida!");
    }
}

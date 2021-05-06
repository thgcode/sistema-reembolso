package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class VerbaDoProjetoInsuficienteException extends ErroDoSistemaException{
    public VerbaDoProjetoInsuficienteException() {
        super(HttpStatus.PRECONDITION_FAILED, "Projeto", "Verba do projeto insuficiente!");
    }
}

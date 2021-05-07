package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class DespesaJaReprovadaException extends ErroDoSistemaException{
    public DespesaJaReprovadaException() {
        super(HttpStatus.PRECONDITION_FAILED, "Despesa", "Despesa já não aprovada!");
    }
}

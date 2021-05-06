package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class DespesaJaAprovadaException extends ErroDoSistemaException {
    public DespesaJaAprovadaException() {
        super(HttpStatus.PRECONDITION_FAILED, "Despesa", "Despesa já aprovada!");
    }
}

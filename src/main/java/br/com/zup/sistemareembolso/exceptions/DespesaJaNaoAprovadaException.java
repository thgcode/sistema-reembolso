package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class DespesaJaNaoAprovadaException extends ErroDoSistemaException{
    public DespesaJaNaoAprovadaException() {
        super(HttpStatus.PRECONDITION_FAILED, "Despesa", "Despesa já não aprovada!");
    }
}

package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class DespesaNaoEncontradaException extends ErroDoSistemaException {
    public DespesaNaoEncontradaException(){
        super(HttpStatus.NOT_FOUND , "Despesa", "Despesa não encontrada");
    }
}

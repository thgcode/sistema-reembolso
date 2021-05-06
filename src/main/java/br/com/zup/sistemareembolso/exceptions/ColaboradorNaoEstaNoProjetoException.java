package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class ColaboradorNaoEstaNoProjetoException extends ErroDoSistemaException {
    public ColaboradorNaoEstaNoProjetoException() {
        super(HttpStatus.PRECONDITION_FAILED, "Colaborador", "Colaborador não está no projeto!");
    }
}

package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class ColaboradorNaoEAprovadorException extends ErroDoSistemaException {
    public ColaboradorNaoEAprovadorException() {
        super(HttpStatus.PRECONDITION_FAILED, "Colaborador", "Colaborador não pode aprovar uma despesa!");
    }
}

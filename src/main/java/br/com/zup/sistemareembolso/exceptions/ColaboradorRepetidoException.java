package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class ColaboradorRepetidoException extends ErroDoSistemaException {
    public ColaboradorRepetidoException() {
        super(HttpStatus.PRECONDITION_FAILED, "Colaborador", "Colaborador repetido!");
    }
}

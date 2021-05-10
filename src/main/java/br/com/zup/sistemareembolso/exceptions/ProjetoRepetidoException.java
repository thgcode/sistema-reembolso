package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class ProjetoRepetidoException extends ErroDoSistemaException{
    public ProjetoRepetidoException() {
        super(HttpStatus.PRECONDITION_FAILED, "Projeto", "Projeto repetido!");
    }
}

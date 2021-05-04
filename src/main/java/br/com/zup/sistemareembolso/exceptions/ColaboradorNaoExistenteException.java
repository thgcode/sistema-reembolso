package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class ColaboradorNaoExistenteException extends ErroDoSistemaException{

    public ColaboradorNaoExistenteException() {
        super(HttpStatus.NOT_FOUND, "Colaborador", "Colaborador não existente!");
    }
}

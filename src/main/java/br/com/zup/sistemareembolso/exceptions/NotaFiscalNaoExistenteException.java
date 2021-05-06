package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class NotaFiscalNaoExistenteException extends ErroDoSistemaException{
    public NotaFiscalNaoExistenteException() {
        super(HttpStatus.PRECONDITION_FAILED, "NotaFiscal", "Nota fiscal não existente!");
    }
}

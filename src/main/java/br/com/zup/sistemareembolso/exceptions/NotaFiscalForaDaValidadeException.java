package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class NotaFiscalForaDaValidadeException extends ErroDoSistemaException {
    public NotaFiscalForaDaValidadeException() {
        super(HttpStatus.PRECONDITION_FAILED, "NotaFiscal", "Nota fiscal fora da validade!");
    }
}

package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class PermissaoNegadaParaCriarLocalidadeException extends ErroDoSistemaException {
    public PermissaoNegadaParaCriarLocalidadeException() {
        super(HttpStatus.FORBIDDEN, "Localidade", "Permissão negada para criar localidade!");
    }
}

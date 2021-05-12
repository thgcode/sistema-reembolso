package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class PermissaoNegadaParaExcluirLocalidadeException extends ErroDoSistemaException{
    public PermissaoNegadaParaExcluirLocalidadeException() {
        super(HttpStatus.FORBIDDEN, "Localidade", "Permissão negada para excluir localidade!");
    }
}

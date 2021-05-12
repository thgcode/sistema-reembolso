package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class PermissaoNegadaParaExcluirProjetoException extends ErroDoSistemaException {
    public PermissaoNegadaParaExcluirProjetoException() {
        super(HttpStatus.FORBIDDEN, "Projeto", "Permissão negada para excluir projeto!");
    }
}

package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class PermissaoNegadaParaExcluirCategoriaException extends ErroDoSistemaException {
    public PermissaoNegadaParaExcluirCategoriaException() {
        super(HttpStatus.FORBIDDEN, "Categoria", "Permissão negada para excluir categoria!");
    }
}

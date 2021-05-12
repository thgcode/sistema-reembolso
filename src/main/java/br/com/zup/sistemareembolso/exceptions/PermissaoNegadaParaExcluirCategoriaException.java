package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class PermissaoNegadaParaExcluirCategoriaException {
    public PermissaoNegadaParaExcluirCategoriaException() {
        super(HttpStatus.FORBIDDEN, "Categoria", "Permissão negada para excluir categoria!");
    }
}

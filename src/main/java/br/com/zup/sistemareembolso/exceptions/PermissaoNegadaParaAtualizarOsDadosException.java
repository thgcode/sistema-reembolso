package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class PermissaoNegadaParaAtualizarOsDadosException extends ErroDoSistemaException {
    public PermissaoNegadaParaAtualizarOsDadosException() {
        super(HttpStatus.FORBIDDEN, "Colaborador", "Permissão negada para atualizar o colaborador");
    }
}

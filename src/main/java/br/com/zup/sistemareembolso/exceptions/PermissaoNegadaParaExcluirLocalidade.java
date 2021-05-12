package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class PermissaoNegadaParaExcluirLocalidade extends ErroDoSistemaException{
    public PermissaoNegadaParaExcluirLocalidade() {
        super(HttpStatus.FORBIDDEN, "Localidade", "Permissão negada para excluir localidade!");
    }
}

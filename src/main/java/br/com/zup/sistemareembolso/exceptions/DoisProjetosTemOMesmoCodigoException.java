package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class DoisProjetosTemOMesmoCodigoException extends ErroDoSistemaException{
    public DoisProjetosTemOMesmoCodigoException() {
        super(HttpStatus.PRECONDITION_FAILED, "Projeto", "Dois projetos tem o mesmo código!");
    }
}

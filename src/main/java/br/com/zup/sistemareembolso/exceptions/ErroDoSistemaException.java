package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class ErroDoSistemaException extends RuntimeException{
    private HttpStatus status;
    private String tipo;

    public ErroDoSistemaException(HttpStatus status, String tipo, String mensagem) {
        super(mensagem);
        this.status = status;
        this.tipo = tipo;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getTipo() {
        return tipo;
    }
}

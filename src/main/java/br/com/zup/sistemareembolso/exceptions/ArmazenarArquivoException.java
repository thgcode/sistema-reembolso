package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

import java.io.IOException;

public class ArmazenarArquivoException extends ErroDoSistemaException {
    public ArmazenarArquivoException(String nomeDoArquivo, IOException ex){
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Arquivo",   "Não foi possivel armazenar o arquivo " + nomeDoArquivo + ". Por favor tente novamente! "+ ex);
    }
}

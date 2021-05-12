package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

public class CaminhoDoArquivoInvalidoException extends ErroDoSistemaException {

    public CaminhoDoArquivoInvalidoException(String nomeDoArquivo){
        super(HttpStatus.BAD_REQUEST, "Arquivo", " Desculpe! Nome do arquivo contém sequência de caminho inválida" + nomeDoArquivo);
    }
}

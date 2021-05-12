package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpStatus;

import java.net.MalformedURLException;

public class ArquivoNaoEncontradoException extends ErroDoSistemaException {
   public ArquivoNaoEncontradoException(String nomeDoArquivo){
       super(HttpStatus.BAD_REQUEST, "Arquivo", "Arquivo não encontrado "+ nomeDoArquivo);
   }

    public ArquivoNaoEncontradoException(String nomeDoArquivo, MalformedURLException ex){
        super(HttpStatus.BAD_REQUEST, "Arquivo", "Arquivo não encontrado "+ nomeDoArquivo +" " + ex);
    }
}

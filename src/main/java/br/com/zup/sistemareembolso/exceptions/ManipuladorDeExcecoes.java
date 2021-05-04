package br.com.zup.sistemareembolso.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class ManipuladorDeExcecoes extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErroSaida> erros = new ArrayList<>();

        for (FieldError error : ex.getFieldErrors()) {
            erros.add(new ErroSaida(error.getField(), error.getDefaultMessage()));
        }

        return ResponseEntity.status(status).body(new ExcecaoDTOSaida("validacao", erros));
    }

    @ExceptionHandler({ErroDoSistemaException.class})
    public ResponseEntity <ExcecaoDTOSaida> lidaComErrosDoSistema(ErroDoSistemaException erro) {
        List <ErroSaida> listaDeErros = Arrays.asList(new ErroSaida(null, erro.getMessage()));

        return ResponseEntity.status(erro.getStatus()).body(new ExcecaoDTOSaida(erro.getTipo(), listaDeErros));
    }

}
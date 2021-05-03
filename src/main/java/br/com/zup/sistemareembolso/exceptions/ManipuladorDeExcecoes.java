package br.com.zup.sistemareembolso.exceptions;

import br.com.zup.sistemareembolso.dtos.ErroSaida;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ManipuladorDeExcecoes extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErroSaida> erros = new ArrayList<>();

        for (FieldError error : ex.getFieldErrors()) {
            erros.add(new ErroSaida(error.getField(), error.getDefaultMessage()));
        }

        return ResponseEntity.status(status).body(erros);
    }

}
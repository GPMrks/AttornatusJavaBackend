package com.attornatus.backenddevelopertest.controller;

import com.attornatus.backenddevelopertest.exception.PessoaNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class PessoaControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ProblemDetail pessoaNaoEncontrada(PessoaNaoEncontradaException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        problemDetail.setTitle("Pessoa não encontrada com este ID");
        problemDetail.setDetail("É preciso informar um ID de uma pessoa existente");
        problemDetail.setProperty("Categoria", "Pessoa");
        problemDetail.setProperty("TimeStamp", Instant.now());
        problemDetail.setProperty("Stacktrace", ex.getStackTrace());
        return problemDetail;
    }
}

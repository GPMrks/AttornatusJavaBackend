package com.attornatus.backenddevelopertest.controller;

import com.attornatus.backenddevelopertest.exception.EnderecoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
public class EnderecoControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ProblemDetail enderecoNaoEncontrado(EnderecoNaoEncontradoException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        problemDetail.setTitle("Endereço não encontrado com este ID");
        problemDetail.setDetail("É preciso informar um ID de um endereço existente");
        problemDetail.setProperty("Categoria", "Endereço");
        problemDetail.setProperty("TimeStamp", Instant.now());
        problemDetail.setProperty("Stacktrace", ex.getStackTrace());
        return problemDetail;
    }
}

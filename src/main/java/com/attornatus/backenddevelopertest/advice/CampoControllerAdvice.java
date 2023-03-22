package com.attornatus.backenddevelopertest.advice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class CampoControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail errosDeCampos(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        problemDetail.setTitle("Erro de Campo.");
        problemDetail.setDetail("É preciso informar todos os campos corretamente!");
        problemDetail.setProperty("Categoria", "Campos");
        problemDetail.setProperty("TimeStamp", Instant.now());
        problemDetail.setProperty("Stacktrace", ex.getStackTrace());
        return problemDetail;
    }
}

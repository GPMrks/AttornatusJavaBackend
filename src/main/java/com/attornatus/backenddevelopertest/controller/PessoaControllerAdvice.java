package com.attornatus.backenddevelopertest.controller;

import com.attornatus.backenddevelopertest.exception.PessoaNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class PessoaControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> pessoaNaoEncontrada(PessoaNaoEncontradaException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("status code", 404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
package com.attornatus.backenddevelopertest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EnderecoNaoEncontradoException extends RuntimeException{

    public EnderecoNaoEncontradoException(String id) {
        super("Endereço não encontrada com ID " + id);
    }
}

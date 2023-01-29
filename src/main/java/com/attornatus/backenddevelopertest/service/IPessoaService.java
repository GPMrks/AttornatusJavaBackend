package com.attornatus.backenddevelopertest.service;

import com.attornatus.backenddevelopertest.entities.Pessoa;

import java.util.List;

public interface IPessoaService {

    String generateUUID();

    List<Pessoa> listarTodasAsPessoas();

    Pessoa consultarPessoa(String id);

    Pessoa salvarPessoa(Pessoa pessoa);

    Pessoa atualizarPessoa(String id, Pessoa pessoa);

    void deletarPessoa(String id);
}

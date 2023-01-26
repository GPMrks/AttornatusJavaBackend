package com.attornatus.backenddevelopertest.service;

import com.attornatus.backenddevelopertest.entities.Pessoa;

import java.util.List;

public interface IPessoaService {

    String generateUUID();

    List<Pessoa> listaTodasAsPessoas();

    Pessoa consultaPessoa(String id);

    void salvarPessoa(Pessoa pessoa);

    Pessoa atualizaPessoa(String id, Pessoa pessoa);

    void deletaPessoa(String id);
}

package com.attornatus.backenddevelopertest.service;

import com.attornatus.backenddevelopertest.entities.Pessoa;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PessoaService {

    String generateUUID();

    @Transactional(readOnly = true)
    List<Pessoa> listarTodasAsPessoas();

    @Transactional(readOnly = true)
    Pessoa consultarPessoa(String id);

    @Transactional
    Pessoa salvarPessoa(Pessoa pessoa);

    @Transactional
    Pessoa atualizarPessoa(String id, Pessoa pessoa);

    @Transactional
    void deletarPessoa(String id);
}

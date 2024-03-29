package com.attornatus.backenddevelopertest.service;

import com.attornatus.backenddevelopertest.entities.Endereco;
import com.attornatus.backenddevelopertest.entities.Pessoa;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EnderecoService {

    String generateUUID();

    @Transactional
    Endereco salvarEndereco(Endereco endereco);

    @Transactional(readOnly = true)
    Endereco consultarPorId(int id);

    @Transactional
    void atualizarEndereco(int id, Endereco endereco);

    @Transactional
    Endereco salvarEnderecoParaPessoa(Pessoa pessoa, Endereco endereco);

    @Transactional(readOnly = true)
    List<Endereco> listarTodosOsEnderecosDaPessoa(int idPessoa);

    @Transactional
    Endereco informarEnderecoPrincipal(int idPessoa, int idEndereco);

    @Transactional(readOnly = true)
    Endereco consultarEnderecoPrincipal(int idPessoa);

}

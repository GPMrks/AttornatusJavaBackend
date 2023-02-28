package com.attornatus.backenddevelopertest.service;

import com.attornatus.backenddevelopertest.entities.Endereco;
import com.attornatus.backenddevelopertest.entities.Pessoa;

import java.util.List;

public interface IEnderecoService {

    String generateUUID();

    Endereco salvarEndereco(Endereco endereco);

    Endereco consultarPorId(String id);

    void atualizarEndereco(String id, Endereco endereco);

    Endereco salvarEnderecoParaPessoa(Pessoa pessoa, Endereco endereco);

    List<Endereco> listarTodosOsEnderecosDaPessoa(String idPessoa);

    Endereco informarEnderecoPrincipal(String idPessoa, String idEndereco);

    Endereco consultarEnderecoPrincipal(String idPessoa);

}

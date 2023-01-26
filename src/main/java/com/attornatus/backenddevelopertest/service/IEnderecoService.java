package com.attornatus.backenddevelopertest.service;

import com.attornatus.backenddevelopertest.entities.Endereco;
import com.attornatus.backenddevelopertest.entities.Pessoa;

import java.util.List;

public interface IEnderecoService {

    String generateUUID();

    void salvarEndereco(Endereco endereco);

    void atualizaEndereco(String id, Endereco endereco);

    void salvarEnderecoParaPessoa(Pessoa pessoa, Endereco endereco);

    List<Endereco> listaTodosOsEnderecosDaPessoa(String idPessoa);

    Endereco informarEnderecoPrincipal(String idPessoa, String idEndereco);

    Endereco consultarEnderecoPrincipal(String idPessoa);

}

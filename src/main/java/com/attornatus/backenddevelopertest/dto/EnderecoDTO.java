package com.attornatus.backenddevelopertest.dto;

import org.springframework.hateoas.RepresentationModel;

public class EnderecoDTO extends RepresentationModel<EnderecoDTO> {

    private int publicIdEndereco;
    private int publicIdPessoa;
    private String logradouro;
    private String cep;
    private int numero;
    private String cidade;
    private boolean principal;

    public EnderecoDTO(int publicIdEndereco, int publicIdPessoa, String logradouro, String cep, int numero, String cidade, boolean principal) {
        this.publicIdEndereco = publicIdEndereco;
        this.publicIdPessoa = publicIdPessoa;
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.principal = principal;
    }

    public int getPublicIdEndereco() {
        return publicIdEndereco;
    }

    public int getPublicIdPessoa() {
        return publicIdPessoa;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getCep() {
        return cep;
    }

    public int getNumero() {
        return numero;
    }

    public String getCidade() {
        return cidade;
    }

    public boolean isPrincipal() {
        return principal;
    }
}

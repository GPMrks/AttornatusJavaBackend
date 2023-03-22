package com.attornatus.backenddevelopertest.dto;

import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.List;

public class PessoaDTO extends RepresentationModel<PessoaDTO> {

    private int publicId;
    private String nome;
    private LocalDate dataNascimento;
    private List<EnderecoDTO> enderecos;

    public PessoaDTO(int publicId, String nome, LocalDate dataNascimento, List<EnderecoDTO> enderecos) {
        this.publicId = publicId;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.enderecos = enderecos;
    }

    public int getPublicId() {
        return publicId;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public List<EnderecoDTO> getEnderecos() {
        return enderecos;
    }
}

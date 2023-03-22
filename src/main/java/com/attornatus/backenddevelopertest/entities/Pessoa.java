package com.attornatus.backenddevelopertest.entities;

import com.attornatus.backenddevelopertest.dto.EnderecoDTO;
import com.attornatus.backenddevelopertest.dto.PessoaDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Pessoa {

    private static int counter = 0;

    private int publicIdPessoa;

    @Id
    private String id;

    @NotNull
    @NotBlank
    private String nome;

    @Past(message = "Data não pode ser maior que a atual!")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @NotEmpty
    @OneToMany(fetch = FetchType.EAGER)
    private List<Endereco> enderecos;

    public Pessoa() {

    }

    public Pessoa(String id, String nome, LocalDate dataNascimento, List<Endereco> enderecos) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.enderecos = enderecos;
    }

    public int getPublicIdPessoa() {
        return publicIdPessoa;
    }

    public void setPublicIdPessoa(int publicIdPessoa) {
        this.publicIdPessoa = ++counter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public Endereco adicionaEndereco(Endereco endereco) {
        this.enderecos.add(endereco);
        return endereco;
    }

    public PessoaDTO toDto() {
        List<EnderecoDTO> enderecoDTOS = new ArrayList<>();
        for (Endereco endereco : enderecos) {
            enderecoDTOS.add(endereco.toDto());
        }
        return new PessoaDTO(publicIdPessoa ,nome, dataNascimento, enderecoDTOS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(nome, pessoa.nome) && Objects.equals(dataNascimento, pessoa.dataNascimento) && Objects.equals(enderecos, pessoa.enderecos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, dataNascimento, enderecos);
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", enderecos=" + enderecos.toString() +
                '}';
    }
}

package com.attornatus.backenddevelopertest.entities;

import com.attornatus.backenddevelopertest.dto.EnderecoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Objects;

@Entity
public class Endereco {

    private static int counter = 0;

    private int publicIdEndereco;

    private int publicIdPessoa;

    @Id
    private String id;
    private String idPessoa;

    @NotNull
    @NotBlank
    private String logradouro;

    @NotNull
    @NotBlank
    private String cep;

    @NotNull
    @Positive
    private int numero;

    @NotNull
    @NotBlank
    private String cidade;

    @NotNull
    private boolean principal;

    public Endereco() {

    }

    public Endereco(int publicIdPessoa, String id, String idPessoa, String logradouro, String cep, int numero, String cidade, boolean principal) {
        this.publicIdPessoa = publicIdPessoa;
        this.id = id;
        this.idPessoa = idPessoa;
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.principal = principal;
    }

    public int getPublicIdEndereco() {
        return publicIdEndereco;
    }

    public void setPublicIdEndereco(int publicIdEndereco) {
        this.publicIdEndereco = ++counter;
    }

    public int getPublicIdPessoa() {
        return publicIdPessoa;
    }

    public void setPublicIdPessoa(int publicIdPessoa) {
        this.publicIdPessoa = publicIdPessoa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(String idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public EnderecoDTO toDto() {
        return new EnderecoDTO(publicIdEndereco, publicIdPessoa, logradouro, cep, numero, cidade, principal);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return numero == endereco.numero && principal == endereco.principal && Objects.equals(logradouro, endereco.logradouro) && Objects.equals(cep, endereco.cep) && Objects.equals(cidade, endereco.cidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logradouro, cep, numero, cidade, principal);
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "logradouro='" + logradouro + '\'' +
                ", cep='" + cep + '\'' +
                ", numero=" + numero +
                ", cidade='" + cidade + '\'' +
                ", principal=" + principal +
                '}';
    }
}

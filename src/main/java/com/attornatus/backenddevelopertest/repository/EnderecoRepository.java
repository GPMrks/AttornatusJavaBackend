package com.attornatus.backenddevelopertest.repository;

import com.attornatus.backenddevelopertest.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    @Query("FROM Endereco e WHERE e.publicIdEndereco = :id")
    Endereco consultarEnderecoPorIdPublico(int id);

    @Query("FROM Endereco e WHERE e.publicIdPessoa = :idPessoa")
    List<Endereco> listarEnderecosPorPessoa(int idPessoa);

    @Query("FROM Endereco e WHERE e.publicIdPessoa = :idPessoa AND e.principal = true")
    Endereco consultarEnderecoPrincipal(int idPessoa);
}

package com.attornatus.backenddevelopertest.repository;

import com.attornatus.backenddevelopertest.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, String> {

    @Query("FROM Endereco e WHERE e.idPessoa = :idPessoa")
    List<Endereco> listarEnderecosPorPessoa(String idPessoa);

    @Query("FROM Endereco e WHERE e.idPessoa = :idPessoa AND e.principal = true")
    Endereco concutarEnderecoPrincipal(String idPessoa);
}

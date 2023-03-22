package com.attornatus.backenddevelopertest.repository;

import com.attornatus.backenddevelopertest.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    @Query("FROM Pessoa p WHERE p.publicIdPessoa = :idPessoa")
    Pessoa consultarPorIdPublico(int idPessoa);

    @Modifying
    @Query("DELETE FROM Pessoa p WHERE p.publicIdPessoa = :idPessoa")
    void excluirPorIdPublico(@Param("idPessoa") int idPessoa);
}

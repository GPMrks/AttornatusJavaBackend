package com.attornatus.backenddevelopertest.repository;

import com.attornatus.backenddevelopertest.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, String> {
}

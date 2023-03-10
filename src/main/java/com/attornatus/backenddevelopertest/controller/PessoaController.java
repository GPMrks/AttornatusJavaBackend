package com.attornatus.backenddevelopertest.controller;

import com.attornatus.backenddevelopertest.entities.Pessoa;
import com.attornatus.backenddevelopertest.hateoas.PessoaHateoas;
import com.attornatus.backenddevelopertest.service.impl.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
@Tag(name = "Pessoas Controller")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    @Operation(summary = "Listar todas as pessoas", description = "Realiza a listagem de todas as pessoas cadastradas.")
    public ResponseEntity<List<Pessoa>> listarTodasAsPessoas() {
        List<Pessoa> listaDeTodasAsPessoas = pessoaService.listarTodasAsPessoas();
        PessoaHateoas.toHateoasList(listaDeTodasAsPessoas);
        return ResponseEntity.ok().body(listaDeTodasAsPessoas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar pessoa por ID", description = "Realiza a consulta de uma pessoa por seu ID.")
    public ResponseEntity<Pessoa> consultarPessoaPorID(@PathVariable String id) {
        Pessoa pessoa = pessoaService.consultarPessoa(id);
        PessoaHateoas.toHateoas(id, pessoa);
        return ResponseEntity.ok().body(pessoa);
    }

    @PostMapping
    @Operation(summary = "Cadastrar pessoa", description = "Realiza o cadastro da pessoa na base de dados.")
    public ResponseEntity<Pessoa> salvarPessoa(@RequestBody Pessoa pessoa) {
        Pessoa pessoaSalva = pessoaService.salvarPessoa(pessoa);
        PessoaHateoas.toHateoas(pessoaSalva.getId(), pessoaSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cadastro da pessoa", description = "Realiza a atualiza????o do cadastro da pessoa.")
    public ResponseEntity<Pessoa> atualizarPessoa(@PathVariable String id, @RequestBody Pessoa pessoa) {
        Pessoa pessoaAtualizada = pessoaService.atualizarPessoa(id, pessoa);
        PessoaHateoas.toHateoas(id, pessoaAtualizada);
        return ResponseEntity.ok().body(pessoaAtualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pessoa", description = "Realiza a exclus??o da pessoa da base de dados.")
    public ResponseEntity<Void> excluirPessoa(@PathVariable String id) {
        pessoaService.deletarPessoa(id);
        return ResponseEntity.noContent().build();
    }
}

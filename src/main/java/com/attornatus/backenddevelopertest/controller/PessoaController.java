package com.attornatus.backenddevelopertest.controller;

import com.attornatus.backenddevelopertest.entities.Pessoa;
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
@CrossOrigin(origins = "https://gpmrks.github.io/AttornatusJavaBackend/")
@Tag(name = "Pessoas Controller")
public class PessoaController {

    private PessoaService pessoaService;

    @Autowired
    public void setPessoaService(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    @Operation(summary = "Listar todas as pessoas", description = "Realiza a listagem de todas as pessoas cadastradas.")
    public ResponseEntity<List<Pessoa>> listarTodasAsPessoas() {
        List<Pessoa> listaDeTodasAsPessoas = pessoaService.listaTodasAsPessoas();
        return ResponseEntity.ok().body(listaDeTodasAsPessoas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar pessoa por ID", description = "Realiza a consulta de uma pessoa por seu ID.")
    public ResponseEntity<Pessoa> consultarPessoaPorID(@PathVariable String id) {
        Pessoa pessoa = pessoaService.consultaPessoa(id);
        return ResponseEntity.ok().body(pessoa);
    }

    @PostMapping
    @Operation(summary = "Cadastrar pessoa", description = "Realiza o cadastro da pessoa na base de dados.")
    public ResponseEntity<Pessoa> salvaPessoa(@RequestBody Pessoa pessoa) {
        pessoaService.salvarPessoa(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cadastro da pessoa", description = "Realiza a atualização do cadastro da pessoa.")
    public ResponseEntity<Pessoa> atualizaPessoa(@PathVariable String id, @RequestBody Pessoa pessoa) {
        Pessoa pessoaAtualizada = pessoaService.atualizaPessoa(id, pessoa);
        return ResponseEntity.ok().body(pessoaAtualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pessoa", description = "Realiza a exclusão da pessoa da base de dados.")
    public ResponseEntity<Void> excluiPessoa(@PathVariable String id){
        pessoaService.deletaPessoa(id);
        return ResponseEntity.noContent().build();
    }

}

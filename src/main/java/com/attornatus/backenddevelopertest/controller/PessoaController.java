package com.attornatus.backenddevelopertest.controller;

import com.attornatus.backenddevelopertest.dto.PessoaDTO;
import com.attornatus.backenddevelopertest.entities.Pessoa;
import com.attornatus.backenddevelopertest.hateoas.PessoaHateoas;
import com.attornatus.backenddevelopertest.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
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
    public ResponseEntity<List<PessoaDTO>> listarTodasAsPessoas() {
        List<Pessoa> listaDeTodasAsPessoas = pessoaService.listarTodasAsPessoas();
        List<PessoaDTO> listaDeTodasAsPessoasDTO = new ArrayList<>();
        for (Pessoa pessoa : listaDeTodasAsPessoas) {
            listaDeTodasAsPessoasDTO.add(pessoa.toDto());
        }
        PessoaHateoas.toHateoasList(listaDeTodasAsPessoasDTO);
        return ResponseEntity.ok().body(listaDeTodasAsPessoasDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar pessoa por ID", description = "Realiza a consulta de uma pessoa por seu ID.")
    public ResponseEntity<PessoaDTO> consultarPessoaPorID(@PathVariable int id) {
        Pessoa pessoa = pessoaService.consultarPessoaPorId(id);
        PessoaDTO pessoaDTO = pessoa.toDto();
        PessoaHateoas.toHateoas(pessoaDTO.getPublicId(), pessoaDTO);
        return ResponseEntity.ok().body(pessoaDTO);
    }

    @PostMapping
    @Operation(summary = "Cadastrar pessoa", description = "Realiza o cadastro da pessoa na base de dados.")
    public ResponseEntity<PessoaDTO> salvarPessoa(@RequestBody @Valid Pessoa pessoa, UriComponentsBuilder uriComponentsBuilder) {
        Pessoa pessoaSalva = pessoaService.salvarPessoa(pessoa);
        PessoaDTO pessoaDTO = pessoaSalva.toDto();
        PessoaHateoas.toHateoas(pessoaDTO.getPublicId(), pessoaDTO);
        URI uri = uriComponentsBuilder.path("/pessoas/{id}").buildAndExpand(pessoaDTO.getPublicId()).toUri();
        return ResponseEntity.created(uri).body(pessoaDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cadastro da pessoa", description = "Realiza a atualização do cadastro da pessoa.")
    public ResponseEntity<PessoaDTO> atualizarPessoa(@PathVariable int id, @RequestBody Pessoa pessoa) {
        Pessoa pessoaAtualizada = pessoaService.atualizarPessoa(id, pessoa);
        PessoaDTO pessoaDTO = pessoaAtualizada.toDto();
        PessoaHateoas.toHateoas(pessoaDTO.getPublicId(), pessoaDTO);
        return ResponseEntity.ok().body(pessoaDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pessoa", description = "Realiza a exclusão da pessoa da base de dados.")
    public ResponseEntity<Void> excluirPessoa(@PathVariable int id) {
        pessoaService.deletarPessoa(id);
        return ResponseEntity.noContent().build();
    }
}

package com.attornatus.backenddevelopertest.controller;

import com.attornatus.backenddevelopertest.entities.Endereco;
import com.attornatus.backenddevelopertest.hateoas.EnderecoHateoas;
import com.attornatus.backenddevelopertest.service.EnderecoService;
import com.attornatus.backenddevelopertest.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pessoas/{idPessoa}")
@Tag(name = "Endereços Controller")
public class EnderecoController {

    private final PessoaService pessoaService;

    private final EnderecoService enderecoService;

    @Autowired
    public EnderecoController(PessoaService pessoaService, EnderecoService enderecoService) {
        this.pessoaService = pessoaService;
        this.enderecoService = enderecoService;
    }

    @GetMapping("/enderecos")
    @Operation(summary = "Listar todos os endereços da pessoa", description = "Listagem de todos os endereços da pessoa")
    public ResponseEntity<List<Endereco>> listarTodosOsEnderecosDaPessoa(@PathVariable String idPessoa) {
        List<Endereco> enderecos = enderecoService.listarTodosOsEnderecosDaPessoa(idPessoa);
        EnderecoHateoas.toHateoasList(enderecos);
        return ResponseEntity.ok().body(enderecos);
    }

    @GetMapping("/enderecos/{idEndereco}")
    @Operation(summary = "Consultar endereço por ID", description = "Consulta o endereço da pessoa pelo ID do endereço.")
    public ResponseEntity<Endereco> consultarEnderecoPorID(@PathVariable String idPessoa, @PathVariable String idEndereco){
        Endereco endereco = enderecoService.consultarPorId(idEndereco);
        EnderecoHateoas.toHateoas(endereco.getIdPessoa(), endereco.getId(), endereco);
        return ResponseEntity.ok().body(endereco);
    }

    @GetMapping("/endereco-principal")
    @Operation(summary = "Consultar endereço principal", description = "Consulta o endereço principal da pessoa.")
    public ResponseEntity<Endereco> consultarEnderecoPrincipal(@PathVariable String idPessoa){
        Endereco enderecoPrincipal = enderecoService.consultarEnderecoPrincipal(idPessoa);
        EnderecoHateoas.toHateoas(enderecoPrincipal.getIdPessoa(), enderecoPrincipal.getId(), enderecoPrincipal);
        return ResponseEntity.ok().body(enderecoPrincipal);
    }

    @PostMapping("/enderecos")
    @Operation(summary = "Cadastrar endereço para a pessoa", description = "Realiza o cadastro do endereço na lista de endereços da pessoa.")
    public ResponseEntity<Endereco> criarEnderecoParaPessoa(@PathVariable String idPessoa, @RequestBody Endereco endereco, UriComponentsBuilder uriComponentsBuilder) {
        Endereco enderecoParaPessoa = enderecoService.salvarEnderecoParaPessoa(pessoaService.consultarPessoa(idPessoa), endereco);
        EnderecoHateoas.toHateoas(enderecoParaPessoa.getIdPessoa(), enderecoParaPessoa.getId(), enderecoParaPessoa);
        URI uri = uriComponentsBuilder.path("/pessoas/{idPessoa}/enderecos/{idEndereco}").buildAndExpand(enderecoParaPessoa.getIdPessoa(), enderecoParaPessoa.getId()).toUri();
        return ResponseEntity.created(uri).body(enderecoParaPessoa);
    }

    @PutMapping("/endereco-principal/{idEndereco}")
    @Operation(summary = "Informar endereço principal", description = "Informar qual será o endereço principal da pessoa, baseado nos endereços cadastrados.")
    public ResponseEntity<Endereco> informarEnderecoPrincipal(@PathVariable String idPessoa, @PathVariable String idEndereco) {
        Endereco enderecoPrincipal = enderecoService.informarEnderecoPrincipal(idPessoa, idEndereco);
        EnderecoHateoas.toHateoas(enderecoPrincipal.getIdPessoa(), enderecoPrincipal.getId(), enderecoPrincipal);
        return ResponseEntity.ok().body(enderecoPrincipal);
    }
}

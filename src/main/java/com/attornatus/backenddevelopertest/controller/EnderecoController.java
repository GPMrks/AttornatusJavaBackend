package com.attornatus.backenddevelopertest.controller;

import com.attornatus.backenddevelopertest.dto.EnderecoDTO;
import com.attornatus.backenddevelopertest.entities.Endereco;
import com.attornatus.backenddevelopertest.hateoas.EnderecoHateoas;
import com.attornatus.backenddevelopertest.service.EnderecoService;
import com.attornatus.backenddevelopertest.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
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
    public ResponseEntity<List<EnderecoDTO>> listarTodosOsEnderecosDaPessoa(@PathVariable int idPessoa) {
        List<Endereco> enderecos = enderecoService.listarTodosOsEnderecosDaPessoa(idPessoa);
        List<EnderecoDTO> enderecoDTOS = new ArrayList<>();
        for (Endereco endereco : enderecos) {
            enderecoDTOS.add(endereco.toDto());
        }
        EnderecoHateoas.toHateoasList(enderecoDTOS);
        return ResponseEntity.ok().body(enderecoDTOS);
    }

    @GetMapping("/enderecos/{idEndereco}")
    @Operation(summary = "Consultar endereço por ID", description = "Consulta o endereço da pessoa pelo ID do endereço.")
    public ResponseEntity<EnderecoDTO> consultarEnderecoPorID(@PathVariable int idPessoa, @PathVariable int idEndereco){
        Endereco endereco = enderecoService.consultarPorId(idEndereco);
        EnderecoDTO enderecoDTO = endereco.toDto();
        EnderecoHateoas.toHateoas(enderecoDTO.getPublicIdPessoa(), enderecoDTO.getPublicIdEndereco(), enderecoDTO);
        return ResponseEntity.ok().body(enderecoDTO);
    }

    @GetMapping("/endereco-principal")
    @Operation(summary = "Consultar endereço principal", description = "Consulta o endereço principal da pessoa.")
    public ResponseEntity<EnderecoDTO> consultarEnderecoPrincipal(@PathVariable int idPessoa){
        Endereco enderecoPrincipal = enderecoService.consultarEnderecoPrincipal(idPessoa);
        EnderecoDTO enderecoPrincipalDTO = enderecoPrincipal.toDto();
        EnderecoHateoas.toHateoas(enderecoPrincipalDTO.getPublicIdPessoa(), enderecoPrincipalDTO.getPublicIdEndereco(), enderecoPrincipalDTO);
        return ResponseEntity.ok().body(enderecoPrincipalDTO);
    }

    @PostMapping("/enderecos")
    @Operation(summary = "Cadastrar endereço para a pessoa", description = "Realiza o cadastro do endereço na lista de endereços da pessoa.")
    public ResponseEntity<EnderecoDTO> cadastrarEnderecoParaPessoa(@PathVariable int idPessoa, @RequestBody @Valid Endereco endereco, UriComponentsBuilder uriComponentsBuilder) {
        Endereco enderecoParaPessoa = enderecoService.salvarEnderecoParaPessoa(pessoaService.consultarPessoaPorId(idPessoa), endereco);
        EnderecoDTO enderecoParaPessoaDTO = enderecoParaPessoa.toDto();
        EnderecoHateoas.toHateoas(enderecoParaPessoaDTO.getPublicIdPessoa(), enderecoParaPessoaDTO.getPublicIdEndereco(), enderecoParaPessoaDTO);
        URI uri = uriComponentsBuilder.path("/pessoas/{idPessoa}/enderecos/{idEndereco}").buildAndExpand(enderecoParaPessoaDTO.getPublicIdPessoa(), enderecoParaPessoaDTO.getPublicIdEndereco()).toUri();
        return ResponseEntity.created(uri).body(enderecoParaPessoaDTO);
    }

    @PutMapping("/endereco-principal/{idEndereco}")
    @Operation(summary = "Informar endereço principal", description = "Informar qual será o endereço principal da pessoa, baseado nos endereços cadastrados.")
    public ResponseEntity<EnderecoDTO> informarEnderecoPrincipal(@PathVariable int idPessoa, @PathVariable int idEndereco) {
        Endereco enderecoPrincipal = enderecoService.informarEnderecoPrincipal(idPessoa, idEndereco);
        EnderecoDTO enderecoPrincipalDTO = enderecoPrincipal.toDto();
        EnderecoHateoas.toHateoas(enderecoPrincipalDTO.getPublicIdPessoa(), enderecoPrincipalDTO.getPublicIdEndereco(), enderecoPrincipalDTO);
        return ResponseEntity.ok().body(enderecoPrincipalDTO);
    }
}

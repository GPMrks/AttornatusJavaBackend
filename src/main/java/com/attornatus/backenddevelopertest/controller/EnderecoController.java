package com.attornatus.backenddevelopertest.controller;

import com.attornatus.backenddevelopertest.entities.Endereco;
import com.attornatus.backenddevelopertest.service.impl.EnderecoService;
import com.attornatus.backenddevelopertest.service.impl.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/${api.version}/pessoas/{idPessoa}")
@CrossOrigin("/pessoas")
@Tag(name = "Endereços Controller")
public class EnderecoController {

    private PessoaService pessoaService;

    @Autowired
    public void setPessoaService(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    private EnderecoService enderecoService;

    @Autowired
    public void setEnderecoService(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/enderecos")
    @Operation(summary = "Listar todos os endereços da pessoa", description = "Listagem de todos os endereços da pessoa")
    public ResponseEntity<List<Endereco>> listarTodosOsEnderecosDaPessoa(@PathVariable String idPessoa) {
        List<Endereco> enderecos = enderecoService.listaTodosOsEnderecosDaPessoa(idPessoa);
        return ResponseEntity.ok().body(enderecos);
    }

    @GetMapping("/endereco-principal")
    @Operation(summary = "Consultar endereço principal", description = "Consulta o endereço principal da pessoa.")
    public ResponseEntity<Endereco> consultarEnderecoPrincipal(@PathVariable String idPessoa){
        Endereco enderecoPrincipal = enderecoService.consultarEnderecoPrincipal(idPessoa);
        return ResponseEntity.ok().body(enderecoPrincipal);
    }

    @PostMapping("/enderecos")
    @Operation(summary = "Cadastrar endereço para a pessoa", description = "Realiza o cadastro do endereço na lista de endereços da pessoa.")
    public ResponseEntity<Endereco> criarEnderecoParaPessoa(@PathVariable String idPessoa, @RequestBody Endereco endereco) {
        enderecoService.salvarEnderecoParaPessoa(pessoaService.consultaPessoa(idPessoa), endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
    }

    @PostMapping("/endereco-principal/{idEndereco}")
    @Operation(summary = "Informar endereço principal", description = "Informar qual será o endereço principal da pessoa, baseado nos endereços cadastrados.")
    public ResponseEntity<Endereco> informarEnderecoPrincipal(@PathVariable String idPessoa, @PathVariable String idEndereco) {
        Endereco enderecoPrincipal = enderecoService.informarEnderecoPrincipal(idPessoa, idEndereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoPrincipal);
    }
}

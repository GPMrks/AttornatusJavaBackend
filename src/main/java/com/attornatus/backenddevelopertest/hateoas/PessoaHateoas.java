package com.attornatus.backenddevelopertest.hateoas;

import com.attornatus.backenddevelopertest.controller.PessoaController;
import com.attornatus.backenddevelopertest.entities.Endereco;
import com.attornatus.backenddevelopertest.entities.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PessoaHateoas {

    private final EnderecoHateoas enderecoHateoas;

    @Autowired
    public PessoaHateoas(EnderecoHateoas enderecoHateoas) {
        this.enderecoHateoas = enderecoHateoas;
    }

    public static void toHateoas(String id, Pessoa pessoa) {
        pessoa.add(linkTo(methodOn(PessoaController.class).consultarPessoaPorID(id)).withSelfRel());
        pessoa.add(linkTo(methodOn(PessoaController.class).listarTodasAsPessoas()).withRel(IanaLinkRelations.COLLECTION));
        for (Endereco endereco : pessoa.getEnderecos()) {
            EnderecoHateoas.toHateoas(pessoa.getId(), endereco.getId(), endereco);
        }
    }

    public static void toHateoasList(List<Pessoa> listaDeTodasAsPessoas) {
        for (Pessoa pessoa : listaDeTodasAsPessoas) {
            PessoaHateoas.toHateoas(pessoa.getId(), pessoa);
        }
    }
}

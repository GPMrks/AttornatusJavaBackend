package com.attornatus.backenddevelopertest.hateoas;

import com.attornatus.backenddevelopertest.controller.EnderecoController;
import com.attornatus.backenddevelopertest.entities.Endereco;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EnderecoHateoas {

    public static void toHateoas(String idPessoa, String id, Endereco endereco) {
        endereco.add(linkTo(methodOn(EnderecoController.class).consultarEnderecoPorID(idPessoa, id)).withSelfRel());
        endereco.add(linkTo(methodOn(EnderecoController.class).listarTodosOsEnderecosDaPessoa(idPessoa)).withRel(IanaLinkRelations.COLLECTION));
    }

    public static void toHateoasList(List<Endereco> listaDeTodosOsEnderecos) {
        for (Endereco endereco : listaDeTodosOsEnderecos) {
            EnderecoHateoas.toHateoas(endereco.getIdPessoa(), endereco.getId(), endereco);
        }
    }

}

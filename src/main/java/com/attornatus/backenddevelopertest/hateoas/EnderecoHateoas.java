package com.attornatus.backenddevelopertest.hateoas;

import com.attornatus.backenddevelopertest.controller.EnderecoController;
import com.attornatus.backenddevelopertest.dto.EnderecoDTO;
import com.attornatus.backenddevelopertest.entities.Endereco;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EnderecoHateoas {

    public static void toHateoas(int idPessoa, int id, EnderecoDTO enderecoDTO) {
        enderecoDTO.add(linkTo(methodOn(EnderecoController.class).consultarEnderecoPorID(idPessoa, id)).withSelfRel());
        enderecoDTO.add(linkTo(methodOn(EnderecoController.class).listarTodosOsEnderecosDaPessoa(idPessoa)).withRel(IanaLinkRelations.COLLECTION));
    }

    public static void toHateoasList(List<EnderecoDTO> listaDeTodosOsEnderecos) {
        for (EnderecoDTO enderecoDTO : listaDeTodosOsEnderecos) {
            EnderecoHateoas.toHateoas(enderecoDTO.getPublicIdPessoa(), enderecoDTO.getPublicIdEndereco(), enderecoDTO);
        }
    }

}

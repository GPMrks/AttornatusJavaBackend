package com.attornatus.backenddevelopertest.hateoas;

import com.attornatus.backenddevelopertest.controller.PessoaController;
import com.attornatus.backenddevelopertest.dto.EnderecoDTO;
import com.attornatus.backenddevelopertest.dto.PessoaDTO;
import com.attornatus.backenddevelopertest.entities.Endereco;
import com.attornatus.backenddevelopertest.entities.Pessoa;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PessoaHateoas {

    public static void toHateoas(int id, PessoaDTO pessoaDTO) {
        pessoaDTO.add(linkTo(methodOn(PessoaController.class).consultarPessoaPorID(id)).withSelfRel());
        pessoaDTO.add(linkTo(methodOn(PessoaController.class).listarTodasAsPessoas()).withRel(IanaLinkRelations.COLLECTION));
        for (EnderecoDTO enderecoDTO : pessoaDTO.getEnderecos()) {
            EnderecoHateoas.toHateoas(pessoaDTO.getPublicId(), enderecoDTO.getPublicIdEndereco(), enderecoDTO);
        }
    }

    public static void toHateoasList(List<PessoaDTO> listaDeTodasAsPessoas) {
        for (PessoaDTO pessoaDTO : listaDeTodasAsPessoas) {
            PessoaHateoas.toHateoas(pessoaDTO.getPublicId(), pessoaDTO);
        }
    }
}

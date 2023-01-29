package com.attornatus.backenddevelopertest.service;

import com.attornatus.backenddevelopertest.entities.Endereco;
import com.attornatus.backenddevelopertest.entities.Pessoa;
import com.attornatus.backenddevelopertest.exception.EnderecoNaoEncontradoException;
import com.attornatus.backenddevelopertest.exception.PessoaNaoEncontradaException;
import com.attornatus.backenddevelopertest.repository.EnderecoRepository;
import com.attornatus.backenddevelopertest.repository.PessoaRepository;
import com.attornatus.backenddevelopertest.service.impl.EnderecoService;
import com.attornatus.backenddevelopertest.service.impl.PessoaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockRestServiceServer
@ExtendWith(SpringExtension.class)
public class EnderecoServiceTest {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private EnderecoService enderecoService;

    @Test
    void whenSaveAddressThenCheckIfAddressWasCreated() {

        //given
        Pessoa pessoaExpected = getPessoa();
        Endereco enderecoExpected = getEndereco();

        //when
        Pessoa pessoa = pessoaService.salvarPessoa(pessoaExpected);
        Endereco endereco = enderecoService.salvarEnderecoParaPessoa(pessoa, enderecoExpected);

        //then
        assertThat(endereco).isNotNull();
        assertEquals(endereco.toString(), enderecoService.listarTodosOsEnderecosDaPessoa(pessoa.getId()).get(0).toString());
    }

    @Test
    void whenSaveAddressToAddressRepositoryThenCheckIfAddressWasCreated() {

        //given
        Endereco enderecoExpected = getEndereco();

        //when
        Endereco endereco = enderecoService.salvarEndereco(enderecoExpected);

        //then
        assertThat(endereco).isNotNull();
        assertEquals(endereco.getId(), enderecoRepository.findById(endereco.getId()).get().getId());
    }

    @Test
    void whenGetAllAddressessThenCheckIfReturnedAListOfAllAddresses() {

        //given
        Pessoa pessoaExpected = getPessoa();
        Endereco endereco1 = getEndereco();
        Endereco endereco2 = getEndereco();
        Endereco endereco3 = getEndereco();

        //when
        Pessoa pessoa = pessoaService.salvarPessoa(pessoaExpected);
        enderecoService.salvarEnderecoParaPessoa(pessoa, endereco1);
        enderecoService.salvarEnderecoParaPessoa(pessoa, endereco2);
        enderecoService.salvarEnderecoParaPessoa(pessoa, endereco3);

        //then
        assertThat(enderecoService.listarTodosOsEnderecosDaPessoa(pessoa.getId())).isNotNull();
        assertEquals(3, enderecoService.listarTodosOsEnderecosDaPessoa(pessoa.getId()).size());
    }

    @Test
    void whenGetMainAddressessThenCheckIfReturnedTheRight() {

        //given
        Pessoa pessoaExpected = getPessoa();
        Endereco endereco1 = getEndereco();
        Endereco endereco2 = getEndereco();
        endereco2.setLogradouro("Rua Jane Doe");
        endereco2.setCep("40000000");
        endereco2.setNumero(400);
        endereco2.setCidade("Test City");


        //when
        Pessoa pessoa = pessoaService.salvarPessoa(pessoaExpected);
        enderecoService.salvarEnderecoParaPessoa(pessoa, endereco1);
        enderecoService.salvarEnderecoParaPessoa(pessoa, endereco2);
        enderecoService.informarEnderecoPrincipal(pessoa.getId(), endereco2.getId());

        //then
        assertTrue(enderecoService.listarTodosOsEnderecosDaPessoa(pessoa.getId()).get(1).isPrincipal());
        assertEquals(enderecoRepository.findById(endereco2.getId()).get() ,enderecoService.consultarEnderecoPrincipal(pessoa.getId()));
    }

    @Test
    void whenGetAddressThatIdDoesntExistsThenThrowEnderecoNaoExisteException() {

        //given
        Pessoa pessoa = getPessoa();

        //when
        pessoaService.salvarPessoa(pessoa);

        //then
        assertThrows(EnderecoNaoEncontradoException.class, () -> enderecoService.informarEnderecoPrincipal(pessoa.getId(), "IDNULL"));
    }

    @Test
    void whenUpdateAddressThenCheckIfDataWasChanged() {

        //given
        Endereco enderecoToBeUpdated = getEndereco();
        Pessoa pessoa = getPessoa();
        pessoaService.salvarPessoa(pessoa);
        enderecoService.salvarEndereco(enderecoToBeUpdated);
        enderecoService.salvarEnderecoParaPessoa(pessoa, enderecoToBeUpdated);

        //when
        enderecoToBeUpdated.setLogradouro("Rua Jimmy Doe");
        enderecoService.atualizarEndereco(enderecoToBeUpdated.getId(), enderecoToBeUpdated);
        pessoaService.salvarPessoa(pessoa);

        //then
        assertEquals("Rua Jimmy Doe", enderecoService.listarTodosOsEnderecosDaPessoa(pessoa.getId()).get(0).getLogradouro());
    }

    private Pessoa getPessoa() {

        Pessoa pessoaExpected = new Pessoa();
        pessoaExpected.setNome("John Doe");
        pessoaExpected.setDataNascimento(LocalDate.of(2000, 1, 1));
        pessoaExpected.setEnderecos(new ArrayList<Endereco>());

        return pessoaExpected;
    }

    private Endereco getEndereco() {

        Endereco enderecoExpected = new Endereco();
        enderecoExpected.setLogradouro("Rua John Doe");
        enderecoExpected.setCep("30000000");
        enderecoExpected.setNumero(300);
        enderecoExpected.setCidade("Test City");

        return enderecoExpected;
    }
}

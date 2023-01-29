package com.attornatus.backenddevelopertest.service;

import com.attornatus.backenddevelopertest.entities.Endereco;
import com.attornatus.backenddevelopertest.entities.Pessoa;
import com.attornatus.backenddevelopertest.exception.PessoaNaoEncontradaException;
import com.attornatus.backenddevelopertest.repository.EnderecoRepository;
import com.attornatus.backenddevelopertest.repository.PessoaRepository;
import com.attornatus.backenddevelopertest.service.impl.EnderecoService;
import com.attornatus.backenddevelopertest.service.impl.PessoaService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockRestServiceServer
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PessoaServiceTest {

    @SpyBean
    private PessoaRepository pessoaRepository;

    @SpyBean
    private EnderecoRepository enderecoRepository;

    @SpyBean
    private PessoaService pessoaService;

    @SpyBean
    private EnderecoService enderecoService;

    @Test
    void whenGetAllPersonThenCheckIfListOfPersonWasReturned() {

        //given
        Pessoa pessoa1 = getPessoa();
        Pessoa pessoa2 = getPessoa();
        Pessoa pessoa3 = getPessoa();

        //when
        pessoaService.salvarPessoa(pessoa1);
        pessoaService.salvarPessoa(pessoa2);
        pessoaService.salvarPessoa(pessoa3);

        //then
        assertEquals(3, pessoaService.listarTodasAsPessoas().size());
    }


    @Test
    void whenGetPersonThenCheckIfPersonWasReturned() {

        //given
        Pessoa pessoaExpected = getPessoa();

        //when
        Pessoa pessoa = pessoaService.salvarPessoa(pessoaExpected);

        //then
        assertEquals(pessoaService.consultarPessoa(pessoa.getId()).toString(), pessoa.toString());
    }

    @Test
    @Order(1)
    void whenSavePersonThenCheckIfPersonWasCreated() {

        //given
        Pessoa pessoaExpected = getPessoa();

        //when
        Pessoa pessoa = pessoaService.salvarPessoa(pessoaExpected);
        System.out.println(pessoaService.listarTodasAsPessoas());

        //then
        assertThat(pessoa).isNotNull();
        assertEquals(pessoa.getId(), pessoaService.listarTodasAsPessoas().get(0).getId());

        //after
        pessoaService.deletarPessoa(pessoa.getId());
    }

    @Test
    void whenUpdatePersonThenCheckIfDataWasChanged() {

        //given
        Pessoa pessoaExpected = getPessoa();
        pessoaService.salvarPessoa(pessoaExpected);

        //when
        Pessoa pessoa = getPessoa();
        pessoa.setNome("Jack Doe");
        pessoaService.atualizarPessoa(pessoaExpected.getId(), pessoa);

        //then
        assertEquals("Jack Doe", pessoaService.consultarPessoa(pessoaExpected.getId()).getNome());
    }

    @Test
    void whenDeletePersonThenCheckIfDataWasDeleted() {

        //given
        Pessoa pessoaExpected = getPessoa();
        Pessoa pessoa = pessoaService.salvarPessoa(pessoaExpected);

        //when
        pessoaService.deletarPessoa(pessoa.getId());

        //then
        assertFalse(pessoaRepository.existsById(pessoa.getId()));
    }

    @Test
    void whenGetPersonThatIdDoesntExistsThenThrowPessoaNaoExisteException() {
        assertThrows(PessoaNaoEncontradaException.class, () -> pessoaService.consultarPessoa("IDNULL"));
    }

    private Pessoa getPessoa() {

        Pessoa pessoaExpected = new Pessoa();
        pessoaExpected.setNome("John Doe");
        pessoaExpected.setDataNascimento(LocalDate.of(2000, 1, 1));
        pessoaExpected.setEnderecos(new ArrayList<Endereco>());

        return pessoaExpected;
    }
}

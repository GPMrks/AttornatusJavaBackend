package com.attornatus.backenddevelopertest.service;

import com.attornatus.backenddevelopertest.entities.Endereco;
import com.attornatus.backenddevelopertest.entities.Pessoa;
import com.attornatus.backenddevelopertest.exception.PessoaNaoEncontradaException;
import com.attornatus.backenddevelopertest.repository.PessoaRepository;
import com.attornatus.backenddevelopertest.service.impl.PessoaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PessoaServiceTest {

    private final Pessoa pessoaMock = new Pessoa();
    private final Pessoa pessoa1 = new Pessoa();
    private final Pessoa pessoa2 = new Pessoa();
    private final Pessoa pessoa3 = new Pessoa();
    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaServiceImpl pessoaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPessoas();
    }

    @Test
    void whenGetAllPersonThenCheckIfListOfPersonWasReturned() {

        //when
        when(pessoaRepository.findAll()).thenReturn(List.of(pessoa1, pessoa2, pessoa3));

        //then
        assertEquals(3, pessoaService.listarTodasAsPessoas().size());
        verify(pessoaRepository).findAll();
    }

    @Test
    void whenGetPersonThenCheckIfPersonWasReturned() {

        //given
        Pessoa pessoa = pessoaMock;

        //when
        when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));
        Pessoa pessoaConsultada = pessoaService.consultarPessoa(pessoa.getId());

        //then
        assertEquals(pessoa, pessoaConsultada);
        verify(pessoaRepository).findById(pessoa.getId());
    }

    @Test
    void whenSavePersonThenCheckIfPersonWasCreated() {

        //when
        when(pessoaRepository.save(pessoaMock)).thenReturn(pessoaMock);
        Pessoa pessoa = pessoaService.salvarPessoa(pessoaMock);

        //then
        assertEquals(pessoa, pessoaMock);
        verify(pessoaRepository).save(pessoa);
    }

    @Test
    void whenUpdatePersonThenCheckIfDataWasChanged() {

        //when
        Pessoa pessoa = pessoaMock;
        when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
        pessoa.setNome("Jane Doe");
        Pessoa pessoaAtualizada = pessoaService.atualizarPessoa(pessoa.getId(), pessoa);

        //then
        assertNotNull(pessoaAtualizada);
        assertEquals(pessoa.getNome(), pessoaAtualizada.getNome());
        verify(pessoaRepository).save(pessoaAtualizada);
    }

    @Test
    void whenDeletePersonThenCheckIfDataWasDeleted() {

        //given
        Pessoa pessoa = pessoaMock;

        //when
        when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));
        doNothing().when(pessoaRepository).deleteById(pessoa.getId());
        pessoaService.deletarPessoa(pessoa.getId());

        //then
        verify(pessoaRepository).deleteById(pessoa.getId());
        verify(pessoaRepository, times(1)).deleteById(pessoa.getId());
    }

    @Test
    void whenGetPersonThatIdDoesntExistsThenThrowPessoaNaoExisteException() {
        assertThrows(PessoaNaoEncontradaException.class, () -> pessoaService.consultarPessoa("IDNULL"));
    }

    private void startPessoas() {

        pessoaMock.setId(String.valueOf(UUID.randomUUID()).replace("-", "").substring(0, 6));
        pessoaMock.setNome("John Doe");
        pessoaMock.setDataNascimento(LocalDate.of(2001, 1, 1));
        pessoaMock.setEnderecos(new ArrayList<Endereco>());

        pessoa1.setId(String.valueOf(UUID.randomUUID()).replace("-", "").substring(0, 6));
        pessoa1.setNome("Jane Doe");
        pessoa1.setDataNascimento(LocalDate.of(2002, 2, 2));
        pessoa1.setEnderecos(new ArrayList<Endereco>());

        pessoa2.setId(String.valueOf(UUID.randomUUID()).replace("-", "").substring(0, 6));
        pessoa2.setNome("Jimmy Doe");
        pessoa2.setDataNascimento(LocalDate.of(2003, 3, 3));
        pessoa2.setEnderecos(new ArrayList<Endereco>());

        pessoa3.setId(String.valueOf(UUID.randomUUID()).replace("-", "").substring(0, 6));
        pessoa3.setNome("Geoffrey Doe");
        pessoa3.setDataNascimento(LocalDate.of(2004, 4, 4));
        pessoa3.setEnderecos(new ArrayList<Endereco>());
    }
}

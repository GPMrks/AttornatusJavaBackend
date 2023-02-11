package com.attornatus.backenddevelopertest.service;

import com.attornatus.backenddevelopertest.entities.Endereco;
import com.attornatus.backenddevelopertest.entities.Pessoa;
import com.attornatus.backenddevelopertest.exception.EnderecoNaoEncontradoException;
import com.attornatus.backenddevelopertest.repository.EnderecoRepository;
import com.attornatus.backenddevelopertest.repository.PessoaRepository;
import com.attornatus.backenddevelopertest.service.impl.EnderecoService;
import com.attornatus.backenddevelopertest.service.impl.PessoaService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EnderecoServiceTest {

    private final Pessoa pessoaMock = new Pessoa();
    private final Endereco enderecoMock = new Endereco();
    private final Endereco endereco1 = new Endereco();
    private final Endereco endereco2 = new Endereco();
    private final Endereco endereco3 = new Endereco();
    @Mock
    private PessoaRepository pessoaRepository;
    @Mock
    private EnderecoRepository enderecoRepository;
    @InjectMocks
    private PessoaService pessoaService;
    @InjectMocks
    private EnderecoService enderecoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPessoa();
        startAddresses();
    }

    @Test
    void whenSaveAddressThenCheckIfAddressWasCreated() {

        //given
        Pessoa pessoa = pessoaMock;
        Endereco endereco = enderecoMock;

        //when
        when(enderecoRepository.save(endereco)).thenReturn(endereco);
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        Endereco enderecoSalvo = enderecoService.salvarEnderecoParaPessoa(pessoa, endereco);

        //then
        assertEquals(pessoa.getId(), endereco.getIdPessoa());
        assertEquals(endereco, enderecoSalvo);
        verify(enderecoRepository).save(endereco);
    }

    @Test
    void whenSaveAddressToAddressRepositoryThenCheckIfAddressWasCreated() {

        //when
        Pessoa pessoaSalva = pessoaService.salvarPessoa(pessoaMock);
        Endereco enderecoSalvo = enderecoService.salvarEndereco(enderecoMock);
        when(pessoaRepository.findById(pessoaSalva.getId())).thenReturn(Optional.of(pessoaSalva));
        when(enderecoRepository.listarEnderecosPorPessoa(pessoaSalva.getId())).thenReturn(List.of(enderecoSalvo));
        enderecoService.salvarEnderecoParaPessoa(pessoaSalva, enderecoSalvo);

        //then
        assertEquals(pessoaSalva.getId(), enderecoSalvo.getIdPessoa());
        assertEquals(enderecoMock, enderecoService.listarTodosOsEnderecosDaPessoa(pessoaSalva.getId()).get(0));
        assertThat(enderecoSalvo).isNotNull();
        assertEquals(enderecoMock.getId(), enderecoSalvo.getId());
        verify(enderecoRepository, times(2)).save(enderecoSalvo);
    }

    @Test
    void whenGetAllAddressessThenCheckIfReturnedAListOfAllAddresses() {

        //when
        Pessoa pessoa = pessoaService.salvarPessoa(pessoaMock);
        when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));
        enderecoService.salvarEnderecoParaPessoa(pessoa, endereco1);
        enderecoService.salvarEnderecoParaPessoa(pessoa, endereco2);
        enderecoService.salvarEnderecoParaPessoa(pessoa, endereco3);
        when(enderecoRepository.listarEnderecosPorPessoa(pessoa.getId())).thenReturn(List.of(endereco1, endereco2, endereco3));

        //then
        assertEquals(pessoaService.consultarPessoa(pessoa.getId()).getEnderecos().get(2), endereco3);
        assertEquals(3, enderecoService.listarTodosOsEnderecosDaPessoa(pessoa.getId()).size());
        verify(enderecoRepository).listarEnderecosPorPessoa(pessoa.getId());
    }

    @Test
    void whenGetMainAddressessThenCheckIfReturnedTheRight() {

        //when
        Pessoa pessoa = pessoaService.salvarPessoa(pessoaMock);
        when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));
        enderecoService.salvarEnderecoParaPessoa(pessoa, endereco1);
        enderecoService.salvarEnderecoParaPessoa(pessoa, endereco2);
        enderecoService.salvarEnderecoParaPessoa(pessoa, endereco3);
        when(enderecoRepository.listarEnderecosPorPessoa(pessoa.getId())).thenReturn(List.of(endereco1, endereco2, endereco3));
        when(enderecoRepository.findById(endereco2.getId())).thenReturn(Optional.of(endereco2));
        when(enderecoRepository.save(endereco2)).thenReturn(endereco2);
        when(enderecoRepository.consultarEnderecoPrincipal(pessoa.getId())).thenReturn(endereco2);
        enderecoService.informarEnderecoPrincipal(pessoa.getId(), endereco2.getId());

        //then
        assertTrue(enderecoService.listarTodosOsEnderecosDaPessoa(pessoa.getId()).get(1).isPrincipal());
        assertEquals(enderecoRepository.findById(endereco2.getId()).get(), enderecoService.consultarEnderecoPrincipal(pessoa.getId()));
        verify(enderecoRepository, times(2)).save(endereco2);
    }

    @Test
    void whenGetAddressThatIdDoesntExistsThenThrowEnderecoNaoExisteException() {

        //when
        Pessoa pessoa = pessoaService.salvarPessoa(pessoaMock);
        when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));

        //then
        assertThrows(EnderecoNaoEncontradoException.class, () -> enderecoService.informarEnderecoPrincipal(pessoa.getId(), "IDNULL"));
        verify(enderecoRepository).findById("IDNULL");
    }

    @Test
    void whenUpdateAddressThenCheckIfDataWasChanged() {

        //given
        Pessoa pessoa = pessoaService.salvarPessoa(pessoaMock);
        Endereco endereco = enderecoService.salvarEndereco(endereco1);
        enderecoService.salvarEnderecoParaPessoa(pessoa, endereco);

        //when
        when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));
        when(enderecoRepository.findById(endereco.getId())).thenReturn(Optional.of(endereco));
        when(enderecoRepository.listarEnderecosPorPessoa(pessoa.getId())).thenReturn(List.of(endereco));
        enderecoService.atualizarEndereco(endereco.getId(), endereco2);

        //then
        assertEquals("Rua Jane Doe", enderecoService.listarTodosOsEnderecosDaPessoa(pessoa.getId()).get(0).getLogradouro());
    }

    private void startPessoa() {

        pessoaMock.setId(String.valueOf(UUID.randomUUID()).replace("-", "").substring(0, 6));
        pessoaMock.setNome("John Doe");
        pessoaMock.setDataNascimento(LocalDate.of(2000, 1, 1));
        pessoaMock.setEnderecos(new ArrayList<Endereco>());

    }

    private void startAddresses() {

        enderecoMock.setLogradouro("Rua John Doe");
        enderecoMock.setCep("30000000");
        enderecoMock.setNumero(300);
        enderecoMock.setCidade("Test City");

        endereco1.setLogradouro("Rua James Doe");
        endereco1.setCep("40000000");
        endereco1.setNumero(400);
        endereco1.setCidade("Test City");

        endereco2.setLogradouro("Rua Jane Doe");
        endereco2.setCep("50000000");
        endereco2.setNumero(500);
        endereco2.setCidade("Test City");

        endereco3.setLogradouro("Rua Geoffrey Doe");
        endereco3.setCep("60000000");
        endereco3.setNumero(600);
        endereco3.setCidade("Test City");

    }
}

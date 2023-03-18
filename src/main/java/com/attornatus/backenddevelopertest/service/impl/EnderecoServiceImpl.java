package com.attornatus.backenddevelopertest.service.impl;

import com.attornatus.backenddevelopertest.entities.Endereco;
import com.attornatus.backenddevelopertest.entities.Pessoa;
import com.attornatus.backenddevelopertest.exception.EnderecoNaoEncontradoException;
import com.attornatus.backenddevelopertest.exception.PessoaNaoEncontradaException;
import com.attornatus.backenddevelopertest.repository.EnderecoRepository;
import com.attornatus.backenddevelopertest.repository.PessoaRepository;
import com.attornatus.backenddevelopertest.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    private EnderecoRepository enderecoRepository;

    private PessoaRepository pessoaRepository;

    @Autowired
    public EnderecoServiceImpl(EnderecoRepository enderecoRepository, PessoaRepository pessoaRepository) {
        this.enderecoRepository = enderecoRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }

    @Override
    public Endereco salvarEndereco(Endereco endereco) {
        endereco.setId(generateUUID());
        enderecoRepository.save(endereco);
        return endereco;
    }

    @Override
    public Endereco consultarPorId(String id) {
        return verificarSeEnderecoExiste(id);
    }

    @Override
    public Endereco salvarEnderecoParaPessoa(Pessoa pessoa, Endereco endereco) {
        endereco.setId(generateUUID());
        endereco.setIdPessoa(pessoa.getId());
        enderecoRepository.save(endereco);
        pessoa.adicionaEndereco(endereco);
        pessoaRepository.save(pessoa);
        return endereco;
    }

    @Override
    public List<Endereco> listarTodosOsEnderecosDaPessoa(String idPessoa) {
        verificarSePessoaExiste(idPessoa);
        return enderecoRepository.listarEnderecosPorPessoa(idPessoa);
    }

    @Override
    public void atualizarEndereco(String id, Endereco endereco) {
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);
        Endereco enderecoAtualizado = enderecoOptional.get();
        enderecoAtualizado.setLogradouro(endereco.getLogradouro());
        enderecoAtualizado.setNumero(endereco.getNumero());
        enderecoAtualizado.setCep(endereco.getCep());
        enderecoAtualizado.setCidade(endereco.getCidade());
    }

    @Override
    public Endereco consultarEnderecoPrincipal(String idPessoa) {
        Pessoa pessoa = verificarSePessoaExiste(idPessoa);
        return enderecoRepository.consultarEnderecoPrincipal(idPessoa);
    }

    @Override
    public Endereco informarEnderecoPrincipal(String idPessoa, String idEndereco) {
        Pessoa pessoa = verificarSePessoaExiste(idPessoa);
        Endereco enderecoPrincipal = verificarSeEnderecoExiste(idEndereco);
        for (Endereco endereco : pessoa.getEnderecos()) {
            if (endereco.getId().equals(idEndereco)) {
                endereco.setPrincipal(true);
                enderecoPrincipal = endereco;
                enderecoRepository.save(endereco);
            } else {
                endereco.setPrincipal(false);
                enderecoRepository.save(endereco);
            }
        }
        pessoaRepository.save(pessoa);

        return enderecoPrincipal;
    }

    private Endereco verificarSeEnderecoExiste(String id) {
        Optional<Endereco> optionalEndereco = enderecoRepository.findById(id);
        final Endereco enderecoIndicado;

        if (optionalEndereco.isPresent()) {
            enderecoIndicado = optionalEndereco.get();
        } else {
            throw new EnderecoNaoEncontradoException(id);
        }

        return enderecoIndicado;
    }

    private Pessoa verificarSePessoaExiste(String id) {
        Optional<Pessoa> optionalPessoa = pessoaRepository.findById(id);
        final Pessoa pessoaIndicada;

        if (optionalPessoa.isPresent()) {
            pessoaIndicada = optionalPessoa.get();
        } else {
            throw new PessoaNaoEncontradaException(id);
        }

        return pessoaIndicada;
    }
}

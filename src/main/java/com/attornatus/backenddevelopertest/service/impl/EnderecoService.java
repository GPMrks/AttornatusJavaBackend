package com.attornatus.backenddevelopertest.service.impl;

import com.attornatus.backenddevelopertest.entities.Endereco;
import com.attornatus.backenddevelopertest.entities.Pessoa;
import com.attornatus.backenddevelopertest.exception.EnderecoNaoEncontradoException;
import com.attornatus.backenddevelopertest.exception.PessoaNaoEncontradaException;
import com.attornatus.backenddevelopertest.repository.EnderecoRepository;
import com.attornatus.backenddevelopertest.repository.PessoaRepository;
import com.attornatus.backenddevelopertest.service.IEnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnderecoService implements IEnderecoService {

    private EnderecoRepository enderecoRepository;

    @Autowired
    private void setEnderecoRepository(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    private PessoaRepository pessoaRepository;

    @Autowired
    private void setPessoaRepository(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }

    @Override
    public void salvarEndereco(Endereco endereco) {
        enderecoRepository.save(endereco);
    }

    @Override
    public void salvarEnderecoParaPessoa(Pessoa pessoa, Endereco endereco) {
        endereco.setId(generateUUID());
        endereco.setIdPessoa(pessoa.getId());
        enderecoRepository.save(endereco);
        pessoa.adicionaEndereco(endereco);
        pessoaRepository.save(pessoa);
    }

    @Override
    public List<Endereco> listaTodosOsEnderecosDaPessoa(String idPessoa) {
        verificarSePessoaExiste(idPessoa);
        return enderecoRepository.listarEnderecosPorPessoa(idPessoa);
    }

    @Override
    public void atualizaEndereco(String id, Endereco endereco) {
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);
        Endereco enderecoAtualizar = enderecoOptional.get();
        enderecoAtualizar.setLogradouro(endereco.getLogradouro());
        enderecoAtualizar.setNumero(endereco.getNumero());
        enderecoAtualizar.setCep(endereco.getCep());
        enderecoAtualizar.setCidade(endereco.getCidade());

    }

    @Override
    public Endereco consultarEnderecoPrincipal(String idPessoa) {
        Pessoa pessoa = verificarSePessoaExiste(idPessoa);
        return enderecoRepository.concutarEnderecoPrincipal(idPessoa);
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

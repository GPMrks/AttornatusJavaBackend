package com.attornatus.backenddevelopertest.service.impl;

import com.attornatus.backenddevelopertest.entities.Endereco;
import com.attornatus.backenddevelopertest.entities.Pessoa;
import com.attornatus.backenddevelopertest.exception.PessoaNaoEncontradaException;
import com.attornatus.backenddevelopertest.repository.PessoaRepository;
import com.attornatus.backenddevelopertest.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PessoaServiceImpl implements PessoaService {

    private PessoaRepository pessoaRepository;

    private EnderecoServiceImpl enderecoService;

    @Autowired
    public PessoaServiceImpl(PessoaRepository pessoaRepository, EnderecoServiceImpl enderecoService) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoService = enderecoService;
    }

    @Override
    public String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }

    @Override
    public Pessoa consultarPessoa(String id) {
        return verificarSePessoaExiste(id);
    }

    @Override
    public List<Pessoa> listarTodasAsPessoas() {
        return pessoaRepository.findAll();
    }

    @Override
    public Pessoa salvarPessoa(Pessoa pessoa) {
        pessoa.setId(generateUUID());

        for (Endereco endereco : pessoa.getEnderecos()) {
            endereco.setId(generateUUID());
            endereco.setIdPessoa(pessoa.getId());
            enderecoService.salvarEndereco(endereco);
        }
        pessoaRepository.save(pessoa);
        return pessoa;
    }

    @Override
    public Pessoa atualizarPessoa(String id, Pessoa pessoa) {
        final Pessoa pessoaIndicada = verificarSePessoaExiste(id);
        pessoaIndicada.setNome(pessoa.getNome());
        pessoaIndicada.setDataNascimento(pessoa.getDataNascimento());

        for (Endereco endereco : pessoa.getEnderecos()) {
            enderecoService.atualizarEndereco(endereco.getId(), endereco);
        }

        return pessoaRepository.save(pessoaIndicada);
    }

    @Override
    public void deletarPessoa(String id) {
        verificarSePessoaExiste(id);
        pessoaRepository.deleteById(id);
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

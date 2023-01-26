package com.attornatus.backenddevelopertest.service.impl;

import com.attornatus.backenddevelopertest.entities.Endereco;
import com.attornatus.backenddevelopertest.entities.Pessoa;
import com.attornatus.backenddevelopertest.exception.PessoaNaoEncontradaException;
import com.attornatus.backenddevelopertest.repository.PessoaRepository;
import com.attornatus.backenddevelopertest.service.IPessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PessoaService implements IPessoaService {

    private PessoaRepository pessoaRepository;

    @Autowired
    private void setPessoaRepository(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    private EnderecoService enderecoService;

    @Autowired
    private void setEnderecoRepository(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @Override
    public String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }

    @Override
    public Pessoa consultaPessoa(String id) {
        return verificarSePessoaExiste(id);
    }

    @Override
    public List<Pessoa> listaTodasAsPessoas() {
        return pessoaRepository.findAll();
    }

    @Override
    @Transactional
    public void salvarPessoa(Pessoa pessoa) {
        pessoa.setId(generateUUID());

        for (Endereco endereco : pessoa.getEnderecos()) {
            endereco.setId(generateUUID());
            endereco.setIdPessoa(pessoa.getId());
            enderecoService.salvarEndereco(endereco);
        }
        pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa atualizaPessoa(String id, Pessoa pessoa) {
        final Pessoa pessoaIndicada = verificarSePessoaExiste(id);
        pessoaIndicada.setNome(pessoa.getNome());
        pessoaIndicada.setDataNascimento(pessoa.getDataNascimento());

        for (Endereco endereco : pessoa.getEnderecos()) {
            enderecoService.atualizaEndereco(endereco.getId(), endereco);
        }

        return pessoaRepository.save(pessoaIndicada);
    }

    @Override
    public void deletaPessoa(String id) {
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
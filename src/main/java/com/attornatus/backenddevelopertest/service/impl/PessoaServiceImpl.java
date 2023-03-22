package com.attornatus.backenddevelopertest.service.impl;

import com.attornatus.backenddevelopertest.entities.Endereco;
import com.attornatus.backenddevelopertest.entities.Pessoa;
import com.attornatus.backenddevelopertest.exception.PessoaNaoEncontradaException;
import com.attornatus.backenddevelopertest.repository.PessoaRepository;
import com.attornatus.backenddevelopertest.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Pessoa consultarPessoaPorId(int id) {
        return verificarSePessoaExiste(id);
    }

    @Override
    public List<Pessoa> listarTodasAsPessoas() {
        return pessoaRepository.findAll();
    }

    @Override
    public Pessoa salvarPessoa(Pessoa pessoa) {
        pessoa.setId(generateUUID());
        pessoa.setPublicIdPessoa(pessoa.getPublicIdPessoa());

        for (Endereco endereco : pessoa.getEnderecos()) {
            endereco.setPublicIdPessoa(pessoa.getPublicIdPessoa());
            endereco.setPublicIdEndereco(endereco.getPublicIdEndereco());
            endereco.setId(generateUUID());
            endereco.setIdPessoa(pessoa.getId());
            enderecoService.salvarEndereco(endereco);
        }
        pessoaRepository.save(pessoa);
        return pessoa;
    }

    @Override
    public Pessoa atualizarPessoa(int id, Pessoa pessoa) {
        final Pessoa pessoaIndicada = verificarSePessoaExiste(id);
        pessoaIndicada.setNome(pessoa.getNome());
        pessoaIndicada.setDataNascimento(pessoa.getDataNascimento());

        for (Endereco endereco : pessoa.getEnderecos()) {
            enderecoService.atualizarEndereco(endereco.getPublicIdPessoa(), endereco);
        }

        return pessoaRepository.save(pessoaIndicada);
    }

    @Override
    public void deletarPessoa(int id) {
        verificarSePessoaExiste(id);
        pessoaRepository.excluirPorIdPublico(id);
    }

    private Pessoa verificarSePessoaExiste(int id) {
        Optional<Pessoa> optionalPessoa = Optional.ofNullable(pessoaRepository.consultarPorIdPublico(id));
        final Pessoa pessoaIndicada;

        if (optionalPessoa.isPresent()) {
            pessoaIndicada = optionalPessoa.get();
        } else {
            throw new PessoaNaoEncontradaException(id);
        }

        return pessoaIndicada;
    }
}

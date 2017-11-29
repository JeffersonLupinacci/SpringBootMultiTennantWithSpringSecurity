package com.jeffersonlupinacci.contas.service.saas;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.jeffersonlupinacci.contas.model.saas.Pessoa;
import com.jeffersonlupinacci.contas.repository.saas.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	PessoaRepository pessoaRepository;

	public List<Pessoa> pesquisar() {
		return pessoaRepository.findAll();
	}

	public Pessoa atualizar(long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = buscarPeloCodigo(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		pessoaRepository.save(pessoaSalva);
		return pessoaSalva;
	}

	public Pessoa buscarPeloCodigo(long codigo) {
		Pessoa pessoaSalva = pessoaRepository.findOne(codigo);
		if (pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}

	public Pessoa cadastrar(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}

	public void deletar(long codigo) {
		pessoaRepository.delete(codigo);
	}

	public void atualizaPropriedadeAtivo(long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscarPeloCodigo(codigo);
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);
	}
}

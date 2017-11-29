package com.jeffersonlupinacci.contas.service.saas;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jeffersonlupinacci.contas.model.saas.Lancamento;
import com.jeffersonlupinacci.contas.model.saas.Pessoa;
import com.jeffersonlupinacci.contas.repository.saas.LancamentoRepository;
import com.jeffersonlupinacci.contas.repository.saas.PessoaRepository;
import com.jeffersonlupinacci.contas.repository.saas.implementation.LancamentoRepositoryImpl;
import com.jeffersonlupinacci.contas.repository.saas.projection.LancamentoResumido;
import com.jeffersonlupinacci.contas.service.exceptions.PessoaInexistenteInativaException;

@Service
public class LancamentoService {

	@Autowired
	LancamentoRepository lancamentoRepository;

	@Autowired
	LancamentoRepositoryImpl lancamentoRepositoryImpl;	
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	public Iterable<LancamentoResumido> pesquisarResumido(Specification<Lancamento> spec, Pageable pageable) {
		return lancamentoRepositoryImpl.findAllResumido(spec, pageable);
	}


	public Iterable<Lancamento> pesquisar(Specification<Lancamento> spec, Pageable pageable) {
		return lancamentoRepository.findAll(spec, pageable);
	}

	public Lancamento atualizar(long codigo, Lancamento lancamento) {
		checkPessoa(lancamento);
		Lancamento lancamentoSalvo = buscarPeloCodigo(codigo);
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
		lancamentoRepository.save(lancamentoSalvo);
		return lancamentoSalvo;
	}

	public Lancamento buscarPeloCodigo(long codigo) {
		Lancamento lancamentoSalvo = lancamentoRepository.findOne(codigo);
		if (lancamentoSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return lancamentoSalvo;
	}

	public Lancamento cadastrar(Lancamento lancamento) {
		checkPessoa(lancamento);
		return lancamentoRepository.save(lancamento);
	}

	public void checkPessoa(Lancamento lancamento) {
		Pessoa pessoaSalva = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		if ((pessoaSalva == null) || (pessoaSalva.isInativo()))
			throw new PessoaInexistenteInativaException();
	}

	public void deletar(long codigo) {
		lancamentoRepository.delete(codigo);
	}

}

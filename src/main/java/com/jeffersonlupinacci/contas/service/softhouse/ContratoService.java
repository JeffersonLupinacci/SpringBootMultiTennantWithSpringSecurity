package com.jeffersonlupinacci.contas.service.softhouse;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jeffersonlupinacci.contas.model.softhouse.Contrato;
import com.jeffersonlupinacci.contas.repository.softhouse.ContratoRepository;

@Service
public class ContratoService {

	@Autowired
	ContratoRepository contratoRepository;

	public Iterable<Contrato> pesquisar(Specification<Contrato> spec, Pageable pageable) {
		return contratoRepository.findAll(spec, pageable);
	}

	public Contrato atualizar(Long codigo, Contrato contrato) {
		Contrato contratoSalvo = buscarPeloCodigo(codigo);
		BeanUtils.copyProperties(contrato, contratoSalvo, "codigo");
		contratoRepository.save(contratoSalvo);
		return contratoSalvo;
	}

	public Contrato buscarPeloCodigo(Long codigo) {
		Contrato contratoSalvo = contratoRepository.findOne(codigo);
		if (contratoSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return contratoSalvo;
	}

	public Contrato cadastrar(Contrato contrato) {
		contrato.setDataRegistro(new Date());
		return contratoRepository.save(contrato);
	}

	public void deletar(Long codigo) {
		contratoRepository.delete(codigo);
	}

}

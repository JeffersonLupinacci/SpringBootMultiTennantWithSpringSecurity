package com.jeffersonlupinacci.contas.service.saas;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jeffersonlupinacci.contas.model.saas.Categoria;
import com.jeffersonlupinacci.contas.repository.saas.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;

	public Iterable<Categoria> pesquisar(Specification<Categoria> spec, Pageable pageable) {
		return categoriaRepository.findAll(spec, pageable);
	}

	public Categoria atualizar(long codigo, Categoria categoria) {
		Categoria categoriaSalva = buscarPeloCodigo(codigo);
		BeanUtils.copyProperties(categoria, categoriaSalva, "codigo");
		categoriaRepository.save(categoriaSalva);
		return categoriaSalva;
	}

	public Categoria buscarPeloCodigo(long codigo) {
		Categoria categoriaSalva = categoriaRepository.findOne(codigo);
		if (categoriaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return categoriaSalva;
	}

	public Categoria cadastrar(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	public void deletar(long codigo) {
		categoriaRepository.delete(codigo);
	}

}

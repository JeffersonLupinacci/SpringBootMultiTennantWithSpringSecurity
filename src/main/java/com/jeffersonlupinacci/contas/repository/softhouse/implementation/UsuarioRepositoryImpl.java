package com.jeffersonlupinacci.contas.repository.softhouse.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.jeffersonlupinacci.contas.model.softhouse.Usuario;
import com.jeffersonlupinacci.contas.repository.softhouse.projection.UsuarioResumido;

/**
 * Implementação do repositório de lançamentos para a projeção resumida
 * 
 * @author Jefferson Lupinacci
 * @version 0.1
 */
public class UsuarioRepositoryImpl {

	@PersistenceContext
	private EntityManager manager;

	private String clientOwner;

	public Page<UsuarioResumido> findAllResumido(String owner, Specification<Usuario> spec, Pageable pageable) {
		this.clientOwner = owner;

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<UsuarioResumido> criteria = builder.createQuery(UsuarioResumido.class);
		Root<Usuario> root = criteria.from(Usuario.class);

		// @formatter:off
		criteria.select(
				builder.construct(UsuarioResumido.class, 
						root.get("codigo"), 
						root.get("nome"),
						root.get("email"), 
						root.get("contrato").get("razaoSocial")));
		// @formatter:on

		Predicate[] predicates = criarRestricoes(spec, criteria, root, builder);

		criteria.where(predicates);
		TypedQuery<UsuarioResumido> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		return new PageImpl<>(query.getResultList(), pageable, total(spec));
	}

	private Predicate[] criarRestricoes(Specification<Usuario> spec, CriteriaQuery<?> criteria, Root<Usuario> root,
			CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<>();
		if (spec != null)
			predicates.add(spec.toPredicate(root, criteria, builder));

		if (null != this.clientOwner)
			predicates.add(builder.equal(root.get("contrato").get("aliases"), clientOwner));

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

	private Long total(Specification<Usuario> spec) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Usuario> root = criteria.from(Usuario.class);

		Predicate[] predicates = criarRestricoes(spec, criteria, root, builder);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

}

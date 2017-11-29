package com.jeffersonlupinacci.contas.repository.saas.implementation;

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

import com.jeffersonlupinacci.contas.model.saas.Lancamento;
import com.jeffersonlupinacci.contas.repository.saas.projection.LancamentoResumido;

/**
 * Implementação do repositório de lançamentos para a projeção resumida
 * 
 * @author Jefferson Lupinacci
 * @version 0.1
 */
public class LancamentoRepositoryImpl {

	@PersistenceContext
	private EntityManager manager;

	public Page<LancamentoResumido> findAllResumido(Specification<Lancamento> spec, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoResumido> criteria = builder.createQuery(LancamentoResumido.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);

		// @formatter:off
		criteria.select(				
				builder.construct(LancamentoResumido.class, 						
						root.get("codigo"), 
						root.get("descricao"),
						root.get("dataVencimento"), 
						root.get("dataPagamento"), 
						root.get("valor"), 
						root.get("tipo"),
						root.get("categoria").get("nome"), 
						root.get("pessoa").get("nome")));
		// @formatter:on		

		Predicate[] predicates = criarRestricoes(spec, criteria, root, builder);
		criteria.where(predicates);
		TypedQuery<LancamentoResumido> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		return new PageImpl<>(query.getResultList(), pageable, total(spec));
	}

	private Predicate[] criarRestricoes(Specification<Lancamento> spec, CriteriaQuery<?> criteria,
			Root<Lancamento> root, CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<>();
		if (spec != null)
			predicates.add(spec.toPredicate(root, criteria, builder));
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

	private Long total(Specification<Lancamento> spec) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);

		Predicate[] predicates = criarRestricoes(spec, criteria, root, builder);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

}

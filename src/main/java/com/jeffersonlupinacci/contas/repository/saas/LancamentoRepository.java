package com.jeffersonlupinacci.contas.repository.saas;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.jeffersonlupinacci.contas.model.saas.Lancamento;

/**Repositório do cadastro de lançamentos
 * @author Jefferson Lupinacci
 * @version 0.1 */
@Repository
public interface LancamentoRepository extends PagingAndSortingRepository<Lancamento, Long>, JpaSpecificationExecutor<Lancamento>{

}
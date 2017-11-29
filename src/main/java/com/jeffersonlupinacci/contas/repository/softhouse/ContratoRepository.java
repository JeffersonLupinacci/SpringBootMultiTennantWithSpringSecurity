package com.jeffersonlupinacci.contas.repository.softhouse;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.jeffersonlupinacci.contas.model.softhouse.Contrato;

@Repository
public interface ContratoRepository
		extends PagingAndSortingRepository<Contrato, Long>, JpaSpecificationExecutor<Contrato> {

}

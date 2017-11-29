package com.jeffersonlupinacci.contas.repository.saas;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.jeffersonlupinacci.contas.model.saas.Categoria;

/**Repositório do cadastro de categorias
 * @author Jefferson Lupinacci
 * @version 0.1 */
@Repository
public interface CategoriaRepository
		extends PagingAndSortingRepository<Categoria, Long>, JpaSpecificationExecutor<Categoria> {

}

package com.jeffersonlupinacci.contas.repository.softhouse;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.jeffersonlupinacci.contas.model.softhouse.Usuario;

/**Repositório do cadastro de usuários
 * @author Jefferson Lupinacci
 * @version 0.1 */
@Repository
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {

	public Optional<Usuario> findByEmail(String email);

}
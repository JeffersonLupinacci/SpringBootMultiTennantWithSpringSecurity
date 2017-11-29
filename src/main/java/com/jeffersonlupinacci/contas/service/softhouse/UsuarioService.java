package com.jeffersonlupinacci.contas.service.softhouse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jeffersonlupinacci.contas.constants.Constantes;
import com.jeffersonlupinacci.contas.model.softhouse.Usuario;
import com.jeffersonlupinacci.contas.multitennant.SuporteDeContexto;
import com.jeffersonlupinacci.contas.repository.softhouse.UsuarioRepository;
import com.jeffersonlupinacci.contas.repository.softhouse.implementation.UsuarioRepositoryImpl;
import com.jeffersonlupinacci.contas.repository.softhouse.projection.UsuarioResumido;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	UsuarioRepositoryImpl usuarioRepositoryImpl;

	@Autowired
	BCryptPasswordEncoder encoder;

	public Iterable<UsuarioResumido> pesquisar(Specification<Usuario> spec, Pageable pageable) {
		
		String owner = SuporteDeContexto.getCurrentOwnerTennatIdentifier();
		if (owner == Constantes.TENANT_PADRAO)
			owner = null;
		
		return usuarioRepositoryImpl.findAllResumido(owner, spec, pageable);
	}

	public Usuario atualizar(Long codigo, Usuario usuario) {
		Usuario usuarioSalvo = buscarPeloCodigo(codigo);
		BeanUtils.copyProperties(usuario, usuarioSalvo, "codigo");
		usuarioRepository.save(usuarioSalvo);
		return usuarioSalvo;
	}

	public Usuario buscarPeloCodigo(Long codigo) {
		Usuario usuarioSalvo = usuarioRepository.findOne(codigo);
		if (usuarioSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return usuarioSalvo;
	}

	/**
	 * Quando um usuário é cadastrado pela softhouse uma senha aleatória é gerada e um email é enviado ao usuário
	 * 
	 * @param usuario
	 * @return
	 */
	public Usuario cadastrar(Usuario usuario) {

		/* codificando a palavra aleatório e inserindo dentro do usuário */
		usuario.setSenha(encoder.encode("aleatorio"));

		/*
		 * Neste ponto uma classe responsável por trabalhar com emails deve ser instanciada recebendo o usuario como parametro e enviando o email com os dados, um arquivo de template deve possuir o layout
		 */

		return usuarioRepository.save(usuario);
	}

	public void deletar(Long codigo) {
		usuarioRepository.delete(codigo);
	}

}

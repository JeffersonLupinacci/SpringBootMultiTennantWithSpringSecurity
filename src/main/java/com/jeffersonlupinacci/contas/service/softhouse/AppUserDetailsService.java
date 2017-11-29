package com.jeffersonlupinacci.contas.service.softhouse;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jeffersonlupinacci.contas.model.softhouse.CustomUserOauth;
import com.jeffersonlupinacci.contas.model.softhouse.Usuario;
import com.jeffersonlupinacci.contas.repository.softhouse.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

		Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário Incorreto"));

		if (!usuario.getContrato().isAtivo())
			throw new UsernameNotFoundException("Contrato bloqueado");

		return new CustomUserOauth(usuario, getPermissoes(usuario));
	}

	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		usuario.getPermissoes()
				.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
		return authorities;
	}
}

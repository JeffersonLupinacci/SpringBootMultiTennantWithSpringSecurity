package com.jeffersonlupinacci.contas;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.jeffersonlupinacci.contas.model.softhouse.Usuario;
import com.jeffersonlupinacci.contas.repository.softhouse.UsuarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContasApplicationTests {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Test
	public void getDadosUsuario() {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail("mail@mail.com");		
		Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário Incorreto"));
		System.out.println(usuario.getContrato().getAliases()); 
	}

	@Test
	public void GeradorDeSenha() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("admin"));
	}

}

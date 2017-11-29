package com.jeffersonlupinacci.contas.controller.softhouse;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jeffersonlupinacci.contas.constants.Constantes;
import com.jeffersonlupinacci.contas.event.ResourceEvent;
import com.jeffersonlupinacci.contas.model.softhouse.Usuario;
import com.jeffersonlupinacci.contas.repository.softhouse.projection.UsuarioResumido;
import com.jeffersonlupinacci.contas.service.softhouse.UsuarioService;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(Constantes.TENANT_SOFTHOUSE_URLVARIABLE + "/usuarios")
@PreAuthorize("hasAuthority('ROLE_SOFTHOUSE_USUARIOS')")
public class UsuarioSwController {

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	ApplicationEventPublisher publisher;

	@GetMapping("/")
	public Iterable<UsuarioResumido> pesquisar(
			@And({ @Spec(path = "codigo", params = "codigo", spec = Equal.class),
					@Spec(path = "nome", params = "nome", spec = Like.class),
					@Spec(path = "email", params = "email", spec = Like.class) }) Specification<Usuario> spec,
			@PageableDefault(size = 10, sort = "codigo") Pageable pageable) {
		return usuarioService.pesquisar(spec, pageable);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Usuario> buscarPeloCodigo(@PathVariable Long codigo) {
		Usuario Usuario = usuarioService.buscarPeloCodigo(codigo);
		return Usuario != null ? ResponseEntity.ok(Usuario) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Usuario> cadastrar(@Valid @RequestBody Usuario Usuario, HttpServletResponse response) {
		Usuario UsuarioSalvo = usuarioService.cadastrar(Usuario);
		publisher.publishEvent(new ResourceEvent(this, response, UsuarioSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioSalvo);
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long codigo, @Valid @RequestBody Usuario Usuario,
			HttpServletResponse response) {
		Usuario UsuarioSalvo = usuarioService.atualizar(codigo, Usuario);
		return ResponseEntity.status(HttpStatus.OK).body(UsuarioSalvo);
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable long codigo) {
		usuarioService.deletar(codigo);
	}

}

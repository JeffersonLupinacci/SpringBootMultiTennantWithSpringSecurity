package com.jeffersonlupinacci.contas.controller.saas;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jeffersonlupinacci.contas.event.ResourceEvent;
import com.jeffersonlupinacci.contas.model.saas.Categoria;
import com.jeffersonlupinacci.contas.service.saas.CategoriaService;

import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	CategoriaService categoriaService;

	@Autowired
	ApplicationEventPublisher publisher;

	@RequestMapping(value = "", params = {})
	@ResponseBody
	/*@PreAuthorize("hasAuthority('ROLE_CATEGORIA_PESQUISAR')")*/
	public Iterable<Categoria> pesquisar(
			@Spec(path = "nome", params = "nome", spec = Like.class) Specification<Categoria> spec,
			@PageableDefault(size = 10, sort = "codigo") Pageable pageable) {		
		return categoriaService.pesquisar(spec, pageable);
	}

	@GetMapping("/{codigo}")
	/*@PreAuthorize("hasAuthority('ROLE_CATEGORIA_VISUALIZAR')")*/
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable long codigo) {
		Categoria categoria = categoriaService.buscarPeloCodigo(codigo);
		return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	}

	@PostMapping
	/*@PreAuthorize("hasAuthority('ROLE_CATEGORIA_CADASTRAR')")*/
	public ResponseEntity<Categoria> cadastrar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaService.cadastrar(categoria);
		publisher.publishEvent(new ResourceEvent(this, response, categoriaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}

	@PutMapping("/{codigo}")
	/*@PreAuthorize("hasAuthority('ROLE_CATEGORIA_ATUALIZAR')")*/
	public ResponseEntity<Categoria> atualizar(@PathVariable long codigo, @Valid @RequestBody Categoria categoria,
			HttpServletResponse response) {
		Categoria categoriaSalva = categoriaService.atualizar(codigo, categoria);
		return ResponseEntity.status(HttpStatus.OK).body(categoriaSalva);
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	/*@PreAuthorize("hasAuthority('ROLE_CATEGORIA_DELETAR')")*/
	public void deletar(@PathVariable long codigo) {
		categoriaService.deletar(codigo);
	}

}

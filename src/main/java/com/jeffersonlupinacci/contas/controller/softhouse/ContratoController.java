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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jeffersonlupinacci.contas.constants.Constantes;
import com.jeffersonlupinacci.contas.event.ResourceEvent;
import com.jeffersonlupinacci.contas.model.softhouse.Contrato;
import com.jeffersonlupinacci.contas.service.softhouse.ContratoService;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping( Constantes.TENANT_SOFTHOUSE_URLVARIABLE + "/contratos")
@PreAuthorize("hasAuthority('ROLE_SOFTHOUSE_CONTRATOS')")
public class ContratoController {

	@Autowired
	ContratoService contratoService;

	@Autowired
	ApplicationEventPublisher publisher;
	
	@RequestMapping(value = "", params = {})
	@ResponseBody
	public Iterable<Contrato> pesquisar(@And({
		@Spec(path = "codigo", params = "codigo", spec = Equal.class),
		@Spec(path = "razaoSocial", params = "razao", spec = Like.class)}) Specification<Contrato> spec,
		@PageableDefault(size = 10, sort = "codigo") Pageable pageable) {		
		return contratoService.pesquisar(spec, pageable);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Contrato> buscarPeloCodigo(@PathVariable Long codigo) {
		Contrato contrato = contratoService.buscarPeloCodigo(codigo);
		return contrato != null ? ResponseEntity.ok(contrato) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Contrato> cadastrar(@Valid @RequestBody Contrato contrato, HttpServletResponse response) {
		Contrato contratoSalvo = contratoService.cadastrar(contrato);
		publisher.publishEvent(new ResourceEvent(this, response, contratoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(contratoSalvo);
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Contrato> atualizar(@PathVariable Long codigo, @Valid @RequestBody Contrato contrato,
			HttpServletResponse response) {
		Contrato contratoSalvo = contratoService.atualizar(codigo, contrato);
		return ResponseEntity.status(HttpStatus.OK).body(contratoSalvo);
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable long codigo) {
		contratoService.deletar(codigo);
	}

}

package com.jeffersonlupinacci.contas.controller.saas;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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

import com.jeffersonlupinacci.contas.event.ResourceEvent;
import com.jeffersonlupinacci.contas.model.saas.Pessoa;
import com.jeffersonlupinacci.contas.service.saas.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	PessoaService pessoaService;

	@Autowired
	ApplicationEventPublisher publisher;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESSOA_PESQUISAR')")
	public ResponseEntity<?> pesquisar() {
		List<Pessoa> pessoas = pessoaService.pesquisar();
		return ResponseEntity.ok(pessoas);
	}

	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESSOA_VISUALIZAR')")
	public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable long codigo) {
		Pessoa pessoa = pessoaService.buscarPeloCodigo(codigo);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_PESSOA_CADASTRAR')")
	public ResponseEntity<Pessoa> cadastrar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaService.cadastrar(pessoa);
		publisher.publishEvent(new ResourceEvent(this, response, pessoaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESSOA_ATUALIZAR')")
	public ResponseEntity<Pessoa> atualizar(@PathVariable long codigo, @Valid @RequestBody Pessoa pessoa,
			HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaService.atualizar(codigo, pessoa);
		return ResponseEntity.status(HttpStatus.OK).body(pessoaSalva);
	}

	@DeleteMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESSOA_DELETAR')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable long codigo) {
		pessoaService.deletar(codigo);
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizaPropriedadeAtivo(@PathVariable long codigo, @Valid @RequestBody Boolean ativo) {
		pessoaService.atualizaPropriedadeAtivo(codigo, ativo);		
	}
}

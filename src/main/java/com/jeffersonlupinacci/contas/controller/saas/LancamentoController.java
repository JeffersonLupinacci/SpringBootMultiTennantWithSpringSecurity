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
import com.jeffersonlupinacci.contas.model.saas.Lancamento;
import com.jeffersonlupinacci.contas.repository.saas.projection.LancamentoResumido;
import com.jeffersonlupinacci.contas.service.saas.LancamentoService;

import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LessThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

	@Autowired
	LancamentoService lancamentoService;

	@Autowired
	ApplicationEventPublisher publisher;

	@GetMapping("/resumo")
	@PreAuthorize("hasAuthority('ROLE_LANCAMENTO_PESQUISAR')")
	public Iterable<LancamentoResumido> pesquisarResumido(@And({
			@Spec(path = "descricao", params = "descricao", spec = Like.class),
			@Spec(path = "dataVencimento", params = "dataVencimentoDe", config = "yyyy-MM-dd", spec = GreaterThanOrEqual.class),
			@Spec(path = "dataVencimento", params = "dataVencimentoAte", config = "yyyy-MM-dd", spec = LessThanOrEqual.class) }) Specification<Lancamento> spec,
			@PageableDefault(size = 10, sort = "codigo") Pageable pageable) {		
		return lancamentoService.pesquisarResumido(spec, pageable);
	}

	@GetMapping("/")
	@PreAuthorize("hasAuthority('ROLE_LANCAMENTO_PESQUISAR')")
	public Iterable<Lancamento> pesquisar(@And({ @Spec(path = "descricao", params = "descricao", spec = Like.class),
			@Spec(path = "dataVencimento", params = "dataVencimentoDe", config = "yyyy-MM-dd", spec = GreaterThanOrEqual.class),
			@Spec(path = "dataVencimento", params = "dataVencimentoAte", config = "yyyy-MM-dd", spec = LessThanOrEqual.class) }) Specification<Lancamento> spec,
			@PageableDefault(size = 10, sort = "codigo") Pageable pageable) {
		return lancamentoService.pesquisar(spec, pageable);
	}

	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_LANCAMENTO_VISUALIZAR')")
	public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable long codigo) {
		Lancamento lancamento = lancamentoService.buscarPeloCodigo(codigo);
		return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_LANCAMENTO_CADASTRAR')")
	public ResponseEntity<Lancamento> cadastrar(@Valid @RequestBody Lancamento lancamento,
			HttpServletResponse response) {
		Lancamento lancamentoSalvo = lancamentoService.cadastrar(lancamento);
		publisher.publishEvent(new ResourceEvent(this, response, lancamentoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}

	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_LANCAMENTO_ATUALIZAR')")
	public ResponseEntity<Lancamento> atualizar(@PathVariable long codigo, @Valid @RequestBody Lancamento lancamento,
			HttpServletResponse response) {
		Lancamento lancamentoSalvo = lancamentoService.atualizar(codigo, lancamento);
		return ResponseEntity.status(HttpStatus.OK).body(lancamentoSalvo);
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_LANCAMENTO_DELETAR')")
	public void deletar(@PathVariable long codigo) {
		lancamentoService.deletar(codigo);
	}

}

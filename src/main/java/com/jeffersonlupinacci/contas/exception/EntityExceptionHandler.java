package com.jeffersonlupinacci.contas.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jeffersonlupinacci.contas.service.exceptions.PessoaInexistenteInativaException;

@ControllerAdvice
public class EntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	EntityExceptionHandlerCause exceptionHandlerCause;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		exceptionHandlerCause.generate("mensagem.invalida", ex);
		return handleExceptionInternal(ex, exceptionHandlerCause.getCauses(), headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		exceptionHandlerCause.generate(ex);
		return handleExceptionInternal(ex, exceptionHandlerCause.getCauses(), headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	protected ResponseEntity<Object> handleEmptyResultDataAccessException(Exception ex, WebRequest request) {
		exceptionHandlerCause.generate("mensagem.recurso_not_found", ex);
		return handleExceptionInternal(ex, exceptionHandlerCause.getCauses(), new HttpHeaders(), HttpStatus.NOT_FOUND,
				request);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class, InvalidDataAccessResourceUsageException.class })
	protected ResponseEntity<Object> handleDataIntegrityViolationException(Exception ex, WebRequest request) {
		exceptionHandlerCause.generate("mensagem.operacao_nao_permitida", ExceptionUtils.getRootCause(ex));
		return handleExceptionInternal(ex, exceptionHandlerCause.getCauses(), new HttpHeaders(), HttpStatus.BAD_REQUEST,
				request);
	}

	@ExceptionHandler({ PessoaInexistenteInativaException.class })
	protected ResponseEntity<Object> handlePessoaInexistenteInativaException(Exception ex, WebRequest request) {
		exceptionHandlerCause.generate("mensagem.pessoa_indisponivel_inexistente", ex);
		return handleExceptionInternal(ex, exceptionHandlerCause.getCauses(), new HttpHeaders(), HttpStatus.BAD_REQUEST,
				request);
	}

}

package com.jeffersonlupinacci.contas.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
public class EntityExceptionHandlerCause {

	private List<EntityExceptionHandlerData> causes = new ArrayList<EntityExceptionHandlerData>();

	@Autowired
	MessageSource messageSource;

	public void generate(MethodArgumentNotValidException exceptions) {
		causes.clear();
		for (FieldError erro : exceptions.getBindingResult().getFieldErrors()) {
			String usuario = messageSource.getMessage(erro, LocaleContextHolder.getLocale());
			add(usuario, erro.toString());
		}
	}

	public void generate(String message, Exception exception) {
		causes.clear();
		String usuario = messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
		add(usuario, exception.getCause() != null ? exception.getCause().toString() : exception.toString());
	}

	public void generate(String message, Throwable throwable) {
		causes.clear();
		String usuario = messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
		add(usuario, throwable.toString());
	}

	private void add(String message, String cause) {		
		causes.add(new EntityExceptionHandlerData(message, cause));
	}

	public List<EntityExceptionHandlerData> getCauses() {
		return Collections.unmodifiableList(causes);
	}

}

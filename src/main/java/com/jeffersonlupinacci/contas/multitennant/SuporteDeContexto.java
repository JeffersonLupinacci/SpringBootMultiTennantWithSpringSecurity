package com.jeffersonlupinacci.contas.multitennant;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.jeffersonlupinacci.contas.constants.Constantes;

public class SuporteDeContexto {

	public static final String getCurrentTenantIdentifier() {
		String valor = getAttributeFromRequest(Constantes.IDENTIFICADOR_TENANT);
		return valor != null ? valor : Constantes.TENANT_PADRAO;
	}

	public static final String getCurrentOwnerTennatIdentifier() {
		return getAttributeFromRequest(Constantes.IDENTIFICADOR_TENANT_JWT);
	}

	private static String getAttributeFromRequest(String attribute) {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null) {
			String identifier = (String) requestAttributes.getAttribute(attribute, RequestAttributes.SCOPE_REQUEST);
			if (identifier != null) {
				return identifier;
			}
		}
		return null;
	}

}
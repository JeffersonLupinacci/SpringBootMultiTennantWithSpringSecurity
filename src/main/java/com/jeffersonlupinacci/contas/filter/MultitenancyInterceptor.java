package com.jeffersonlupinacci.contas.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeffersonlupinacci.contas.constants.Constantes;

public class MultitenancyInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {

		String customField = identificaTennant();

		// Para o URL {/usuarios/} responder pelo banco de login
		if (req.getRequestURI().startsWith(((Constantes.MANUTENCAO_USUARIOS_URLVARIABLE)))) {
			req.setAttribute(Constantes.IDENTIFICADOR_TENANT, Constantes.TENANT_PADRAO);
			req.setAttribute(Constantes.IDENTIFICADOR_TENANT_JWT, customField);
		} else {
			if (null != customField)
				req.setAttribute(Constantes.IDENTIFICADOR_TENANT, customField);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private String identificaTennant() throws IOException, JsonParseException, JsonMappingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication) {
			Map<String, Object> map = objectMapper.convertValue(authentication.getDetails(), Map.class);
			Jwt jwt = JwtHelper.decode((String) map.get("tokenValue"));
			Map<String, Object> claims = objectMapper.readValue(jwt.getClaims(), Map.class);
			return (String) claims.get(Constantes.IDENTIFICADOR_TENANT_JWT);

		}
		return null;
	}

}
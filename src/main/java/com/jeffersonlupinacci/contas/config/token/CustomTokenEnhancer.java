package com.jeffersonlupinacci.contas.config.token;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.jeffersonlupinacci.contas.constants.Constantes;
import com.jeffersonlupinacci.contas.model.softhouse.CustomUserOauth;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		CustomUserOauth user = (CustomUserOauth) authentication.getPrincipal();
		Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());

		info.put(Constantes.IDENTIFICADOR_NOME_USUARIO, user.getUsuario().getNome());
		info.put(Constantes.IDENTIFICADOR_TENANT_JWT, user.getUsuario().getContrato().getAliases());

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}

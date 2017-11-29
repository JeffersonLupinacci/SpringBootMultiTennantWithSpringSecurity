package com.jeffersonlupinacci.contas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.jeffersonlupinacci.contas.service.softhouse.AppUserDetailsService;

@Configuration
public class JwtAccessTokenConverterConfig implements JwtAccessTokenConverterConfigurer {
    @Autowired
    
    private AppUserDetailsService customUserDetailsService;

    @Override
    public void configure(JwtAccessTokenConverter converter) {
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        DefaultUserAuthenticationConverter userTokenConverter = new DefaultUserAuthenticationConverter();
        userTokenConverter.setUserDetailsService(customUserDetailsService);
        accessTokenConverter.setUserTokenConverter(userTokenConverter);
        converter.setAccessTokenConverter(accessTokenConverter);
    }
}
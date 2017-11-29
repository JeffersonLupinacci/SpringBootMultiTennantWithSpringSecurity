package com.jeffersonlupinacci.contas.config;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jeffersonlupinacci.contas.filter.MultitenancyInterceptor;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;

/**Configuração de Diretório para mensagens e specification resolver 
 * @author Jefferson Lupinacci
 * @version 0.1 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new SpecificationArgumentResolver());
		argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:/translate/messages");
		return messageSource;
	}
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MultitenancyInterceptor());
}
		
}

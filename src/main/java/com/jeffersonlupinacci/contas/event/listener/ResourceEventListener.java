package com.jeffersonlupinacci.contas.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jeffersonlupinacci.contas.event.ResourceEvent;

/**Listner que adiciona o header location com o código do recurso adicionado
 * @author Jefferson Lupinacci
 * @version 0.1 */
@Component
public class ResourceEventListener implements ApplicationListener<ResourceEvent> {

	@Override
	public void onApplicationEvent(ResourceEvent event) {
		HttpServletResponse response = event.getResponse();
		Long codigo = event.getCodigo();
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(codigo).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}

}

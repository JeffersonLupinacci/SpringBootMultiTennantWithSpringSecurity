package com.jeffersonlupinacci.contas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/*
 * Para que o Multi Tenance funcione devo desabilitar a 
 * Configuração padrão do DataSource
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ContasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContasApplication.class, args);
	}

}

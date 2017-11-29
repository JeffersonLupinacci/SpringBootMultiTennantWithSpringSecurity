package com.jeffersonlupinacci.contas.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.jeffersonlupinacci.contas.config.properties.MultitenancyConfigurationProperties;
import com.jeffersonlupinacci.contas.multitennant.DataSourceBasedMultiTenantConnectionProviderImpl;

@Configuration
@EnableConfigurationProperties(MultitenancyConfigurationProperties.class)
public class MultitenancyConfiguration {

	@Autowired
	private MultitenancyConfigurationProperties multitenancyProperties;

	@Bean(name = "multitenantProvider")
	public DataSourceBasedMultiTenantConnectionProviderImpl dataSourceBasedMultiTenantConnectionProvider() {
		HashMap<String, DataSource> dataSources = new HashMap<String, DataSource>();

		// @formatter:off
        multitenancyProperties.getBancos().stream().forEach(tc -> dataSources.put(tc.getName(), DataSourceBuilder
                .create()
                .driverClassName(tc.getDriverClassName())
                .username(tc.getUsername())
                .password(tc.getPassword())
                .url(tc.getUrl()).build()));
        // @formatter:on
		return new DataSourceBasedMultiTenantConnectionProviderImpl(multitenancyProperties.getBancoPadrao().getName(),
				dataSources);
	}

	@Bean
	@DependsOn("multitenantProvider")
	public DataSource defaultDataSource() {
		return dataSourceBasedMultiTenantConnectionProvider().getDefaultDataSource();
	}

}
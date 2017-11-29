package com.jeffersonlupinacci.contas.multitennant;

import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;

public class DataSourceBasedMultiTenantConnectionProviderImpl
		extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

	private static final long serialVersionUID = 1L;

	private String bancoPadrao;

	private Map<String, DataSource> map;

	public DataSourceBasedMultiTenantConnectionProviderImpl(String bancoPadrao, Map<String, DataSource> map) {
		super();
		this.bancoPadrao = bancoPadrao;
		this.map = map;
	}

	@Override
	protected DataSource selectAnyDataSource() {
		return map.get(bancoPadrao);
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		return map.get(tenantIdentifier);
	}

	public DataSource getDefaultDataSource() {
		return map.get(bancoPadrao);
	}

}
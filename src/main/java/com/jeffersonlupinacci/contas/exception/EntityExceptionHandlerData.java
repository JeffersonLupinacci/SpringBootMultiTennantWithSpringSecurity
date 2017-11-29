package com.jeffersonlupinacci.contas.exception;

public class EntityExceptionHandlerData {
	
	private String usuario;
	private String desenvolvedor;

	public EntityExceptionHandlerData(String usuario, String desenvolvedor) {
		super();
		this.usuario = usuario;
		this.desenvolvedor = desenvolvedor;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getDesenvolvedor() {
		return desenvolvedor;
	}

	public void setDesenvolvedor(String desenvolvedor) {
		this.desenvolvedor = desenvolvedor;
	}
	
	@Override
	public String toString() {
		return "EntityExceptionHandlerData [usuario=" + usuario + ", desenvolvedor=" + desenvolvedor + "]";
	}
}

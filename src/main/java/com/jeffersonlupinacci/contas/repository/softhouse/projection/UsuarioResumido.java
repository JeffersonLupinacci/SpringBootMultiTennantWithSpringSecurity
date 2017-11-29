package com.jeffersonlupinacci.contas.repository.softhouse.projection;

/**
 * Projeção para Usuários
 * 
 * @author Jefferson Lupinacci
 * @version 0.1
 */
public class UsuarioResumido {

	private long codigo;
	private String nome;
	private String email;
	private String contrato;

	public UsuarioResumido(long codigo, String nome, String email, String contrato) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.email = email;
		this.contrato = contrato;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

}
package com.bantads.gerente.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="tb_gerente")
public class Gerente implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="id_ger")
	private Long id;
	@Column(name="nome_ger")
	private String nome;
	@Column(name="email_ger")
	private String email;
	@Column(name="cpf_ger")
	private String cpf;

	public Gerente() {
		super();
	}
	public Gerente(Long id, String nome, String email, String cpf) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public static Long getSerialversionuid() {
		return serialVersionUID;
	}
}

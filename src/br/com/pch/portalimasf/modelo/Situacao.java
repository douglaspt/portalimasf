package br.com.pch.portalimasf.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Situacao {
	
	@Id @Column
	private int id;
	
	@Column
	private String descricao;
	
	@Column
	private int tipo; //0 - normal, 1 - Suspenso, 2 - Cancelado

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	

}

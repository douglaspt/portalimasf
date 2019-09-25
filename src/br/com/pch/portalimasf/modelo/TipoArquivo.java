package br.com.pch.portalimasf.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TipoArquivo {
	
	@Id
	private int id;
	
	private String descricao;

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
	
	

}

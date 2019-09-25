package br.com.pch.portalimasf.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ClassificacaoSADT {
	
	@Id
	private int id;
	
	private String grupo;
	private String subGrupo;
	private String evento;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getSubGrupo() {
		return subGrupo;
	}
	public void setSubGrupo(String subGrupo) {
		this.subGrupo = subGrupo;
	}
	public String getEvento() {
		return evento;
	}
	public void setEvento(String evento) {
		this.evento = evento;
	}	

}

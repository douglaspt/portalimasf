package br.com.pch.portalimasf.modelo;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.Email;

@Entity
public class Acesso {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Email(message = "Digite um email válido")
	private String email;
	
	private String senha;
	
	private Calendar dataCriacao;
	
	@OneToOne
	private Beneficiario beneficiario;
	
	private String senhaOld;
	
	
	public Acesso() {

	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Calendar getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Calendar dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public Beneficiario getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	public String getSenhaOld() {
		return senhaOld;
	}

	public void setSenhaOld(String senhaOld) {
		this.senhaOld = senhaOld;
	}

}

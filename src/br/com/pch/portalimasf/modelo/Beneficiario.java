package br.com.pch.portalimasf.modelo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Beneficiario {
	
	@Id @Column
	private int id;
	
	@Column
	private int inscricao;
	
	@Column
	private int titularidade;
	
	@Column
	private String nome;
	
	@Column
	private String CPF;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Calendar dataNascimento;
	
	@Column
	private int idade;
	
	@Column
	private String sexo;
	
	@Column
	private int grauParentesco;
	
	@Column
	private String email;
	
	@Column
	private String celular;
	
	@Column
	private String plano;
	
	@OneToOne
	private Situacao situacao;
	
	@Column
	private String tabela;
	
	@Column
	private int estadoFisico;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInscricao() {
		return inscricao;
	}

	public void setInscricao(int inscricao) {
		this.inscricao = inscricao;
	}

	public int getTitularidade() {
		return titularidade;
	}

	public void setTitularidade(int titularidade) {
		this.titularidade = titularidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public int getGrauParentesco() {
		return grauParentesco;
	}

	public void setGrauParentesco(int grauParentesco) {
		this.grauParentesco = grauParentesco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getPlano() {
		return plano;
	}

	public void setPlano(String plano) {
		this.plano = plano;
	}


	public String getTabela() {
		return tabela;
	}

	public void setTabela(String tabela) {
		this.tabela = tabela;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public int getEstadoFisico() {
		return estadoFisico;
	}

	public void setEstadoFisico(int estadoFisico) {
		this.estadoFisico = estadoFisico;
	}
	
}

package br.com.pch.portalimasf.modelo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class ContaMedica {
	
	@Id
	@GeneratedValue
	private int id;
	@Column
	private int idConveniado;
	@Column
	private String nomeConveniado;
	@Temporal(TemporalType.DATE)
	private Calendar referencia;
	
	@Temporal(TemporalType.DATE)
	private Calendar dataDesconto;
	
	@Temporal(TemporalType.DATE)
	private Calendar dataEntrada;
	@Column
	private int idItem;
	@Column
	private String descricaoItem;
	@Column
	private int qtde;
	@Column
	private Double valorInformado;
	@Column
	private Double valorPago;

	@OneToOne
	private Beneficiario beneficiario;
	
	@Column
	private int idadeAtendimento;

	@Column
	private int idClassificacaoPlano;

	@Column
	private Double coparticipacao;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdConveniado() {
		return idConveniado;
	}
	public void setIdConveniado(int idConveniado) {
		this.idConveniado = idConveniado;
	}
	public String getNomeConveniado() {
		return nomeConveniado;
	}
	public void setNomeConveniado(String nomeConveniado) {
		this.nomeConveniado = nomeConveniado;
	}
	public Calendar getReferencia() {
		return referencia;
	}
	public void setReferencia(Calendar referencia) {
		this.referencia = referencia;
	}
	
	public Calendar getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(Calendar dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	public int getIdItem() {
		return idItem;
	}
	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}
	public String getDescricaoItem() {
		return descricaoItem;
	}
	public void setDescricaoItem(String descricaoItem) {
		this.descricaoItem = descricaoItem;
	}
	public int getQtde() {
		return qtde;
	}
	public void setQtde(int qtde) {
		this.qtde = qtde;
	}
	public Double getValorInformado() {
		return valorInformado;
	}
	public void setValorInformado(Double valorInformado) {
		this.valorInformado = valorInformado;
	}
	public Double getValorPago() {
		return valorPago;
	}
	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}
	public Beneficiario getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}
	public int getIdadeAtendimento() {
		return idadeAtendimento;
	}
	public void setIdadeAtendimento(int idadeAtendimento) {
		this.idadeAtendimento = idadeAtendimento;
	}
	
	public int getIdClassificacaoPlano() {
		return idClassificacaoPlano;
	}
	public void setIdClassificacaoPlano(int idClassificacaoPlano) {
		this.idClassificacaoPlano = idClassificacaoPlano;
	}
	
	public Double getCoparticipacao() {
		return coparticipacao;
	}
	public void setCoparticipacao(Double coparticipacao) {
		this.coparticipacao = coparticipacao;
	}
	
	public Calendar getDataDesconto() {
		return dataDesconto;
	}
	public void setDataDesconto(Calendar dataDesconto) {
		this.dataDesconto = dataDesconto;
	}

	
}

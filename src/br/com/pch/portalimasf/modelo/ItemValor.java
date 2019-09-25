package br.com.pch.portalimasf.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ItemValor {
	
	@Id
	@GeneratedValue
	private int id;
	
	private int idConveniado;
	
	private String nomeConveniado;
	
	private int codPadrao;
	
	private int codItem;
	
	private String descricaoItem;
	
	private int codCBHPM;
	
	private String descricaoCBHPM;
	
	@OneToOne
	private ClassificacaoSADT classificacaoSADT;
	
	private String classificacaoConveniado;
	
	private Double valor;
	
	private Double coparticipacao;
	
	private int idTipoArquivo;

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

	public int getCodPadrao() {
		return codPadrao;
	}

	public void setCodPadrao(int codPadrao) {
		this.codPadrao = codPadrao;
	}

	public int getCodItem() {
		return codItem;
	}

	public void setCodItem(int codItem) {
		this.codItem = codItem;
	}

	public String getDescricaoItem() {
		return descricaoItem;
	}

	public void setDescricaoItem(String descricaoItem) {
		this.descricaoItem = descricaoItem;
	}

	public int getCodCBHPM() {
		return codCBHPM;
	}

	public void setCodCBHPM(int codCBHPM) {
		this.codCBHPM = codCBHPM;
	}

	public String getDescricaoCBHPM() {
		return descricaoCBHPM;
	}

	public void setDescricaoCBHPM(String descricaoCBHPM) {
		this.descricaoCBHPM = descricaoCBHPM;
	}

	public ClassificacaoSADT getClassificacaoSADT() {
		return classificacaoSADT;
	}

	public void setClassificacaoSADT(ClassificacaoSADT classificacaoSADT) {
		this.classificacaoSADT = classificacaoSADT;
	}

	public String getClassificacaoConveniado() {
		return classificacaoConveniado;
	}

	public void setClassificacaoConveniado(String classificacaoConveniado) {
		this.classificacaoConveniado = classificacaoConveniado;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getCoparticipacao() {
		return coparticipacao;
	}

	public void setCoparticipacao(Double coparticipacao) {
		this.coparticipacao = coparticipacao;
	}

	public int getIdTipoArquivo() {
		return idTipoArquivo;
	}

	public void setIdTipoArquivo(int idTipoArquivo) {
		this.idTipoArquivo = idTipoArquivo;
	}	
	
}

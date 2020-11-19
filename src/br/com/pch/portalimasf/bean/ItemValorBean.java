package br.com.pch.portalimasf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pch.portalimasf.dao.ItemValorDao;
import br.com.pch.portalimasf.modelo.Conveniado;
import br.com.pch.portalimasf.modelo.DescricaoCombo;
import br.com.pch.portalimasf.modelo.ItemValor;

@Named
@ViewScoped
public class ItemValorBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nomeConveniado = "";
	private String codPadrao = "";
	private String descricao = "";
	private String grupo = "";
	
	private List<ItemValor> itens = new ArrayList<>();
	private List<Conveniado> conveniadosRedeNormal = new ArrayList<>();
	private List<Conveniado> conveniadosRedeReferencia = new ArrayList<>();
	private List<DescricaoCombo> grupos = new ArrayList<>();
	//private String[] selectedConveniados;
	
	@Inject
	private ItemValorDao itemValorDao;

	@Inject
	FacesContext context;
	
	@PostConstruct
	void init() {
		this.conveniadosRedeNormal = itemValorDao.buscaConveniado(1);
		this.conveniadosRedeReferencia = itemValorDao.buscaConveniado(2);
		this.grupos = itemValorDao.buscaGrupo();
	}
	
	public void listaRedeNormal() {
		
		if (this.nomeConveniado == "" && this.codPadrao == "" && this.descricao == "") {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Um dos campos deve ser preenchido ", ""));
			this.itens = null;
			
		} else {
			this.itens = itemValorDao.buscaItens(1, nomeConveniado, codPadrao, descricao);
			System.out.println(this.nomeConveniado+" - "+this.codPadrao+" - "+this.descricao);
			
		}
		
	}
	
	public void listaRedeReferencia() {
		
		if (this.nomeConveniado == "" && this.codPadrao == "" && this.descricao == "") {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Um dos campos deve ser preenchido ", ""));
			this.itens = null;
			
		} else {
			this.itens = itemValorDao.buscaItens(2, nomeConveniado, codPadrao, descricao);
			System.out.println(this.nomeConveniado+" - "+this.codPadrao+" - "+this.descricao);
			
		}
		
	}
	
	
	
	public void limpar() {
		this.descricao = "";
		this.codPadrao = "";
		this.nomeConveniado = "";
	}
	

	public String getNomeConveniado() {
		return nomeConveniado;
	}

	public void setNomeConveniado(String nomeConveniado) {
		this.nomeConveniado = nomeConveniado;
	}

	public String getCodPadrao() {
		return codPadrao;
	}

	public void setCodPadrao(String codPadrao) {
		this.codPadrao = codPadrao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<ItemValor> getItens() {
		return itens;
	}

	public void setItens(List<ItemValor> itens) {
		this.itens = itens;
	}

	public List<Conveniado> getConveniadosRedeNormal() {
		return conveniadosRedeNormal;
	}

	public void setConveniadosRedeNormal(List<Conveniado> conveniadosRedeNormal) {
		this.conveniadosRedeNormal = conveniadosRedeNormal;
	}

	public List<Conveniado> getConveniadosRedeReferencia() {
		return conveniadosRedeReferencia;
	}

	public void setConveniadosRedeReferencia(List<Conveniado> conveniadosRedeReferencia) {
		this.conveniadosRedeReferencia = conveniadosRedeReferencia;
	}

	public List<DescricaoCombo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<DescricaoCombo> grupos) {
		this.grupos = grupos;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	

}

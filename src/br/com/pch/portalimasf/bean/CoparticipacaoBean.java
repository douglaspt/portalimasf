package br.com.pch.portalimasf.bean;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pch.portalimasf.dao.ContaMedicaDao;
import br.com.pch.portalimasf.modelo.Beneficiario;
import br.com.pch.portalimasf.modelo.ContaMedica;
import br.com.pch.portalimasf.modelo.Meses;
import br.com.pch.portalimasf.modelo.TotalPorBeneficiario;
import br.com.pch.portalimasf.modelo.TotalPorGrupo;

@Named
@ViewScoped
public class CoparticipacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private int mes;
	private int ano;
	private Meses[] meses = Meses.values();
	private TotalPorGrupo totalGeral = new TotalPorGrupo();
	private String tituloPainelTitular = "Titular";

	private Beneficiario beneficiario = new Beneficiario();
	private Calendar referencia = Calendar.getInstance();
	private TotalPorBeneficiario totalBeneficiario;// = new
													// TotalPorBeneficiario();
	// private List<ContaMedica> contas = new ArrayList<>();
	private List<ContaMedica> contaBeneficiario = new ArrayList<>();
	private List<ContaMedica> contaDependenteMenor = new ArrayList<>();
	private List<TotalPorBeneficiario> contaDependenteMaior = new ArrayList<>();
	
	private boolean listaTitularONOFF = false;
	private boolean listaDepMenorONOFF = false;
	private boolean listaDepMaiorONOFF = false;

	@Inject
	private ContaMedicaDao contaMedicaDao;

	@Inject
	FacesContext context;

	@PostConstruct
	void init() {
		System.out.println("CoparticipacaoBean está iniciando ....");
		this.ano = referencia.get(Calendar.YEAR);
		this.mes = (referencia.get(Calendar.MONTH));
		this.beneficiario = (Beneficiario) context.getExternalContext().getSessionMap().get("beneficiarioLogado");
		atualizar();
	}

	@PreDestroy
	void morte() {
		System.out.println("CoparticipacaoBean está morrendo ....");
	}

	public void atualizar() {
		Calendar data = Calendar.getInstance();
		data.set(ano, mes, 1);
		if (this.beneficiario.getTitularidade() == 0) {
			tituloPainelTitular = "Titular";
			this.contaBeneficiario = this.contaMedicaDao.buscaPorBeneficiario(this.beneficiario, data);

			this.contaDependenteMenor = this.contaMedicaDao
					.buscaPorDependenteMenor(this.beneficiario.getInscricao(), data);

			this.contaDependenteMaior = this.contaMedicaDao
					.buscaPorDependenteMaiorAgrupado(this.beneficiario.getInscricao(), data);

		} else {
			tituloPainelTitular = "Beneficiário";
			this.contaBeneficiario = this.contaMedicaDao.buscaPorBeneficiario(this.beneficiario, data);
		}
		
		listaTitularONOFF = this.contaBeneficiario.size() > 0;
		listaDepMenorONOFF =  this.contaDependenteMenor.size() > 0;
		listaDepMaiorONOFF = this.contaDependenteMaior.size() > 0;
		
		this.totalGeral = calculaTotal();
		System.out.println("Total de Contas Beneficiario: " + this.contaBeneficiario.size()+" - "+listaTitularONOFF);
		System.out.println("Total de Contas Dependente Menor: " + this.contaDependenteMenor.size());
		System.out.println("Total de Contas Dependente Maior: " + this.contaDependenteMaior.size());
		System.out.println("Total Geral " + this.totalGeral.getQuantidade()+" - "+this.totalGeral.getTotalCoparticipacaoFormatado());
		
	}

	public TotalPorGrupo calculaTotal() {
		Double valor;
		Double totalPago = 0.0;
		Double totalCoparticipacao = 0.0;
		long qtde;
		long quantidade = 0;
		TotalPorGrupo totalPorGrupoCalculo = new TotalPorGrupo();

		// Total Titular
		for (ContaMedica conta : contaBeneficiario) {

			valor = conta.getValorPago();
			totalPago += valor;

			valor = conta.getCoparticipacao();
			totalCoparticipacao += valor;

			qtde = conta.getQtde();
			quantidade += qtde;
		}
		// Total Dependente Menor
		for (ContaMedica conta : contaDependenteMenor) {

			valor = conta.getValorPago();
			totalPago += valor;

			valor = conta.getCoparticipacao();
			totalCoparticipacao += valor;

			qtde = conta.getQtde();
			quantidade += qtde;
		}
		// Total Dependente Maior Assistido
		for (TotalPorBeneficiario conta : contaDependenteMaior) {

			valor = conta.getValorPago();
			totalPago += valor;

			valor = conta.getCoparticipacao();
			totalCoparticipacao += valor;

			qtde = conta.getQuantidade();
			quantidade += qtde;
		}

		totalPorGrupoCalculo.setQuantidade(quantidade);
		totalPorGrupoCalculo.setTotalPagoFormatado("R$ " + new DecimalFormat("#,###,##0.00").format(totalPago));
		totalPorGrupoCalculo
				.setTotalCoparticipacaoFormatado("R$ " + new DecimalFormat("#,###,##0.00").format(totalCoparticipacao));

		return totalPorGrupoCalculo;
	}

	public long somarQuantidade(int idBeneficiario) {

		Calendar data = Calendar.getInstance();
		data.set(ano, mes, 1);
		totalBeneficiario = contaMedicaDao.buscaTotalPorBeneficiario(idBeneficiario, data);
		// totalBeneficiario = new TotalPorBeneficiario(10, 500.10, 50.25);
		return totalBeneficiario.getQuantidade();
	}

	public Double somarValorPago() {
		return totalBeneficiario.getValorPago();
	}

	public Double somarCoparticipacao() {
		return totalBeneficiario.getCoparticipacao();
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public Meses[] getMeses() {
		return meses;
	}

	public void setMeses(Meses[] meses) {
		this.meses = meses;
	}

	public List<ContaMedica> getContaBeneficiario() {
		return contaBeneficiario;
	}

	public void setContaBeneficiario(List<ContaMedica> contaBeneficiario) {
		this.contaBeneficiario = contaBeneficiario;
	}

	public List<ContaMedica> getContaDependenteMenor() {
		return contaDependenteMenor;
	}

	public void setContaDependenteMenor(List<ContaMedica> contaDependenteMenor) {
		this.contaDependenteMenor = contaDependenteMenor;
	}

	public TotalPorBeneficiario getTotalBeneficiario(int idBeneficiario) {
		Calendar data = Calendar.getInstance();
		data.set(ano, mes, 1);
		totalBeneficiario = contaMedicaDao.buscaTotalPorBeneficiario(idBeneficiario, data);
		// totalBeneficiario = new TotalPorBeneficiario(10, 500.10, 50.25);

		return totalBeneficiario;
	}

	public List<TotalPorBeneficiario> getContaDependenteMaior() {
		return contaDependenteMaior;
	}

	public void setContaDependenteMaior(List<TotalPorBeneficiario> contaDependenteMaior) {
		this.contaDependenteMaior = contaDependenteMaior;
	}

	public TotalPorGrupo getTotalGeral() {
		return totalGeral;
	}

	public void setTotalGeral(TotalPorGrupo totalGeral) {
		this.totalGeral = totalGeral;
	}

	public String getTituloPainelTitular() {
		return tituloPainelTitular;
	}

	public void setTituloPainelTitular(String tituloPainelTitular) {
		this.tituloPainelTitular = tituloPainelTitular;
	}

	public boolean isListaTitularONOFF() {
		return listaTitularONOFF;
	}

	public void setListaTitularONOFF(boolean listaTitularONOFF) {
		this.listaTitularONOFF = listaTitularONOFF;
	}

	public boolean isListaDepMenorONOFF() {
		return listaDepMenorONOFF;
	}

	public void setListaDepMenorONOFF(boolean listaDepMenorONOFF) {
		this.listaDepMenorONOFF = listaDepMenorONOFF;
	}

	public boolean isListaDepMaiorONOFF() {
		return listaDepMaiorONOFF;
	}

	public void setListaDepMaiorONOFF(boolean listaDepMaiorONOFF) {
		this.listaDepMaiorONOFF = listaDepMaiorONOFF;
	}
	
}

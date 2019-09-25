package br.com.pch.portalimasf.modelo;

public class TotalPorBeneficiario {

	private Beneficiario beneficiario;
	private long quantidade;
	private Double valorPago;
	private Double coparticipacao;

	public TotalPorBeneficiario(Beneficiario beneficiario, long quantidade, Double valorPago, Double coparticipacao) {
		super();
		this.beneficiario = beneficiario;
		this.quantidade = quantidade;
		this.valorPago = valorPago;
		this.coparticipacao = coparticipacao;
	}

	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	public long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(long quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

	public Double getCoparticipacao() {
		return coparticipacao;
	}

	public void setCoparticipacao(Double coparticipacao) {
		this.coparticipacao = coparticipacao;
	}
	

}

package br.com.pch.portalimasf.modelo;

public class TotalPorGrupo {
	
	private long quantidade = 0;
	private String totalPagoFormatado = "";
	private String totalCoparticipacaoFormatado = "";
	
	public long getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(long quantidade) {
		this.quantidade = quantidade;
	}

	public String getTotalPagoFormatado() {
		return totalPagoFormatado;
	}
	public void setTotalPagoFormatado(String totalPagoFormatado) {
		this.totalPagoFormatado = totalPagoFormatado;
	}

	public String getTotalCoparticipacaoFormatado() {
		return totalCoparticipacaoFormatado;
	}
	public void setTotalCoparticipacaoFormatado(String totalCoparticipacaoFormatado) {
		this.totalCoparticipacaoFormatado = totalCoparticipacaoFormatado;
	}
	
	

}

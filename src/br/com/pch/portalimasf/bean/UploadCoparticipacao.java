package br.com.pch.portalimasf.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.pch.portalimasf.dao.BeneficiarioDao;
import br.com.pch.portalimasf.dao.ContaMedicaDao;
import br.com.pch.portalimasf.dao.LoteArquivoDao;
import br.com.pch.portalimasf.dao.TipoArquivoDao;
import br.com.pch.portalimasf.modelo.Beneficiario;
import br.com.pch.portalimasf.modelo.ContaMedica;
import br.com.pch.portalimasf.modelo.LoteArquivo;
import br.com.pch.portalimasf.modelo.TipoArquivo;

@Named
@ViewScoped
public class UploadCoparticipacao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private UploadedFile uploadedFile;
	private boolean emProcessamento = false;
	private TipoArquivo tipoArquivo = new TipoArquivo();
	private String aviso = "";
	private List<LoteArquivo> lotes = new ArrayList<>();
	private List<LoteArquivo> lotesEmProcessamento = new ArrayList<>();
	private Beneficiario beneficiario = new Beneficiario();
	
	@Inject
	FacesContext context;
	
	@Inject
	ContaMedicaDao contaMedicaDao;
	
	@Inject
	LoteArquivoDao loteArquivoDao;
	
	@Inject
	TipoArquivoDao tipoArquivoDao;
	
	@Inject
	BeneficiarioDao beneficiarioDao;
	
	@PostConstruct
	public void postConstruct() {
		this.lotes = loteArquivoDao.listaTodos();
		this.lotesEmProcessamento = loteArquivoDao.emProcessamento();
		if (this.lotesEmProcessamento.size() > 0) {
			emProcessamento = true;
			this.aviso = "Existe um Arquivo em Processamento, Aguarde!";

		}
	}
	
	public void upload(FileUploadEvent event) throws InterruptedException {
		uploadedFile = event.getFile();
		
		try {

			InputStream is = uploadedFile.getInputstream();
			Scanner sc = new Scanner(is);
			int ano = 0, mes = 0, dia = 0;
			String linha = null;
			int count = 0;
					
			Calendar referencia = Calendar.getInstance();
			Calendar data = Calendar.getInstance();
			LoteArquivo lote = new LoteArquivo();
			while (sc.hasNext()) {
				linha = sc.nextLine();
				ContaMedica contaMedica = new ContaMedica();
				String campos[] = linha.split(";");
				count += 1;
				// Thread.sleep(1000);
				if (count == 1) {
					this.tipoArquivo = tipoArquivoDao.buscaPorId(3);
					
					contaMedicaDao.excluirTodos(referencia);

					lote.setArquivo(uploadedFile.getFileName());
					lote.setDataCriacao(data);
					referencia.set(referencia.get(Calendar.YEAR), referencia.get(Calendar.MONTH), 1);
					lote.setReferencia(referencia);
					lote.setTipoArquivo(this.tipoArquivo);
					lote.setLinhas(count);
					lote.setProcessado(false);
					loteArquivoDao.adiciona(lote);
					
					if (campos.length < 14) {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Numero de Colunas diferente do Layot - Erro na linha: " + count, ""));

						lote.setErro(true);
						lote.setProcessado(true);
						loteArquivoDao.alterar(lote);
						emProcessamento = false;
						break;
					}

				}
				
				try {
					Calendar dataDesconto = Calendar.getInstance();
					if (campos[0].length() == 10) {
						dia = Integer.parseInt(campos[0].substring(0, 2));
						mes = Integer.parseInt(campos[0].substring(3, 5));
						ano = Integer.parseInt(campos[0].substring(6, 10));
						dataDesconto.set(ano, mes-1, dia);						
					} else {
						dataDesconto = null;
					}	
					contaMedica.setDataDesconto(dataDesconto);

				} catch (Exception e) {
					System.out.println("Erro no Campo Data do Desconto: " + e.getMessage());

				}

				try {
					contaMedica.setIdConveniado(Integer.parseInt(campos[1]));
					
				} catch (Exception e) {
					System.out.println("Erro no ID Conveniado: " + e.getMessage());

				}

				try {
					contaMedica.setNomeConveniado(campos[2]);
				} catch (Exception e) {
					System.out.println("Erro no Campo Nome do Conveniado: " + e.getMessage());

				}

				try {
					Calendar dataReferencia = Calendar.getInstance();
					if (campos[3].length() == 10) {
						dia = Integer.parseInt(campos[3].substring(0, 2));
						mes = Integer.parseInt(campos[3].substring(3, 5));
						ano = Integer.parseInt(campos[3].substring(6, 10));
						dataReferencia.set(ano, mes-1, dia);
					} else {
						dataReferencia = null;
					}	
					contaMedica.setReferencia(dataReferencia);
				} catch (Exception e) {
					System.out.println("Erro no Campo Data de Referencia: " + e.getMessage());

				}

				try {
					Calendar dataEntrada = Calendar.getInstance();
					if (campos[4].length() >= 10) {
						dia = Integer.parseInt(campos[4].substring(0, 2));
						mes = Integer.parseInt(campos[4].substring(3, 5));
						ano = Integer.parseInt(campos[4].substring(6, 10));
						dataEntrada.set(ano, mes-1, dia);
						
					} else {
						dataEntrada = null;
					}	
					contaMedica.setDataEntrada(dataEntrada);
				} catch (Exception e) {
					System.out.println("Erro no Campo Data de Entrada: " + e.getMessage());
				}

				try {
					contaMedica.setIdItem(Integer.parseInt(campos[5]));
				} catch (Exception e) {
					System.out.println("Erro no Campo Id Item " + e.getMessage());

				}

				try {
					contaMedica.setDescricaoItem(campos[6]);
				} catch (Exception e) {
					System.out.println("Erro no Campo Descrição do Procedimento: " + e.getMessage());

				}

				try {
					contaMedica.setQtde(Integer.parseInt(campos[7]));
					
				} catch (Exception e) {
					System.out.println("Erro no Campo Quantidade: " + e.getMessage());

				}

				try {
					contaMedica.setValorInformado(Double.parseDouble(campos[8].replace(",", ".")));
				} catch (Exception e) {
					System.out.println("Erro no Campo Valor Informado: " + e.getMessage());

				}

				try {
					contaMedica.setValorPago(Double.parseDouble(campos[9].replace(",", ".")));
					
				} catch (Exception e) {
					System.out.println("Erro no Campo Valor Pago: " + e.getMessage());

				}

				try {
					this.beneficiario = beneficiarioDao.buscaPorId(Integer.parseInt(campos[10]));
					contaMedica.setBeneficiario(this.beneficiario);
				} catch (Exception e) {
					System.out.println("Erro no Campo Coparticipação: " + e.getMessage());

				}

				try {
					contaMedica.setIdadeAtendimento(Integer.parseInt(campos[11]));
				} catch (Exception e) {
					System.out.println("Erro no Campo Coparticipação: " + e.getMessage());

				}
				
				try {
					contaMedica.setIdClassificacaoPlano(Integer.parseInt(campos[12]));
				} catch (Exception e) {
					System.out.println("Erro no Campo Coparticipação: " + e.getMessage());

				}
				
				try {
					contaMedica.setCoparticipacao(Double.parseDouble(campos[13].replace(",", ".")));
				} catch (Exception e) {
					System.out.println("Erro no Campo Coparticipação: " + e.getMessage());

				}

				contaMedicaDao.adiciona(contaMedica);
				System.out.println(linha);
				
			}
			lote.setProcessado(true);
			lote.setLinhas(count);
			emProcessamento = false;
			this.lotes = loteArquivoDao.listaTodos();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Upload completo! Total de linhas: " + count, ""));

			// buffRead.close();
			sc.close();
			loteArquivoDao.alterar(lote);

		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", e.getMessage()));
		}
		
	}

	public String getAviso() {
		return aviso;
	}

	public void setAviso(String aviso) {
		this.aviso = aviso;
	}

	public List<LoteArquivo> getLotes() {
		return lotes;
	}

	public void setLotes(List<LoteArquivo> lotes) {
		this.lotes = lotes;
	}

	public boolean isEmProcessamento() {
		return emProcessamento;
	}

	public void setEmProcessamento(boolean emProcessamento) {
		this.emProcessamento = emProcessamento;
	}
	

}

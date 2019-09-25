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
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.pch.portalimasf.dao.ClassificacaoSADTDao;
import br.com.pch.portalimasf.dao.ItemValorDao;
import br.com.pch.portalimasf.dao.LoteArquivoDao;
import br.com.pch.portalimasf.dao.TipoArquivoDao;
import br.com.pch.portalimasf.modelo.ClassificacaoSADT;
import br.com.pch.portalimasf.modelo.ItemValor;
import br.com.pch.portalimasf.modelo.LoteArquivo;
import br.com.pch.portalimasf.modelo.TipoArquivo;

@Named
@ViewScoped
public class UploadItemValorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private UploadedFile uploadedFile;
	private List<LoteArquivo> lotes = new ArrayList<>();
	private List<LoteArquivo> lotesEmProcessamento = new ArrayList<>();
	private StreamedContent streamedContent;
	private TipoArquivo tipoArquivo = new TipoArquivo();
	private boolean emProcessamento = false;
	private String aviso = "";

	@Inject
	FacesContext context;

	@Inject
	ItemValorDao itemValorDao;

	@Inject
	ClassificacaoSADTDao classificacaoSADTDao;

	@Inject
	LoteArquivoDao loteArquivoDao;

	@Inject
	TipoArquivoDao tipoArquivoDao;

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

			String linha = null;
			int count = 0;
			Calendar data = Calendar.getInstance();
			Calendar referencia = Calendar.getInstance();
			LoteArquivo lote = new LoteArquivo();
			while (sc.hasNext()) {
				linha = sc.nextLine();
				ClassificacaoSADT classificacaoSADT = new ClassificacaoSADT();
				ItemValor itemValor = new ItemValor();

				String campos[] = linha.split(";");
				count += 1;
				// Thread.sleep(1000);
				if (count == 1) {
					int tipo = Integer.valueOf(campos[11]);
					this.tipoArquivo = tipoArquivoDao.buscaPorId(tipo);
					itemValorDao.excluirTodos(tipo);

					lote.setArquivo(uploadedFile.getFileName());
					lote.setDataCriacao(data);
					referencia.set(referencia.get(Calendar.YEAR), referencia.get(Calendar.MONTH), 1);
					lote.setReferencia(referencia);
					lote.setTipoArquivo(this.tipoArquivo);
					lote.setLinhas(count);
					lote.setProcessado(false);
					loteArquivoDao.adiciona(lote);
					
					if (campos.length < 12) {
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
					itemValor.setIdConveniado(Integer.parseInt(campos[0]));
				} catch (Exception e) {
					System.out.println("Erro no Campo ID Conveniado: " + e.getMessage());

				}

				try {
					itemValor.setNomeConveniado(campos[1]);
				} catch (Exception e) {
					System.out.println("Erro no Campo Nome Conveniado: " + e.getMessage());

				}

				try {
					itemValor.setCodPadrao(Integer.parseInt(campos[2]));
				} catch (Exception e) {
					System.out.println("Erro no Campo Cod. Padrao: " + e.getMessage());

				}

				try {
					itemValor.setCodItem(Integer.parseInt(campos[3]));
				} catch (Exception e) {
					System.out.println("Erro no Campo Cod. Item: " + e.getMessage());

				}

				try {
					itemValor.setDescricaoItem(campos[4]);
				} catch (Exception e) {
					System.out.println("Erro no Campo Descrição do Item: " + e.getMessage());
				}

				try {
					itemValor.setCodCBHPM(Integer.parseInt(campos[5]));
				} catch (Exception e) {
					System.out.println("Erro no Campo Cod. CBHPM: " + e.getMessage());

				}

				try {
					itemValor.setDescricaoCBHPM(campos[6]);
				} catch (Exception e) {
					System.out.println("Erro no Campo Descrição CBHPM: " + e.getMessage());

				}

				try {
					classificacaoSADT.setId(Integer.parseInt(campos[7]));
					itemValor.setClassificacaoSADT(classificacaoSADT);
				} catch (Exception e) {
					System.out.println("Erro no Campo Classificação SADT: " + e.getMessage());

				}

				try {
					itemValor.setClassificacaoConveniado(campos[8]);
				} catch (Exception e) {
					System.out.println("Erro no Campo Classificação do Conveniado: " + e.getMessage());

				}

				try {
					itemValor.setValor(Double.parseDouble(campos[9].replace(",", ".")));
				} catch (Exception e) {
					System.out.println("Erro no Campo Valor: " + e.getMessage());

				}

				try {
					itemValor.setCoparticipacao(Double.parseDouble(campos[10].replace(",", ".")));
				} catch (Exception e) {
					System.out.println("Erro no Campo Coparticipação: " + e.getMessage());

				}

				try {
					itemValor.setIdTipoArquivo(this.tipoArquivo.getId());
				} catch (Exception e) {
					System.out.println("Erro no Campo Coparticipação: " + e.getMessage());

				}

				itemValorDao.adiciona(itemValor);
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

	public StreamedContent getStreamedContent() {
		return streamedContent;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
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

	public String getAviso() {
		return aviso;
	}

	public void setAviso(String aviso) {
		this.aviso = aviso;
	}

}

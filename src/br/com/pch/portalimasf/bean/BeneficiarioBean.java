package br.com.pch.portalimasf.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import br.com.pch.portalimasf.dao.AcessoDao;
import br.com.pch.portalimasf.dao.BeneficiarioDao;
import br.com.pch.portalimasf.dao.LoteArquivoDao;
import br.com.pch.portalimasf.dao.SituacaoDao;
import br.com.pch.portalimasf.dao.TipoArquivoDao;
import br.com.pch.portalimasf.modelo.Acesso;
import br.com.pch.portalimasf.modelo.Beneficiario;
import br.com.pch.portalimasf.modelo.LoteArquivo;
import br.com.pch.portalimasf.modelo.Situacao;
import br.com.pch.portalimasf.modelo.TipoArquivo;

@Named
@ViewScoped
public class BeneficiarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private UploadedFile uploadedFile;
	private String inscricao = "";
	private String nome = "";
	private String CPF = "";
	private Date dataNascimento = null;
	private TipoArquivo tipoArquivo = new TipoArquivo();
	private boolean emProcessamento = false;
	private List<LoteArquivo> lotes = new ArrayList<>();
	private List<LoteArquivo> lotesEmProcessamento = new ArrayList<>();
	private String aviso = "";
	

	private Beneficiario beneficiario = new Beneficiario();
	private Acesso acesso = new Acesso();

	private List<Beneficiario> beneficiarios = new ArrayList<>();

	@Inject
	BeneficiarioDao beneficiarioDao;

	@Inject
	AcessoDao acessoDao;

	@Inject
	FacesContext context;
	
	@Inject
	TipoArquivoDao tipoArquivoDao;
	
	@Inject
	LoteArquivoDao loteArquivoDao;
	
	@Inject 
	SituacaoDao situacaoDao;

	@PostConstruct
	void init() {
		System.out.println("BeneficiarioBean está iniciando ....");

		this.acesso = (Acesso) context.getExternalContext().getSessionMap().get("acessoLogado");
		this.beneficiario = (Beneficiario) context.getExternalContext().getSessionMap().get("beneficiarioLogado");
		
		this.lotes = loteArquivoDao.listaTodos();
		this.lotesEmProcessamento = loteArquivoDao.emProcessamento();
		if (this.lotesEmProcessamento.size() > 0) {
			emProcessamento = true;
			this.aviso = "Existe um Arquivo em Processamento, Aguarde!";

		}
	}

	public String gravar() {
		this.beneficiarioDao.alterar(this.beneficiario);
		
		if (this.acesso != null) {
			this.acessoDao.alterar(this.acesso);	
		}

		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Beneficiário Alterado com Sucesso!"));
		return "";
	}

	public String pesquisaBeneficiario() {
		if ((this.inscricao == "") && (this.nome == "") && (this.CPF == "") && (this.dataNascimento == null)) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Um dos campos deve ser preenchido!"));
		} else {
			this.beneficiarios = beneficiarioDao.buscaPorBeneficiario(this.inscricao, this.nome, this.CPF,
					this.dataNascimento);
		}
		return "";
	}

	public String limpar() {
		this.inscricao = "";
		this.nome = "";
		this.CPF = "";
		this.dataNascimento = null;
		System.out.println("LIMPARRR");
		return "";
	}


	public void onRowSelect(SelectEvent event) {
		FacesMessage msg;	

		this.beneficiario = (Beneficiario) event.getObject();

		this.acesso = acessoDao.buscaPorBeneficiario(this.beneficiario);
		
		System.out.println("Acesso Aquiii : "+this.acesso);
		
		context.getExternalContext().getSessionMap().put("acessoLogado", this.acesso);

		if (this.beneficiario != null) {
			context.getExternalContext().getSessionMap().put("beneficiarioLogado", this.beneficiario);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Beneficiário Selecionado: " + ((Beneficiario) event.getObject()).getId(), "");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} else {
		  msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Beneficiário não encontrado!", "");
		  FacesContext.getCurrentInstance().addMessage(null, msg);
		}  
	}
	
	
	public void upload(FileUploadEvent event) throws InterruptedException {
		uploadedFile = event.getFile();
		boolean beneficiarioNovo = false;
		
		try {

			InputStream is = uploadedFile.getInputstream();
			Scanner sc = new Scanner(is);

			String linha = null;
			int count = 0;
			Calendar data = Calendar.getInstance();
			Calendar referencia = Calendar.getInstance();
			Calendar dataNascimento = Calendar.getInstance();
			LoteArquivo lote = new LoteArquivo();
			int ano = 0, mes = 0, dia = 0;
			
			while (sc.hasNext()) {
				linha = sc.nextLine();
				String campos[] = linha.split(";");
				count += 1;
				System.out.println(linha);
				if (count == 1) {
					
					this.tipoArquivo = tipoArquivoDao.buscaPorId(4);
					lote.setArquivo(uploadedFile.getFileName());
					lote.setDataCriacao(data);
					referencia.set(referencia.get(Calendar.YEAR), referencia.get(Calendar.MONTH), 1);
					lote.setReferencia(referencia);
					lote.setTipoArquivo(this.tipoArquivo);
					lote.setLinhas(count);
					lote.setProcessado(false);
					//lote.setProcessado(true);
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
				
				this.beneficiario = beneficiarioDao.buscaPorId(Integer.parseInt(campos[0]));
				if (this.beneficiario == null) {
					this.beneficiario = new Beneficiario();	
					this.beneficiario.setId(Integer.parseInt(campos[0]));
					this.beneficiario.setInscricao(Integer.parseInt(campos[1]));
					this.beneficiario.setTitularidade(Integer.parseInt(campos[2]));
					beneficiarioNovo = true;
				} else {
					beneficiarioNovo = false;
				}
				this.beneficiario.setNome(campos[3]);
				this.beneficiario.setCPF(campos[4]);
				 
				if (campos[5].length() == 10) {
					dia = Integer.parseInt(campos[5].substring(0, 2));
					mes = Integer.parseInt(campos[5].substring(3, 5));
					ano = Integer.parseInt(campos[5].substring(6, 10));
					System.out.println(dia+"/"+mes+"/"+ano);
					
				} else {
					dataNascimento = null;
				}	
				dataNascimento.set(ano, mes, dia);				
				this.beneficiario.setDataNascimento(dataNascimento);
				this.beneficiario.setIdade(Integer.parseInt(campos[6]));
				this.beneficiario.setSexo(campos[7]);
				this.beneficiario.setGrauParentesco(Integer.parseInt(campos[8]));
				this.beneficiario.setEmail(campos[9]);
				this.beneficiario.setCelular(campos[10]);
				this.beneficiario.setPlano(campos[11]);
				Situacao situacao = situacaoDao.buscaPorId(Integer.parseInt(campos[12]));
				this.beneficiario.setSituacao(situacao);
				this.beneficiario.setTabela(campos[13]);
				this.beneficiario.setEstadoFisico(Integer.parseInt(campos[14]));
				
				if (beneficiarioNovo) {
					beneficiarioDao.adiciona(beneficiario);
				} else {
					beneficiarioDao.alterar(beneficiario);
				}
				
				
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

	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	public Acesso getAcesso() {
		return acesso;
	}

	public void setAcesso(Acesso acesso) {
		this.acesso = acesso;
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

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getInscricao() {
		return inscricao;
	}

	public void setInscricao(String inscricao) {
		this.inscricao = inscricao;
	}

	public List<Beneficiario> getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(List<Beneficiario> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}

	public boolean isEmProcessamento() {
		return emProcessamento;
	}

	public void setEmProcessamento(boolean emProcessamento) {
		this.emProcessamento = emProcessamento;
	}

	public List<LoteArquivo> getLotes() {
		return lotes;
	}

	public void setLotes(List<LoteArquivo> lotes) {
		this.lotes = lotes;
	}

	public List<LoteArquivo> getLotesEmProcessamento() {
		return lotesEmProcessamento;
	}

	public void setLotesEmProcessamento(List<LoteArquivo> lotesEmProcessamento) {
		this.lotesEmProcessamento = lotesEmProcessamento;
	}

	public String getAviso() {
		return aviso;
	}

	public void setAviso(String aviso) {
		this.aviso = aviso;
	}
	
	

}

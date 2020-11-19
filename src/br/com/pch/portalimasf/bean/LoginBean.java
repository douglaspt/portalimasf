package br.com.pch.portalimasf.bean;

import java.io.Serializable;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.validator.constraints.Email;
import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.PrimeFaces;

import br.com.pch.portalimasf.dao.AcessoDao;
import br.com.pch.portalimasf.dao.BeneficiarioDao;
import br.com.pch.portalimasf.modelo.Acesso;
import br.com.pch.portalimasf.modelo.Beneficiario;
import br.com.pch.portalimasf.modelo.EnviaEmail;
import br.com.pch.portalimasf.modelo.Senha;
import br.com.pch.portalimasf.tx.Transacional;

@Named
@ManagedBean
@ViewScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Beneficiario beneficiario = new Beneficiario();
	private Acesso acesso = new Acesso();

	@Email(message = "Digite um email válido")
	private String email = "";

	@Email(message = "Digite um email válido")
	private String emailRecuperacao = "";

	private String senha;
	private String senhaNova;
	private String CPF;
	private Calendar dataNascimento = Calendar.getInstance();
	private boolean primeiroAcessoVisible = false;

	@Inject
	BeneficiarioDao beneficiarioDao;

	@Inject
	AcessoDao acessoDao;

	@Inject
	FacesContext context;

	@PostConstruct
	void init() {
		// Object valor =
		// FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
		// .get("nomeDoParametro");
		this.email = (String) context.getExternalContext().getRequestParameterMap().get("emailRec");

	}

	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	
	public String efetuaLoginPorEmailSenha() {

		System.out.println(email + " - " + senha);
		try {
			System.out.println("Aquii 01");
			this.acesso = acessoDao.buscaPorEmailSenha(email, senha);
			//this.acesso = acessoDao.buscaPorEmail(email);
			System.out.println("Aquii 02");
			if (this.acesso != null) {
				//if (BCrypt.checkpw(senha, this.acesso.getSenha())) {
					System.out.println("Senha Correta");					
					context.getExternalContext().getSessionMap().put("acessoLogado", this.acesso);
					this.beneficiario = this.acesso.getBeneficiario();
					context.getExternalContext().getSessionMap().put("beneficiarioLogado", this.beneficiario);
					return "principal?faces-redirect=true";
					
				/*}	
			    else {
			    	System.out.println("Senha Incorreta");	
			    	context.getExternalContext().getFlash().setKeepMessages(true);
					context.addMessage(null, new FacesMessage("Senha Inválida"));
			    }
			    */	
				
			} else {
				context.getExternalContext().getFlash().setKeepMessages(true);
				context.addMessage(null, new FacesMessage("E-mail Inválido"));
			}

		} catch (Exception e) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Falha ao localizar Beneficiário"));
		}
		return "";

	}

	public String efetuaLoginPorCPFNascimento() {

		this.beneficiario = beneficiarioDao.buscaPorCPFNascimento(CPF, dataNascimento);
		if (this.beneficiario != null) {

			Acesso acesso = acessoDao.buscaPorBeneficiario(beneficiario);

			if (acesso == null) {
				context.getExternalContext().getSessionMap().put("beneficiarioLogado", this.beneficiario);
				return "cadastro?faces-redirect=true";
			} else {
				context.getExternalContext().getFlash().setKeepMessages(true);
				context.addMessage(null,
						new FacesMessage("Beneficiario já possue cadastro no email: " + acesso.getEmail()));
				email = acesso.getEmail();
				return "login?faces-redirect=true";
			}
		} else {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Beneficiario não encontrado"));
		}
		return "";

	}

	public String deslogar() {
		context.getExternalContext().getSessionMap().remove("acessoLogado");
		context.getExternalContext().getSessionMap().remove("beneficiarioLogado");
		context.getExternalContext().getSessionMap().remove("adminLogado");
		return "login?faces-redirect=true";
	}

	public String voltar() {
		return "login?faces-redirect=true";
	}

	@Transacional
	public void recuperarSenha() {
		FacesMessage message = null;
		boolean loggedIn = false;

		this.acesso = acessoDao.buscaPorEmail(emailRecuperacao);

		if (this.acesso != null) {

			String senhaNova = Senha.getRandomPass(6);
			String senhaHasheada = Senha.criptografarSenha(senhaNova);

			if (EnviaEmail.enviaEmail(this.acesso.getEmail(), this.acesso.getBeneficiario().getNome(), senhaNova)) {
				loggedIn = true;
				this.acesso.setSenha(senhaHasheada);
				acessoDao.alterar(this.acesso);

				message = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Recuperação de senha enviado com sucesso para o e-mail " + emailRecuperacao, emailRecuperacao);
			} else {
				loggedIn = false;
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Falha ao enviar o e-mail", "Erro de envio");
			}
		} else {
			loggedIn = false;
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "E-mail não encontrado", "Email inválido");
		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		PrimeFaces.current().ajax().addCallbackParam("loggedIn", loggedIn);
	}

	@Transacional
	public void alterarSenha() {

		FacesMessage message = null;
		boolean loggedIn = false;

		this.acesso = (Acesso) context.getExternalContext().getSessionMap().get("acessoLogado");

		//if (this.acesso.getSenha().equals(this.senha)) {
		if (BCrypt.checkpw(this.senha, this.acesso.getSenha())) {
			loggedIn = true;
			this.acesso.setSenha(Senha.criptografarSenha(this.senhaNova));
			acessoDao.alterar(this.acesso);
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Senha Alterada", "Senha");

		} else {
			loggedIn = false;
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Senha Atual Incorreta!", "Erro de senha");
		}

		System.out.println(this.acesso.getSenha() + "  -  " + senha);

		FacesContext.getCurrentInstance().addMessage(null, message);
		PrimeFaces.current().ajax().addCallbackParam("loggedIn", loggedIn);
	}

	public void primeiroAcesso() {
		this.primeiroAcessoVisible = true;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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

	public boolean isPrimeiroAcessoVisible() {
		return primeiroAcessoVisible;
	}

	public void setPrimeiroAcessoVisible(boolean primeiroAcessoVisible) {
		this.primeiroAcessoVisible = primeiroAcessoVisible;
	}

	public String getEmailRecuperacao() {
		return emailRecuperacao;
	}

	public void setEmailRecuperacao(String emailRecuperacao) {
		this.emailRecuperacao = emailRecuperacao;
	}

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}

}

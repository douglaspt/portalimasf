package br.com.pch.portalimasf.bean;

import java.io.Serializable;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.validator.constraints.Email;

import br.com.pch.portalimasf.dao.AcessoDao;
import br.com.pch.portalimasf.modelo.Acesso;
import br.com.pch.portalimasf.modelo.Beneficiario;
import br.com.pch.portalimasf.tx.Transacional;

@Named
@ViewScoped
public class AcessoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Beneficiario beneficiario = new Beneficiario();
	private Acesso acesso = new Acesso();
	
	@Email(message = "Digite um email de confirmação válido")
	private String emailConfirm;
	
	@Inject
	AcessoDao acessoDao;

	@Inject
	FacesContext context;

	@PostConstruct
	void init() {
		this.beneficiario = (Beneficiario) context.getExternalContext().getSessionMap().get("beneficiarioLogado");
		this.acesso.setBeneficiario(beneficiario);
		this.acesso.setEmail(this.beneficiario.getEmail());

	}

	@Transacional
	public String gravar() {
		
		if (! this.acesso.getEmail().equals(emailConfirm)) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("E-mail de Confirmação Incorreto"));
			return "";
		}
		
		Acesso acessoExistente = this.acessoDao.buscaPorEmail(this.acesso.getEmail());
		
		if (acessoExistente != null) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("E-mail já está cadastrado"));
			return "";
		}

		this.acesso.setDataCriacao(Calendar.getInstance());
		this.acessoDao.adiciona(this.acesso);
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Cadastro Realizado com Sucesso!"));
		this.acesso = new Acesso();
		return "login?faces-redirect=true";
	}

	public Acesso getAcesso() {
		return acesso;
	}

	public String getEmailConfirm() {
		return emailConfirm;
	}

	public void setEmailConfirm(String emailConfirm) {
		this.emailConfirm = emailConfirm;
	}
	
	

}

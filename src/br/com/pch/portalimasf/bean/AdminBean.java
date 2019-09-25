package br.com.pch.portalimasf.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pch.portalimasf.dao.AcessoDao;
import br.com.pch.portalimasf.dao.AdminDao;
import br.com.pch.portalimasf.modelo.Acesso;
import br.com.pch.portalimasf.modelo.Admin;
import br.com.pch.portalimasf.modelo.Beneficiario;

@Named
@ViewScoped
public class AdminBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String usuario;
	private String senha;
	
	private Admin admin = new Admin();
	private Acesso acesso = new Acesso();
	private Beneficiario beneficiario = new Beneficiario();
	
	@Inject
	AdminDao adminDao;
	
	@Inject
	AcessoDao acessoDao;

	@Inject
	FacesContext context;
	
	public String efetuaLoginPorUsuarioSenha() {

		System.out.println(usuario + " - " + senha);
		try {
			
			this.admin = adminDao.buscaPorUsuarioSenha(usuario, senha);
			
			if (this.admin != null) {
				context.getExternalContext().getSessionMap().put("adminLogado", this.admin);
				
				this.acesso = acessoDao.buscaPorBeneficiario(this.admin.getBeneficiario());
				context.getExternalContext().getSessionMap().put("acessoLogado", this.acesso);
				
				this.beneficiario = this.acesso.getBeneficiario();
				context.getExternalContext().getSessionMap().put("beneficiarioLogado", this.beneficiario);
				
				return "principal?faces-redirect=true";
			}
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Usuario ou senha Inválido"));

		} catch (Exception e) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Falha ao localizar Admin"));
		}
		return "";

	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

}

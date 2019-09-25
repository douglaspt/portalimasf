package br.com.pch.portalimasf.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.pch.portalimasf.modelo.Acesso;
import br.com.pch.portalimasf.modelo.Admin;
import br.com.pch.portalimasf.modelo.Beneficiario;

public class Autorizador implements PhaseListener{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();
		String nomePagina = context.getViewRoot().getViewId();
		
		if ("/login.xhtml".equals(nomePagina)) { 
	        return;
	    }	
		
		Acesso acesso = (Acesso) context.getExternalContext()
	            .getSessionMap().get("acessoLogado");
		
		Admin admin = (Admin) context.getExternalContext().getSessionMap().get("adminLogado");
		
		if((acesso != null) || (admin != null)) {
	        return;
	    }
		
		Beneficiario beneficiario = (Beneficiario) context.getExternalContext()
	            .getSessionMap().get("beneficiarioLogado");
		
		if ((beneficiario != null) && ("/cadastro.xhtml".equals(nomePagina))) {
	        return;
	    }
		
		if (("/admin.xhtml".equals(nomePagina)) || ("/uploadItemValor.xhtml".equals(nomePagina))
				|| ("/uploadCoparticipacao.xhtml".equals(nomePagina))
				
				) {
			return;
		}
		
		
		NavigationHandler handler = context.getApplication().getNavigationHandler();
	    handler.handleNavigation(context, null, "/login?faces-redirect=true");

	    context.renderResponse();

		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}

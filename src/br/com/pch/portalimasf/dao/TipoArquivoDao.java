package br.com.pch.portalimasf.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.pch.portalimasf.modelo.TipoArquivo;

public class TipoArquivoDao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;

	public TipoArquivoDao() {

	}

	public TipoArquivoDao(EntityManager em) {
		this.em = em;
	}

	public void adiciona(TipoArquivo t) {
		em.persist(t);
	}
	
	public TipoArquivo buscaPorId(Integer id) {
		TipoArquivo t = em.find(TipoArquivo.class, id);
		return t;
	}

}

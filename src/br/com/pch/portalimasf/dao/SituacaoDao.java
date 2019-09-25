package br.com.pch.portalimasf.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.pch.portalimasf.modelo.Situacao;

public class SituacaoDao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;
	
	public SituacaoDao() {

	}

	public SituacaoDao(EntityManager em) {
		super();
		this.em = em;
	}
	
	public Situacao buscaPorId(Integer id) {
		Situacao b = em.find(Situacao.class, id);
		return b;
	}
	
	

}

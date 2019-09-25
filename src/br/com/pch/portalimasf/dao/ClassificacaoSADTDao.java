package br.com.pch.portalimasf.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.pch.portalimasf.modelo.ClassificacaoSADT;

public class ClassificacaoSADTDao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	public ClassificacaoSADTDao() {
		
	}

	public ClassificacaoSADTDao(EntityManager em) {
		super();
		this.em = em;
	}

	public ClassificacaoSADT buscaPorId(Integer id) {
		ClassificacaoSADT c = em.find(ClassificacaoSADT.class, id);
		return c;
	}
	
	public void adiciona(ClassificacaoSADT c) {
		em.persist(c);
	}
}

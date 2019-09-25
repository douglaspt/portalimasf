package br.com.pch.portalimasf.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.pch.portalimasf.modelo.LoteArquivo;
import br.com.pch.portalimasf.tx.Transacional;

public class LoteArquivoDao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;
	
	public LoteArquivoDao() {
		
	}

	public LoteArquivoDao(EntityManager em) {
		super();
		this.em = em;
	}
	@Transacional
	public void adiciona(LoteArquivo l) {
		em.persist(l);
	}
	@Transacional
	public void alterar(LoteArquivo l) {
		em.merge(l);		
	}
	
	public List<LoteArquivo> listaTodos() {
		CriteriaQuery<LoteArquivo> query = em.getCriteriaBuilder().createQuery(LoteArquivo.class);
		query.select(query.from(LoteArquivo.class));

		List<LoteArquivo> lista = em.createQuery(query).setMaxResults(100).getResultList();
		return lista;
	}
	
	public List<LoteArquivo> emProcessamento() {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<LoteArquivo> query = cb.createQuery(LoteArquivo.class);
		Root<LoteArquivo> root = query.from(LoteArquivo.class);
		Path<Boolean> processadoPath = root.<Boolean> get("processado");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate processadoIgual = cb.equal(processadoPath, false);
		predicates.add(processadoIgual);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));

		TypedQuery<LoteArquivo> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			ex.printStackTrace();
			return null;

		}
	}


}

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

import br.com.pch.portalimasf.modelo.Admin;

public class AdminDao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;
	
	public AdminDao() {

	}

	public AdminDao(EntityManager em) {
		super();
		this.em = em;
	}
	
	public void adiciona(Admin a) {
		System.out.println("Adicionar Admin");
		em.persist(a);
	}
	
	public void alterar(Admin a) {
		System.out.println("Alterar Admin"+ a.getUsuario() +"  "+a.getSenha());
		em.merge(a);		
	}
	
	public Admin buscaPorUsuario(String usuario) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Admin> query = criteriaBuilder.createQuery(Admin.class);
		Root<Admin> root = query.from(Admin.class);
		Path<String> emailPath = root.<String> get("email");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate usuIgual = criteriaBuilder.equal(emailPath, usuario);
		predicates.add(usuIgual);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));

		TypedQuery<Admin> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException ex) {
			return null;

		}
	}
	
	
	public Admin buscaPorUsuarioSenha(String usuario, String senha) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Admin> query = criteriaBuilder.createQuery(Admin.class);
		Root<Admin> root = query.from(Admin.class);
		Path<String> usuPath = root.<String> get("usuario");
		Path<String> senhaPath = root.<String> get("senha");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate usuIgual = criteriaBuilder.equal(usuPath, usuario);
		predicates.add(usuIgual);

		Predicate senhaIgual = criteriaBuilder.equal(senhaPath, senha);
		predicates.add(senhaIgual);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));

		TypedQuery<Admin> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException ex) {
			return null;

		}

	}
	
	

}

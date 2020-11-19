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

import br.com.pch.portalimasf.modelo.Acesso;
import br.com.pch.portalimasf.modelo.Beneficiario;

public class AcessoDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public AcessoDao() {

	}

	public AcessoDao(EntityManager em) {
		this.em = em;
	}

	public void adiciona(Acesso a) {
		System.out.println("Adicionar Acesso");
		em.persist(a);
	}
	
	public void alterar(Acesso a) {
		System.out.println("Alterar"+ a.getEmail() +"  "+a.getSenha());
		em.merge(a);		
	}

	public Acesso buscaPorBeneficiario(Beneficiario beneficiario) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Acesso> query = cb.createQuery(Acesso.class);
		Root<Acesso> root = query.from(Acesso.class);
		query.where(cb.equal(root.get("beneficiario"), beneficiario));

		TypedQuery<Acesso> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException ex) {
			return null;

		}
	}

	public Acesso buscaPorEmail(String email) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Acesso> query = criteriaBuilder.createQuery(Acesso.class);
		Root<Acesso> root = query.from(Acesso.class);
		Path<String> emailPath = root.<String> get("email");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate emailIgual = criteriaBuilder.equal(emailPath, email);
		predicates.add(emailIgual);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));

		TypedQuery<Acesso> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException ex) {
			return null;

		}
	}

	public Acesso buscaPorEmailSenha(String email, String senha) {
		System.out.println("Aquii 03");
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Acesso> query = criteriaBuilder.createQuery(Acesso.class);
		Root<Acesso> root = query.from(Acesso.class);
		Path<String> emailPath = root.<String> get("email");
		Path<String> senhaPath = root.<String> get("senha");
		System.out.println("Aquii 04");
		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate emailIgual = criteriaBuilder.equal(emailPath, email);
		predicates.add(emailIgual);

		Predicate senhaIgual = criteriaBuilder.equal(senhaPath, senha);
		predicates.add(senhaIgual);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));

		TypedQuery<Acesso> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException ex) {
			return null;

		}

	}
	
	public List<Acesso> listaTodos() {
		CriteriaQuery<Acesso> query = em.getCriteriaBuilder().createQuery(Acesso.class);
		query.select(query.from(Acesso.class));
		List<Acesso> lista = em.createQuery(query).getResultList();
		return lista;
	}

}

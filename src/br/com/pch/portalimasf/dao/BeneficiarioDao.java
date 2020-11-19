package br.com.pch.portalimasf.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import br.com.pch.portalimasf.modelo.Beneficiario;
import br.com.pch.portalimasf.modelo.Situacao;
import br.com.pch.portalimasf.tx.Transacional;

public class BeneficiarioDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public BeneficiarioDao() {

	}

	public BeneficiarioDao(EntityManager em) {
		this.em = em;
	}

	public Beneficiario buscaPorId(Integer id) {
		Beneficiario b = em.find(Beneficiario.class, id);
		return b;
	}
	
	@Transacional
	public void adiciona(Beneficiario l) {
		em.persist(l);
	}
	
	@Transacional
	public void alterar(Beneficiario b) {
		em.merge(b);
	}

	public Beneficiario buscaPorCPFNascimento(String cpf, Calendar dataNascimento) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Beneficiario> query = criteriaBuilder.createQuery(Beneficiario.class);
		Root<Beneficiario> root = query.from(Beneficiario.class);
		Path<String> cpfPath = root.<String>get("CPF");
		Path<Calendar> dataNascimentoPath = root.<Calendar>get("dataNascimento");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate cpfIgual = criteriaBuilder.equal(cpfPath, cpf);
		predicates.add(cpfIgual);

		Predicate dataNascimentoIgual = criteriaBuilder.equal(dataNascimentoPath, dataNascimento);
		predicates.add(dataNascimentoIgual);

		Path<Integer> situacaoPath = root.<Situacao>get("situacao").<Integer>get("tipo");

		predicates.add(criteriaBuilder.lt(situacaoPath, 2));

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));

		query.orderBy(criteriaBuilder.asc(root.<Integer>get("plano")));

		// (root.<Beneficiario>get("beneficiario").<Integer>get("titularidade")),
		// cb.asc(root.get("dataEntrada")));
		System.out.println(dataNascimento.getTime()+" - "+cpf);
		TypedQuery<Beneficiario> typedQuery = em.createQuery(query).setMaxResults(1);
		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException ex) {
			ex.printStackTrace();
			return null;

		}

	}

	public Beneficiario buscaPorEmailSenha(String email, String senha) {

		System.out.println("DAO - " + email + " - " + senha);

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Beneficiario> query = criteriaBuilder.createQuery(Beneficiario.class);
		Root<Beneficiario> root = query.from(Beneficiario.class);
		Path<String> emailPath = root.<String>get("email");
		Path<String> senhaPath = root.<String>get("senha");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate emailIgual = criteriaBuilder.equal(emailPath, email);
		predicates.add(emailIgual);

		Predicate senhaIgual = criteriaBuilder.equal(senhaPath, senha);
		predicates.add(senhaIgual);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));

		TypedQuery<Beneficiario> typedQuery = em.createQuery(query).setMaxResults(1);
		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException ex) {
			return null;

		}

	}

	public Beneficiario buscaPorInscricaoTit(int inscricao, int titularidade) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Beneficiario> query = criteriaBuilder.createQuery(Beneficiario.class);
		Root<Beneficiario> root = query.from(Beneficiario.class);
		Path<Integer> inscricaoPath = root.<Integer>get("inscricao");
		Path<Integer> titularidadePath = root.<Integer>get("titularidade");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate inscricaoIgual = criteriaBuilder.equal(inscricaoPath, inscricao);
		predicates.add(inscricaoIgual);

		Predicate titularidadeIgual = criteriaBuilder.equal(titularidadePath, titularidade);
		predicates.add(titularidadeIgual);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));

		TypedQuery<Beneficiario> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException ex) {
			return null;

		}

	}

	public int buscaAleatorio() {
		long result = (Integer) em.createQuery("select id from Beneficiario ORDER BY RAND()").setMaxResults(1)
				.getSingleResult();
		return (int) result;
	}

	public List<Beneficiario> buscaPorBeneficiario(String inscricao, String nome, String cpf, Date dataNascimento) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Beneficiario> query = criteriaBuilder.createQuery(Beneficiario.class);
		Root<Beneficiario> root = query.from(Beneficiario.class);
		Path<String> inscricaoPath = root.<String>get("inscricao");
		Path<String> nomePath = root.<String>get("nome");
		Path<String> cpfPath = root.<String>get("CPF");
		Path<Calendar> dataNascimentoPath = root.<Calendar>get("dataNascimento");

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (inscricao != "" && inscricao != null) {
			Predicate inscricaoIgual = criteriaBuilder.equal(inscricaoPath, inscricao);
			predicates.add(inscricaoIgual);
		}

		if (nome != "" && nome != null) {
			Predicate nomeIgual = criteriaBuilder.like(nomePath, "%"+nome+"%");
			predicates.add(nomeIgual);	
		}
		
		if (cpf != "" && cpf != null) {
			Predicate cpfIgual = criteriaBuilder.equal(cpfPath, cpf);
			predicates.add(cpfIgual);
		}

		if (dataNascimento != null) {
			Predicate dataNascimentoIgual = criteriaBuilder.equal(dataNascimentoPath, dataNascimento);
			predicates.add(dataNascimentoIgual);	
		}

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));
		query.orderBy(criteriaBuilder.asc(root.<Integer>get("inscricao")));

		TypedQuery<Beneficiario> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			ex.printStackTrace();
			return null;

		}

	}

}

package br.com.pch.portalimasf.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.pch.portalimasf.modelo.Beneficiario;
import br.com.pch.portalimasf.modelo.ContaMedica;
import br.com.pch.portalimasf.modelo.TotalPorBeneficiario;
import br.com.pch.portalimasf.tx.Transacional;

public class ContaMedicaDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public ContaMedicaDao() {

	}

	public ContaMedicaDao(EntityManager em) {
		this.em = em;
	}

	public ContaMedica buscaPorId(Integer id) {
		ContaMedica c = em.find(ContaMedica.class, id);
		return c;
	}
	
	@Transacional
	public void adiciona(ContaMedica c) {
		em.persist(c);
	}
	
	@Transacional
	public void excluirTodos(Calendar dataDesconto) {

		Query query = em.createNativeQuery("DELETE FROM ContaMedica WHERE dataDesconto = :dataDesconto");
		query.setParameter("dataDesconto", dataDesconto).executeUpdate();
	}

	public List<ContaMedica> buscaPorBeneficiario(Beneficiario beneficiario, Calendar dataDesconto) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ContaMedica> query = cb.createQuery(ContaMedica.class);
		Root<ContaMedica> root = query.from(ContaMedica.class);

		Path<Beneficiario> beneficiarioPath = root.<Beneficiario>get("beneficiario");
		Path<Calendar> dataDescontoPath = root.<Calendar>get("dataDesconto");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate benefiIgual = cb.equal(beneficiarioPath, beneficiario);
		predicates.add(benefiIgual);

		Predicate dataDescontoIgual = cb.equal(dataDescontoPath, dataDesconto);
		predicates.add(dataDescontoIgual);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));
		query.orderBy(cb.asc(root.<Beneficiario>get("beneficiario").<Integer>get("titularidade")),
				cb.asc(root.get("dataEntrada")));

		TypedQuery<ContaMedica> typedQuery = em.createQuery(query);
		
		System.out.println("buscaPorBeneficiario: "+beneficiario.getId()+" Data: "+dataDesconto.getTime());
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			return null;

		}

	}

	public List<ContaMedica> buscaPorDependenteMenor(int inscricao, Calendar dataDesconto) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ContaMedica> query = cb.createQuery(ContaMedica.class);
		Root<ContaMedica> root = query.from(ContaMedica.class);

		Path<Integer> beneficiarioPath = root.<Beneficiario>get("beneficiario").<Integer>get("inscricao");
		Path<Calendar> dataDescontoPath = root.<Calendar>get("dataDesconto");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate prestadorIgual = cb.equal(beneficiarioPath, inscricao);
		predicates.add(prestadorIgual);

		Predicate dataDescontoIgual = cb.equal(dataDescontoPath, dataDesconto);
		predicates.add(dataDescontoIgual);

		predicates.add(cb.gt(root.<Beneficiario>get("beneficiario").<Integer>get("titularidade"), 0));
		predicates.add(cb.le(root.<Beneficiario>get("beneficiario").<Integer>get("titularidade"), 49));
		predicates.add(cb.lt(root.<Beneficiario>get("beneficiario").<Integer>get("idade"), 18));

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));
		query.orderBy(cb.asc(root.<Beneficiario>get("beneficiario").<Integer>get("titularidade")),
				cb.asc(root.get("dataEntrada")));

		System.out.println("buscaPorDependenteMenor: "+inscricao+" Data: "+dataDesconto.getTime());
		
		TypedQuery<ContaMedica> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			return null;

		}

	}

	public List<ContaMedica> buscaPorDependenteMaior(int inscricao, Calendar dataDesconto) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ContaMedica> query = cb.createQuery(ContaMedica.class);
		Root<ContaMedica> root = query.from(ContaMedica.class);

		Path<Integer> inscricaoPath = root.<Beneficiario>get("beneficiario").<Integer>get("inscricao");
		Path<Calendar> dataDescontoPath = root.<Calendar>get("dataDesconto");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate prestadorIgual = cb.equal(inscricaoPath, inscricao);
		predicates.add(prestadorIgual);

		Predicate dataDescontoIgual = cb.equal(dataDescontoPath, dataDesconto);
		predicates.add(dataDescontoIgual);

		predicates.add(cb.or(
				cb.and(cb.gt(root.<Beneficiario>get("beneficiario").<Integer>get("titularidade"), 0),
						cb.ge(root.<Beneficiario>get("beneficiario").<Integer>get("idade"), 18)),

				cb.ge(root.<Beneficiario>get("beneficiario").<Integer>get("titularidade"), 50))

		);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));
		query.orderBy(cb.asc(root.<Beneficiario>get("beneficiario").<Integer>get("titularidade")),
				cb.asc(root.get("dataEntrada")));
		
		System.out.println("buscaPorDependenteMaior: "+inscricao+" Data: "+dataDesconto.getTime());

		TypedQuery<ContaMedica> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			return null;

		}

	}
	
	public List<TotalPorBeneficiario> buscaPorDependenteMaiorAgrupado(int inscricao, Calendar dataDesconto) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TotalPorBeneficiario> query = cb.createQuery(TotalPorBeneficiario.class);
		Root<ContaMedica> conta = query.from(ContaMedica.class);
		
		query.multiselect(conta.<Beneficiario>get("beneficiario"),				
				cb.sum(conta.<Integer>get("qtde")), cb.sum(conta.<Double>get("valorPago")),
				cb.sum(conta.<Double>get("coparticipacao")));
				

		Path<Integer> inscricaoPath = conta.<Beneficiario>get("beneficiario").<Integer>get("inscricao");
		Path<Calendar> dataDescontoPath = conta.<Calendar>get("dataDesconto");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate prestadorIgual = cb.equal(inscricaoPath, inscricao);
		predicates.add(prestadorIgual);

		Predicate dataDescontoIgual = cb.equal(dataDescontoPath, dataDesconto);
		predicates.add(dataDescontoIgual);

		predicates.add(cb.or(
				cb.and(cb.gt(conta.<Beneficiario>get("beneficiario").<Integer>get("titularidade"), 0),
						cb.ge(conta.<Beneficiario>get("beneficiario").<Integer>get("idade"), 18)),

				cb.ge(conta.<Beneficiario>get("beneficiario").<Integer>get("titularidade"), 50))

		);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));
		query.groupBy(conta.<Beneficiario>get("beneficiario"));
		query.orderBy(cb.asc(conta.<Beneficiario>get("beneficiario").<Integer>get("titularidade")),
				cb.asc(conta.get("dataEntrada")));
		
		System.out.println("buscaPorDependenteMaiorAgrupado: "+inscricao+" Data: "+dataDesconto.getTime());

		TypedQuery<TotalPorBeneficiario> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			return null;

		}

	}
	

	public TotalPorBeneficiario buscaTotalPorBeneficiario(int idBeneficiario, Calendar dataDesconto) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TotalPorBeneficiario> query = cb.createQuery(TotalPorBeneficiario.class);
		Root<ContaMedica> conta = query.from(ContaMedica.class);

		query.multiselect(conta.<Beneficiario>get("beneficiario"),				
				cb.sum(conta.<Integer>get("qtde")), cb.sum(conta.<Double>get("valorPago")),
				cb.sum(conta.<Double>get("coparticipacao")));

		Path<Integer> beneficiarioPath = conta.<Beneficiario>get("beneficiario").<Integer>get("id");
		Path<Calendar> dataDescontoPath = conta.<Calendar>get("dataDesconto");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate prestadorIgual = cb.equal(beneficiarioPath, idBeneficiario);
		predicates.add(prestadorIgual);

		Predicate dataDescontoIgual = cb.equal(dataDescontoPath, dataDesconto);
		predicates.add(dataDescontoIgual);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));
		TypedQuery<TotalPorBeneficiario> typedQuery = em.createQuery(query);
		
		System.out.println("buscaTotalPorBeneficiario: "+idBeneficiario+" Data: "+dataDesconto.getTime());
		
		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException ex) {
			return null;

		}

	}
	
	
	//Adicionado em 03/12/2019 - Nova determinação
	public List<ContaMedica> buscaPorTitular(Beneficiario beneficiario, Calendar dataDesconto) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ContaMedica> query = cb.createQuery(ContaMedica.class);
		Root<ContaMedica> root = query.from(ContaMedica.class);

		Path<Integer> beneficiarioPath = root.<Beneficiario>get("beneficiario").<Integer>get("inscricao");
		Path<Calendar> dataDescontoPath = root.<Calendar>get("dataDesconto");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate benefiIgual = cb.equal(beneficiarioPath, beneficiario.getInscricao());
		predicates.add(benefiIgual);

		Predicate dataDescontoIgual = cb.equal(dataDescontoPath, dataDesconto);
		predicates.add(dataDescontoIgual);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));
		query.orderBy(cb.asc(root.<Beneficiario>get("beneficiario").<Integer>get("titularidade")),
				cb.asc(root.get("dataEntrada")));

		TypedQuery<ContaMedica> typedQuery = em.createQuery(query);
		
		System.out.println("buscaPorBeneficiario: "+beneficiario.getId()+" Data: "+dataDesconto.getTime());
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			return null;

		}

	}


	// Calendar referenciaFinal = Calendar.getInstance();
	// referenciaFinal.set(referencia.get(Calendar.YEAR),
	// referencia.get(Calendar.MONTH) + 1, 1);
	// referenciaFinal.set(Calendar.DAY_OF_MONTH,
	// referenciaFinal.get(Calendar.DAY_OF_MONTH) -1 );
	// Predicate referenciaIniFin = cb.between(referenciaPath, referencia,
	// referenciaFinal);
	// predicates.add(referenciaIniFin);

}

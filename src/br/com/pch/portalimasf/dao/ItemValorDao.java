package br.com.pch.portalimasf.dao;

import java.io.Serializable;
import java.util.ArrayList;
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

import br.com.pch.portalimasf.modelo.Conveniado;
import br.com.pch.portalimasf.modelo.ItemValor;
import br.com.pch.portalimasf.tx.Transacional;

public class ItemValorDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public ItemValorDao() {

	}

	public ItemValorDao(EntityManager em) {
		super();
		this.em = em;
	}

	public ItemValor buscaPorId(Integer id) {
		ItemValor i = em.find(ItemValor.class, id);
		return i;
	}

	@Transacional
	public void adiciona(ItemValor i) {
		em.persist(i);
	}

	@Transacional
	public void alterar(ItemValor i) {
		em.merge(i);
	}

	@Transacional
	public void excluirTodos(int idTipoArquivo) {

		Query query = em.createNativeQuery("DELETE FROM ItemValor WHERE idTipoArquivo = :idTipoArquivo");
		query.setParameter("idTipoArquivo", idTipoArquivo).executeUpdate();
	}

	public List<ItemValor> buscaItens(int idTipoArquivo, String nomeConveniado, String codPadrao,
			String descricao) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ItemValor> query = cb.createQuery(ItemValor.class);
		Root<ItemValor> root = query.from(ItemValor.class);
		Path<Integer> idTipoArquivoPath = root.<Integer>get("idTipoArquivo");
		Path<String> nomeConveniadoPath = root.<String>get("nomeConveniado");
		Path<Integer> codPadraoPath = root.<Integer>get("codPadrao");
		Path<String> descricaoPath = root.<String>get("descricaoItem");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate idTipoArquivoIgual = cb.equal(idTipoArquivoPath, idTipoArquivo);
		predicates.add(idTipoArquivoIgual);

		if (nomeConveniado != "" && nomeConveniado != null) {
			Predicate nomeConveniadoIgual = cb.equal(nomeConveniadoPath, nomeConveniado);
			predicates.add(nomeConveniadoIgual);
		}

		if (codPadrao != "" && codPadrao != null) {
			Predicate codPadraoIgual = cb.equal(codPadraoPath, codPadrao);
			predicates.add(cb.or(codPadraoIgual, cb.or(cb.equal(root.<Integer>get("codItem"), codPadrao)),
					cb.equal(root.<Integer>get("codCBHPM"), codPadrao)));
		}

		if (descricao != "" && descricao != null) {
			Predicate descricaoIgual = cb.like(descricaoPath, "%" + descricao + "%");
			predicates.add(cb.or(descricaoIgual, cb.like(root.<String>get("descricaoCBHPM"), "%" + descricao + "%")));
		}

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));
		query.orderBy(cb.asc(root.<Integer>get("descricaoItem")));

		TypedQuery<ItemValor> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			ex.printStackTrace();
			return null;

		}

	}

	public List<Conveniado> buscaConveniado(int idTipoArquivo) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Conveniado> query = criteriaBuilder.createQuery(Conveniado.class);
		Root<ItemValor> item = query.from(ItemValor.class);
		query.multiselect(item.<Integer>get("idConveniado"), item.<String>get("nomeConveniado"));
		query.where(criteriaBuilder.equal(item.get("idTipoArquivo"), idTipoArquivo));
		query.groupBy(item.<Integer>get("idConveniado"), item.<String>get("nomeConveniado"));
		query.orderBy(criteriaBuilder.asc(item.<String>get("nomeConveniado")));
		TypedQuery<Conveniado> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			return null;

		}

	}

}

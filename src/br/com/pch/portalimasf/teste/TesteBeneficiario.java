package br.com.pch.portalimasf.teste;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.pch.portalimasf.dao.BeneficiarioDao;
import br.com.pch.portalimasf.modelo.Beneficiario;

public class TesteBeneficiario {

	public static void main(String[] args) {

		// EntityManager em = new JPAUtil().getEntityManager();

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("portalimasfLocaWeb");

		EntityManager em = emf.createEntityManager();

		BeneficiarioDao dao = new BeneficiarioDao(em);
		
Calendar data = Calendar.getInstance();
		
		data.set(1975, 2, 19);
		
		Beneficiario benCPF;
		
		try {
			benCPF = dao.buscaPorCPFNascimento("246.612.688-57", data);
		} catch (Exception e) {
			System.out.println("Não foi possivel localizar o beneficiario");
			benCPF = null;
		}
			
				
		System.out.println(benCPF.getNome()+" - "+benCPF.getInscricao());
		
		
		
		/*
		Beneficiario b2 = dao.buscaPorId(10);

		int id = dao.buscaAleatorio();

		Beneficiario b = dao.buscaPorId(id);

		System.out.println(b.getEmail());
		
		System.out.println(b2.getNome());
*/
		
	}

}

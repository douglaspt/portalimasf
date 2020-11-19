package br.com.pch.portalimasf.teste;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.pch.portalimasf.dao.ContaMedicaDao;
import br.com.pch.portalimasf.modelo.Beneficiario;
import br.com.pch.portalimasf.modelo.ContaMedica;

public class TesteContamedica {
	
	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("portalimasfLocaWeb");

		EntityManager em = emf.createEntityManager();
		
		Calendar dataIni = Calendar.getInstance();
		

		dataIni.set(2019, 10, 01);
						
		ContaMedicaDao dao = new ContaMedicaDao(em);
		
		//List<ContaMedica> contas = dao.buscaPorDependenteMaior(11396, dataIni);
		
		//List<ContaMedica> contas = dao.buscaPorDependenteMenor(3930, dataIni);
		
		Beneficiario ben = new Beneficiario();
		ben.setId(128106);
		ben.setInscricao(19035);
		
		List<ContaMedica> contas = dao.buscaPorTitular(ben, dataIni);
		
		for (ContaMedica contaMedica : contas) {
			System.out.println(contaMedica.getDescricaoItem());
		}
		
				
		//TotalPorBeneficiario total = dao.buscaTotalPorBeneficiario(11, dataIni);
		
		//System.out.println(total.getValorPago());
		
		/*
		//System.out.println(conta.getDescricaoItem());
		for (ContaMedica contaMedica : contas) {
			System.out.println(contaMedica.getDescricaoItem()+" - "+contaMedica.getBeneficiario().getId());
		}
		*/
	}

}

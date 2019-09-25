package br.com.pch.portalimasf.teste;

import java.util.Calendar;

public class TesteAcesso {
	
	public static void main(String[] args) {
		/*
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("portalimasfLocal");

		EntityManager em = emf.createEntityManager();
		
		AcessoDao dao = new AcessoDao(em);
		
		Beneficiario b = new Beneficiario();
		
		b.setId(37);
		
		Acesso acesso = dao.buscaPorBeneficiario(b);
		
		System.out.println(acesso.getEmail());
		*/
		int ano = 0, mes = 0, dia = 0;
		Calendar data = Calendar.getInstance();
		
		String dataS = "26/09/2018 15:11";
		if (dataS.length() == 10) {
			dia = Integer.parseInt((String) dataS.subSequence(0, 2));
			mes = Integer.parseInt((String) dataS.subSequence(3, 5));
			ano = Integer.parseInt((String) dataS.subSequence(6, 10));
		}
		
		System.out.println(dia+"/"+mes+"/"+ano);
		
		data.set(ano, mes, dia);
		
		System.out.println(data.getTime());
		
		
	}

}

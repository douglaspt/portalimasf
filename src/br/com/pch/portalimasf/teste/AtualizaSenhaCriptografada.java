package br.com.pch.portalimasf.teste;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.pch.portalimasf.dao.AcessoDao;
import br.com.pch.portalimasf.modelo.Acesso;
import br.com.pch.portalimasf.modelo.Senha;

public class AtualizaSenhaCriptografada {
	
	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("portalimasfLocal");

		EntityManager em = emf.createEntityManager();

		AcessoDao dao = new AcessoDao(em);
		
		List<Acesso> acessos = dao.listaTodos();
		
		for (Acesso a : acessos) {
			System.out.println(a.getEmail()+" - "+a.getSenha());
			a.setSenha(Senha.criptografarSenha(a.getSenha()));
			dao.alterar(a);
		}		
		
	}
	

}

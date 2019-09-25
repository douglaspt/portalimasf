package br.com.pch.portalimasf.teste;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.pch.portalimasf.dao.ItemValorDao;
import br.com.pch.portalimasf.modelo.Conveniado;

public class TesteItem {
	
	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("portalimasfLocal");

		EntityManager em = emf.createEntityManager();
		
		ItemValorDao dao = new ItemValorDao(em);
		
	//	TipoArquivo t = new TipoArquivo();
	//	t.setId(1);
	//	dao.excluirTodos(t.getId());
		
		List<Conveniado> c = dao.buscaConveniado(1);
		
		System.out.println(c.size());
				
		for (Conveniado conveniado : c) {
			System.out.println(conveniado.getId()+" - "+conveniado.getNome());
			
		}
		
		
		
		
	}

}

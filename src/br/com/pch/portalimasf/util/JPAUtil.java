package br.com.pch.portalimasf.util;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

@ApplicationScoped
public class JPAUtil {

	//private static EntityManagerFactory emf = Persistence
	//		.createEntityManagerFactory("portalimasf");
	
	//@PersistenceUnit(unitName = "portalimasf2xxxxx")
	//private static EntityManagerFactory emf;
	
    @PersistenceUnit(unitName = "portalimasf")
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("portalimasf");

	@Produces
    @RequestScoped
	public EntityManager getEntityManager() {
		System.out.println("ABRIR CONNEXÃO");
		return emf.createEntityManager();
		
	}

	public void close(@Disposes EntityManager em) {
		
		if (em.isOpen()) {
			System.out.println("FECHAR CONNEXÃO");
            em.close();
        }
	}
	
	@PreDestroy
    public void preDestroy(){
        if (emf.isOpen()) {
        	System.out.println("FECHAR EMF");
            emf.close();
        }
    }
	
}

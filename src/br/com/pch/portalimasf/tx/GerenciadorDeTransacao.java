package br.com.pch.portalimasf.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transacional
@Interceptor
public class GerenciadorDeTransacao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
    EntityManager manager;

	@AroundInvoke
	public Object executaTX(InvocationContext contexto) throws Exception {
        manager.getTransaction().begin();

        System.out.println("Begin tran");
        Object resultado =  contexto.proceed();

        manager.getTransaction().commit();
        System.out.println("Commit");
        
        return resultado;
    }

	
	

}

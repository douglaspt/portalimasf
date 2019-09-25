package br.com.pch.portalimasf.tx;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@SuppressWarnings("serial")
@Interceptor
@Log
public class TempoDeExecucaoInterceptor  implements Serializable{

    @AroundInvoke
    public Object log(InvocationContext contexto) throws Exception {

        long inicio = System.currentTimeMillis();

        String metodo = contexto.getMethod().getName();

        Object proceder = contexto.proceed();

        long fim = System.currentTimeMillis();

        long resultado = fim - inicio;

        System.out.println("Método executado: " + metodo + " Tempo execução: " + resultado);

        return proceder;    
    }    
}

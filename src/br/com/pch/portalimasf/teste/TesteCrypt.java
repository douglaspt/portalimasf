package br.com.pch.portalimasf.teste;

import org.mindrot.jbcrypt.BCrypt;

public class TesteCrypt {
	
	public static void main(String[] args) {
		
		// Gera um sal aleatório
	    String salGerado = BCrypt.gensalt();
	    System.out.println("O sal gerado foi: " + salGerado);
	    
	    String senhaBanco = "ABC123";

	    String senhaHasheada = BCrypt.hashpw(senhaBanco, salGerado);
	    
	    System.out.println("Senha Criptografada = "+senhaHasheada);
	    
	    String senhaDigitada = "abc123";
	    
	    if (BCrypt.checkpw(senhaDigitada, senhaHasheada))
	    	System.out.println("Senha Correta");
	    else
	    	System.out.println("Senha Incorreta");
		
		
	}
	

	

}

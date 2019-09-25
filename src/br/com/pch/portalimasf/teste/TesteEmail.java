package br.com.pch.portalimasf.teste;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import br.com.pch.portalimasf.modelo.Senha;

public class TesteEmail {
	
	public static void main(String[] args) throws EmailException {
		
		/*
		String senha = Senha.getRandomPass(6);
		System.out.println(senha);
		
		//Email simples em texto
		
		//SimpleEmail email = new SimpleEmail();
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("mail-ssl.locaweb.com.br");
		email.setAuthentication("instituto@imasf.com.br", "instituto@imasf");// o servidor SMTP para envio do e-mail
		email.addTo("douglaspt@gmail.com", "Douglas Pimentel Teixeira"); //destinatário
		email.setFrom("instituto@imasf.com.br", "IMASF"); // remetente
		email.setSubject("Recuperação de Senha Portal IMASF"); // assunto do e-mail
		email.setMsg("Segue a nova senha para acesso no Portal Imasf: imasf@123"); //conteudo do e-mail
		
		//email.attach(arg0)
		System.out.println(email.send());
		
		*/
		
		//Email em HTML
		
		String senha = Senha.getRandomPass(6);
		System.out.println(senha);
		
		HtmlEmail email = new HtmlEmail();
		
		String img = "portal.imasf.com.br/portalimasf/javax.faces.resource/logoImasf.png.xhtml?ln=img";
		String nome = "Douglas";
		String emailRec = "douglaspt@gmail.com";
		
		String txtHtml = "<!DOCTYPE html><html>\r\n" + 
        		" <head>\r\n" + 
        		" <title>Portal IMASF</title>\r\n" + 
        		" </head>\r\n" + 
        		" <body>\r\n" + 
        		" <h1>Portal IMASF</h1>\r\n" + 
        		" <p>Oi, "+nome+"!</p>\r\n" + 
        		" <p>Segue a nova senha para acesso no Portal Imasf:</p>\r\n" + 
        		" <h2>"+senha+"</h2>\r\n" + 
        		" <br>\r\n" + 
        		" <p>Para se logar agora clique no logo do imasf:</p>\r\n" +
        		" <a href = \"http://portal.imasf.com.br/portalimasf/?emailRec="+emailRec+"\">\r\n"+  
        		" <img src=\""+img+"\"  alt=\"img\" style=\"width:200px\">\r\n" + 
        		" </a>\r\n" + 
        		" </body>\r\n" + 
        		" </html>";
        		
        System.out.println(txtHtml);

		
		email.setHtmlMsg(txtHtml);
		email.setSubject("Recuperação de Senha Portal IMASF");// Assunto
		email.setHostName("mail-ssl.locaweb.com.br");// - endereco smtp do servidor;
		//email.setSmtpPort("porta do smtp");// servidor porta do servidor;
		email.setAuthentication("instituto@imasf.com.br", "instituto@imasf");

		email.addTo("douglaspt@gmail.com", "Douglas Pimentel Teixeira"); //destinatário
		email.setFrom("instituto@imasf.com.br", "IMASF"); // remetente

		//System.out.println(email.send());
		
		
	}

}

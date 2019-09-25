package br.com.pch.portalimasf.modelo;

import org.apache.commons.mail.HtmlEmail;

public class EnviaEmail {

	public static boolean enviaEmail(String email, String nome, String senhaNova) {
		try {

			HtmlEmail emailEnvio = new HtmlEmail();

			String img = "portal.imasf.com.br/portalimasf/javax.faces.resource/logoImasf.png.xhtml?ln=img";

			// String img = email.embed(new
			// File("./WebContent/resources/img/logoImasf.png"));

			// html text
			String txtHtml = "<!DOCTYPE html><html>\r\n" + 
	        		" <head>\r\n" + 
	        		" <title>Portal IMASF</title>\r\n" + 
	        		" </head>\r\n" + 
	        		" <body>\r\n" + 
	        		" <h1>Portal IMASF</h1>\r\n" + 
	        		" <p>Oi, "+nome+"!</p>\r\n" + 
	        		" <p>Segue a nova senha para acesso no Portal Imasf:</p>\r\n" + 
	        		" <h2>"+senhaNova+"</h2>\r\n" + 
	        		" <br>\r\n" + 
	        		" <p>Para se logar agora clique no logo do imasf:</p>\r\n" +
	        		//" <a href = \"http://portal.imasf.com.br/portalimasf/?emailRec="+email+"\">\r\n"+
	        		" <a href = \"http://localhost:8080/portalimasf/?emailRec="+email+"\">\r\n"+
	        		" <img src=\""+img+"\"  alt=\"img\" style=\"width:200px\">\r\n" + 
	        		" </a>\r\n" + 
	        		" </body>\r\n" + 
	        		" </html>";
			

			System.out.println(txtHtml);

			emailEnvio.setHtmlMsg(txtHtml);
			emailEnvio.setSubject("Recuperação de Senha Portal IMASF");// Assunto
			emailEnvio.setHostName("mail-ssl.locaweb.com.br");// - endereco smtp do servidor;
			// email.setSmtpPort("porta do smtp");// servidor porta do servidor;
			emailEnvio.setAuthentication("instituto@imasf.com.br", "instituto@imasf");

			emailEnvio.addTo(email, nome); // destinatário
			emailEnvio.setFrom("instituto@imasf.com.br", "IMASF"); // remetente

			emailEnvio.send();
			return true;

		} catch (Exception e) {
			System.out.println("Erro ao enviar email");
			return false;
		}

	}

}

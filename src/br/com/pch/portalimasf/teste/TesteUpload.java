package br.com.pch.portalimasf.teste;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class TesteUpload {

	public static void main(String[] args) throws IOException {

		String path = "C:/wIMASF/portalimasf/WebContent/resources/upload/";

		String arquivo = "arquivo.txt";
      //  String arqZip = "Documento.zip";
        
		//compactar(path, arquivo);
		//listarArquivos(path + arqZip);
		//descompactar(path, arqZip);
		// escritor(path+"file.txt");
		leitor(path+arquivo);

	}

	public static void compactar(String path, String arquivo) throws IOException {

		try {
			BufferedInputStream sOrigem = null;
			FileOutputStream sDestino = new FileOutputStream(path + "Documento.zip");
			ZipOutputStream sArquivoZip = new ZipOutputStream(new BufferedOutputStream(sDestino));
			FileInputStream sArquivoEntrada = new FileInputStream(path + arquivo);

			int buffer = 2048;
			byte data[] = new byte[buffer];
			sOrigem = new BufferedInputStream(sArquivoEntrada, buffer);
			ZipEntry entradaZip = new ZipEntry(arquivo);
			sArquivoZip.putNextEntry(entradaZip);

			int contador;

			while ((contador = sOrigem.read(data)) != -1) {
				sArquivoZip.write(data, 0, contador);
			}

			sOrigem.close();
			sArquivoZip.close();
			System.out.println(path + arquivo);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void descompactar(String path, String arquivo) {

		try {
			int buffer = 2048;
			InputStream sArquivoZip = null;
			OutputStream sArquivoDesc = null;
			ZipFile arquivoZip = new ZipFile(path+arquivo);
			@SuppressWarnings("unchecked")
			Enumeration<ZipEntry> e = (Enumeration<ZipEntry>) arquivoZip.entries();
			while (e.hasMoreElements()) {
				ZipEntry entradaZip = (ZipEntry) e.nextElement();
				System.out.println("Descompactando: " + entradaZip.getName());
				sArquivoZip = arquivoZip.getInputStream(entradaZip);
				sArquivoDesc = new FileOutputStream(path+entradaZip.getName());
				int contador;
				byte data[] = new byte[buffer];
				while ((contador = sArquivoZip.read(data)) > 0) {
					sArquivoDesc.write(data, 0, contador);
				}
			}
			arquivoZip.close();
			sArquivoDesc.close();

		} catch (Exception e) {
			// Tratamento erros
		}
	}

	public static void listarArquivos(String zipfile) {
		try {
			ZipFile arquivoZip = new ZipFile(zipfile);
			@SuppressWarnings("unchecked")
			Enumeration<ZipEntry> e = (Enumeration<ZipEntry>) arquivoZip.entries();
			while (e.hasMoreElements()) {
				ZipEntry entradaZip = (ZipEntry) e.nextElement();
				System.out.println("Nome do arquivo: " + entradaZip.getName());
				System.out.println("Tamanho do arquivo: " + entradaZip.getSize());
			}
			arquivoZip.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void leitor(String path) throws IOException {
		BufferedReader buffRead = new BufferedReader(new FileReader(path));
		String linha = null;
		while ((linha = buffRead.readLine()) != null ) {
			
				String campos[] = linha.split(";");
				System.out.println(campos.length);
				System.out.println(linha);
				//System.out.println(campos[0]+" - "+campos[1]+" - "+campos[2]);

		}
		buffRead.close();
	}

	public static void escritor(String path) throws IOException {
		BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path));
		String linha = "";
		Scanner in = new Scanner(System.in);
		System.out.println("Escreva algo: ");
		linha = in.nextLine();
		buffWrite.append(linha + "\n");
		in.close();
		buffWrite.close();
	}

	/*
	 * 
	 * try { InputStream inputStream = new BufferedInputStream( new
	 * FileInputStream("C:/wIMASF/portalimasf/WebContent/resources/tmp/Arq_1002.txt"
	 * ));
	 * 
	 * // System.out.println(convertStreamToString(inputStream));
	 * 
	 * byte[] dataAsByte = new byte[10];
	 * 
	 * int data = inputStream.read(dataAsByte);
	 * 
	 * while (data != -1) { System.out.println((char) data);
	 * 
	 * data = inputStream.read();
	 * 
	 * }
	 * 
	 * inputStream.close();
	 * 
	 * } catch (FileNotFoundException e) { e.printStackTrace(); } catch (Exception
	 * e) { e.printStackTrace(); } }
	 * 
	 */

}

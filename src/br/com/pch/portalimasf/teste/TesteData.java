package br.com.pch.portalimasf.teste;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TesteData {
	
	public static void main(String[] args) {
		
		Integer a = 0;
		System.out.println(a);
		
		
		Calendar data1 = Calendar.getInstance();
		
		data1.set(2018, 1, 1);
		System.out.println(data1.getTime());
		
		Calendar data2 = Calendar.getInstance();
		data2.set(data1.get(Calendar.YEAR), data1.get(Calendar.MONTH) + 1, 1);
		data2.set(Calendar.DAY_OF_MONTH, data2.get(Calendar.DAY_OF_MONTH) -1 );
		System.out.println(data1.getTime());
		
		System.out.println(data2.getTime());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println("Data atual com formatação: "+ dateFormat.format(data1.getTime()));
		
		
	}
	

}

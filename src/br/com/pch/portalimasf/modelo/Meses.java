package br.com.pch.portalimasf.modelo;

public enum Meses {
	JANEIRO(0), FEVEREIRO(1), MARÇO(2), ABRIL(3), MAIO(4), JUNHO(5),   
    JULHO(6), AGOSTO(7), SETEMBRO(8), OUTUBRO(9), NOVEMBRO(10), DEZEMBRO(11);
	
    private int numMes;
    
    Meses(int numMes){
       this.numMes = numMes;
    }
    public int getNumMes(){
       return this.numMes;
    }

}

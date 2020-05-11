package teste;

import importacaoOfxPadrao.importacaoOfxPadrao;

public class teste {

    public static void main(String[] args) {
        int mes = 4;
        int ano = 2019;
        Integer nroBanco = 1;
        String banco = "Banco do Brasil";
        String filtroBanco = "001";
        
        System.out.println(importacaoOfxPadrao.principal(mes, ano, nroBanco, banco, filtroBanco, "").replaceAll("<br>", "\n"));
    }
    
}

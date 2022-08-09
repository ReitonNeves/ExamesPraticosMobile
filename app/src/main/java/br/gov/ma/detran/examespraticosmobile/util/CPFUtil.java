package br.gov.ma.detran.examespraticosmobile.util;

public class CPFUtil {

    public static String formatar(String cpf){

        String cpfFormatado = cpf.substring(0, 3) + "."
                + cpf.substring(3,6) + "."
                + cpf.substring(6,9) + "-"
                + cpf.substring(9,11)
                ;

        return cpfFormatado;

    }

}

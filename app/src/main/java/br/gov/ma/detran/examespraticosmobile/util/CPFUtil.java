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

    public static String formatarSemCaracteres(String cpf) throws NegocioException {
        if(cpf.isEmpty()){
            throw new NegocioException(" Informe o CPF.");
        }
        return cpf.replace(".", "").replace("-", "");
    }




}

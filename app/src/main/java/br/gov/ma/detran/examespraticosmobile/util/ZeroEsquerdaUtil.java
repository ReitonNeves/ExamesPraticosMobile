package br.gov.ma.detran.examespraticosmobile.util;

public class ZeroEsquerdaUtil {

    public static String preencher(int tamanho, String texto){
        String retorno = texto;
        for (int i = texto.length(); i < 11; i++) {
            retorno = "0" + retorno;
        }

        return retorno;
    }

}

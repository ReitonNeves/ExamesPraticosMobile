package br.gov.ma.detran.examespraticosmobile.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataHoraUtil {

    public static String retornarDataHoraAtual_yyyyMMdd_HHmmss(){
        SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date data = new Date();
        return formataData.format(data);
    }

    public static String retornarHoraAtual_HHmmss(){
        SimpleDateFormat formataData = new SimpleDateFormat("HH:mm:ss");
        Date data = new Date();
        return formataData.format(data);
    }

}

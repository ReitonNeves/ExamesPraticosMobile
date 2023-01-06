package br.gov.ma.detran.examespraticosmobile.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    public static String retornarDatayyyyMMdd(String dataExame) {
        DateTimeFormatter formataData = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate data = LocalDate.parse(dataExame);
        return formataData.format(data);
    }

    public static LocalTime parseLocalTime(String turma){
        LocalTime horarioTurma;

        if(Integer.valueOf(turma) <  800){
            turma = turma.concat("00");
            horarioTurma = LocalTime.of(Integer.valueOf(turma.substring(0,2)), Integer.valueOf(turma.substring(2,4)));
        }else {
            horarioTurma = LocalTime.of(Integer.valueOf(turma.substring(0,1)), Integer.valueOf(turma.substring(1)));
        }
        return  horarioTurma;
    }

    public static boolean valida(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate d = LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static String formataData(String data) throws NegocioException {
        if (DataHoraUtil.valida(data)){
            return data.substring(6, 10) + "-" + data.substring(3,5) + "-" + data.substring(0,2);
        } else{
            throw new NegocioException("Data inválida!");
        }
    }

}

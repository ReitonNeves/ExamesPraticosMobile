package br.gov.ma.detran.examespraticosmobile.modeloEspecializada;

//import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.LinkedHashMap;

import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Candidato;
import br.gov.ma.detran.examespraticosmobile.util.DataHoraUtil;

public class ResultadoExameMap extends LinkedHashMap<String, String> {

    public ResultadoExameMap(AGC_Prova_Candidato exameFinalizado) {
        this.put("WSIN_USUARIO", " ");
        this.put("WSIN_SENHA", " ");
        this.put("WSIN_DATA", DataHoraUtil.retornarDatayyyyMMdd(exameFinalizado.getDataExame()));
        this.put("WSIN_TIPO", "02");//cod 2 - exame prático
        this.put("WSIN_RENACH", exameFinalizado.getRenach());
        this.put("WSIN_LOCAL", exameFinalizado.getLocalExame());
        this.put("WSIN_TURMA", exameFinalizado.getTurma());
        this.put("WSIN_NOME", exameFinalizado.getNome());
        this.put("WSIN_RESULTADO", exameFinalizado.getResultado());
        this.put("WSIN_CPF_EXAMINADOR", exameFinalizado.getCpfExaminador1());
        this.put("WSIN_NUMEROPROVA", String.valueOf(exameFinalizado.getId()));
        this.put("WSIN_NUMEROACERTOS", "");
        this.put("WSIN_NOTA", "");
    }

}

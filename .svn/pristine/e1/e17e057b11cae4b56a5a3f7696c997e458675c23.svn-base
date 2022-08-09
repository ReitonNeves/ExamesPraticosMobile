package br.gov.ma.detran.examespraticosmobile.sincronizacao;

import android.content.Context;

import org.json.JSONException;

import java.util.List;

import br.gov.ma.detran.examespraticosmobile.dao.rest.AGC_LocalDeProvaRest;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_LocalDeProva;
import br.gov.ma.detran.examespraticosmobile.service.AGC_LocalDeProvaService;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;

public class AGC_LocalDeProvaSinc {

    AGC_LocalDeProvaRest agcLocalDeProvaRest = new AGC_LocalDeProvaRest();
    AGC_LocalDeProvaService agcLocalDeProvaService = new AGC_LocalDeProvaService();

    public void sincronizarTabelaDeLocaisDeProva(Context context) throws JSONException, NegocioException {

        agcLocalDeProvaService.limparDados(context);
        List<AGC_LocalDeProva> agcLocalDeProvaList = agcLocalDeProvaRest.listar();
        if (agcLocalDeProvaList != null && agcLocalDeProvaList.size() > 0){
            for (AGC_LocalDeProva agcLocalDeProva:agcLocalDeProvaList) {
                agcLocalDeProvaService.inserir(agcLocalDeProva, context);
            }
        }

    }

}

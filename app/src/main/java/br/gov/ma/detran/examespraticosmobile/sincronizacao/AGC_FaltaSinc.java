package br.gov.ma.detran.examespraticosmobile.sincronizacao;

import android.content.Context;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import br.gov.ma.detran.examespraticosmobile.dao.rest.AGC_FaltaRest;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Falta;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Usuario;
import br.gov.ma.detran.examespraticosmobile.service.AGC_FaltaService;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;

public class AGC_FaltaSinc {

    private AGC_FaltaService agcFaltaService = new AGC_FaltaService();
    private AGC_FaltaRest agcFaltaRest = new AGC_FaltaRest();

    public void sincronizarTabelaDeFaltas(Context context) throws NegocioException, JSONException {

        agcFaltaService.limparDados(context);

        List<AGC_Falta> agcFaltaList = agcFaltaRest.listar();

        if (agcFaltaList != null && agcFaltaList.size() > 0){

            for (AGC_Falta agcFalta : agcFaltaList) {
                agcFaltaService.inserir(agcFalta, context);
            }

        }

    }
}

package br.gov.ma.detran.examespraticosmobile.dao.rest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.gov.ma.detran.examespraticosmobile.modelo.AGC_LocalDeProva;
import br.gov.ma.detran.examespraticosmobile.util.ConstantesUtil;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;
import br.gov.ma.detran.examespraticosmobile.util.RedeUtil;

public class AGC_LocalDeProvaRest {

    ConstantesUtil constantesUtil = new ConstantesUtil();

    private String mensagemRespostaRest = "Não foi possível obter resposta dos Dados dos Locais de Prova.";

    public List<AGC_LocalDeProva> listar() throws JSONException, NegocioException {

        String url = constantesUtil.URL_REST + "/LocalDeProvaRest/listarTodos";
        String resposta = RedeUtil.getJSONFromAPI(url);

        if (resposta.equals("")){
            throw new NegocioException(mensagemRespostaRest);
        }

        List<AGC_LocalDeProva> agcLocalDeProvaList = parseJson(resposta);

        return agcLocalDeProvaList;

    }

    private List<AGC_LocalDeProva> parseJson(String json) throws JSONException {

        List<AGC_LocalDeProva> agcLocalDeProvaList = null;

        JSONObject jsonObj = new JSONObject(json.replace("[", "{\"root\": [").replace("]", "]}"));

        JSONArray array = jsonObj.getJSONArray("root");

        if (array.length() == 0){
            return null;
        } else {

            agcLocalDeProvaList = new ArrayList<AGC_LocalDeProva>();

            for (int i = 0; i < array.length() - 1; i++) {

                JSONObject objArray = array.getJSONObject(i);

                AGC_LocalDeProva agcLocalDeProva = new AGC_LocalDeProva();

                agcLocalDeProva.setId(objArray.getLong("id"));
                agcLocalDeProva.setDescricao(objArray.getString("descricao"));

                agcLocalDeProvaList.add(agcLocalDeProva);

            }

        }

        return agcLocalDeProvaList;

    }
}

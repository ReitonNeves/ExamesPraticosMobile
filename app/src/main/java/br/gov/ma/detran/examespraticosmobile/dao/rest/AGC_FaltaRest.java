package br.gov.ma.detran.examespraticosmobile.dao.rest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Falta;
import br.gov.ma.detran.examespraticosmobile.util.ConstantesUtil;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;
import br.gov.ma.detran.examespraticosmobile.util.RedeUtil;

public class AGC_FaltaRest {

    ConstantesUtil contantesUtil = new ConstantesUtil();

    private String mensagemRespostaRest = "Não foi possível obter resposta dos Dados das Faltas.";

    public List<AGC_Falta> listar() throws NegocioException, JSONException {

        String url = contantesUtil.URL_REST + "/AGCFaltaRest/listarTodos";
        String resposta = RedeUtil.getJSONFromAPI(url);

        if (resposta.equals("")){
            throw new NegocioException(mensagemRespostaRest);
        }

        List<AGC_Falta> agcFaltaList = parseJson(resposta);

        return agcFaltaList;

    }

    private List<AGC_Falta> parseJson(String json) throws JSONException {

        List<AGC_Falta> agcFaltaList = null;

        JSONObject jsonObj = new JSONObject(json.replace("[", "{\"root\": [").replace("]", "]}"));

        JSONArray array = jsonObj.getJSONArray("root");

        if (array.length() == 0){
            return null;
        } else {

            agcFaltaList = new ArrayList<AGC_Falta>();

            for (int i = 0; i < array.length() - 1; i++) {

                JSONObject objArray = array.getJSONObject(i);

                AGC_Falta agcFalta = new AGC_Falta();

                agcFalta.setTipoFalta(objArray.getString("tipoFalta"));
                agcFalta.setTipoExame(objArray.getString("tipoExame"));
                agcFalta.setPontos(objArray.getInt("pontos"));
                agcFalta.setItemLetra(objArray.getString("itemLetra"));
                agcFalta.setDescricao(objArray.getString("descricao"));

                agcFaltaList.add(agcFalta);

            }

        }

        return agcFaltaList;

    }
}

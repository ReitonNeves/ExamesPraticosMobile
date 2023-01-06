package br.gov.ma.detran.examespraticosmobile.dao.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Falta;
import br.gov.ma.detran.examespraticosmobile.util.ConstantesUtil;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;
import br.gov.ma.detran.examespraticosmobile.util.RedeUtil;

public class AGC_Prova_FaltaRest {

    ConstantesUtil constantesUtil = new ConstantesUtil();

    private String mensagemRespostaRest = "Não foi possível obter resposta dos Dados das Faltas.";

    public void inserir(AGC_Prova_Falta agcProvaFalta) throws NegocioException, UnsupportedEncodingException {

        String url = constantesUtil.URL_REST + "/AGCProvaFaltaRest/inserir/" +
                agcProvaFalta.getCpfCandidato() + "/" +
                agcProvaFalta.getDataExame() + "/" +
                agcProvaFalta.getLocalExame() + "/" +
                agcProvaFalta.getTipoExame() + "/" +
                agcProvaFalta.getTipoFalta() + "/" +
                agcProvaFalta.getItemLetra() + "/" +
                agcProvaFalta.getQuantidadeDeFaltas() + "/" +
                agcProvaFalta.getCpfInclusao() + "/" +
                agcProvaFalta.getDataHoraInclusao().replace(" ", "+") + "/" +
                agcProvaFalta.getObservacoes(); //URLEncoder.encode(agcProvaFalta.getDataHoraInclusao(), "UTF-8")


        String resposta = RedeUtil.getJSONFromAPI(url, "POST");

        if (resposta.equals("ok") == false){
            throw new NegocioException(mensagemRespostaRest);
        }

    }

    public void remover(String cpfCandidato,String dataExame,String localExame,String tipoExame) throws NegocioException {

        String url = constantesUtil.URL_REST + "/AGCProvaFaltaRest/remover/" +
                cpfCandidato + "/" +
                dataExame + "/" +
                localExame + "/" +
                tipoExame
                ;

        String resposta = RedeUtil.getJSONFromAPI(url, "DELETE");

        if (resposta.equals("ok") == false){
            throw new NegocioException(mensagemRespostaRest);
        }

    }

}

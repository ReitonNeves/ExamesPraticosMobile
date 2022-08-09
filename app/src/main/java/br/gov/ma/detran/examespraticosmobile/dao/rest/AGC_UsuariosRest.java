package br.gov.ma.detran.examespraticosmobile.dao.rest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Usuario;
import br.gov.ma.detran.examespraticosmobile.util.ConstantesUtil;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;
import br.gov.ma.detran.examespraticosmobile.util.RedeUtil;

public class AGC_UsuariosRest {

    ConstantesUtil constantesUtil = new ConstantesUtil();

    private String mensagemRespostaRest = "Não foi possível obter resposta dos Dados dos Usuários.";

    public List<AGC_Usuario> listar() throws IOException, JSONException, NegocioException {

        String url = constantesUtil.URL_REST + "/AGCUsuarioRest/listarTodos";
        String resposta = RedeUtil.getJSONFromAPI(url);

        if (resposta.equals("")){
            throw new NegocioException(mensagemRespostaRest);
        }

        List<AGC_Usuario> agcUsuarios = parseJson(resposta);

        return agcUsuarios;

    }

    private List<AGC_Usuario> parseJson(String json) throws JSONException {

        List<AGC_Usuario> agcUsuarios = null;

        JSONObject jsonObj = new JSONObject(json.replace("[", "{\"usuarios\": [").replace("]", "]}"));

        JSONArray array = jsonObj.getJSONArray("usuarios");

        if (array.length() == 0){
            return null;
        } else {

            agcUsuarios = new ArrayList<AGC_Usuario>();

            for (int i = 0; i < array.length(); i++) {

                JSONObject objArray = array.getJSONObject(i);

                AGC_Usuario agcUsuario = new AGC_Usuario();

                agcUsuario.setIdUsuario(Long.parseLong(objArray.getString("idUsuario")));
                agcUsuario.setLogin(objArray.getString("login"));
                agcUsuario.setCpfUsuario(objArray.getString("cpfUsuario"));
                agcUsuario.setNome(objArray.getString("nome"));
                agcUsuario.setSenha(objArray.getString("senha"));
                agcUsuario.setTipoUsuario(objArray.getString("tipoUsuario"));

                agcUsuarios.add(agcUsuario);

            }

        }

        return agcUsuarios;

    }

}

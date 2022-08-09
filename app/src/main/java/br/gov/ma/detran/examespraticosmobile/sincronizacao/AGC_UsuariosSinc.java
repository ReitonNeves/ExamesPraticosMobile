package br.gov.ma.detran.examespraticosmobile.sincronizacao;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import br.gov.ma.detran.examespraticosmobile.dao.rest.AGC_UsuariosRest;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Usuario;
import br.gov.ma.detran.examespraticosmobile.service.AGC_UsuariosService;
import br.gov.ma.detran.examespraticosmobile.sqlite.operations.AGC_UsuariosOperations;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;

public class AGC_UsuariosSinc {

    AGC_UsuariosRest agcUsuariosRest = new AGC_UsuariosRest();
    AGC_UsuariosService agcUsuarioService = new AGC_UsuariosService();

    public void sincronizarTabelaDeUsuarios(Context context) throws IOException, JSONException, NegocioException {

        agcUsuarioService.limparDados(context);

        List<AGC_Usuario> agcUsuarios = agcUsuariosRest.listar();

        if (agcUsuarios != null && agcUsuarios.size() > 0){

            for (AGC_Usuario agcUsuario : agcUsuarios) {
                agcUsuarioService.inserir(agcUsuario, context);
            }

        }

    }

}

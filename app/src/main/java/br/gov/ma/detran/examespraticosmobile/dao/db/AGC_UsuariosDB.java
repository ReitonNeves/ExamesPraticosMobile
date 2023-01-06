package br.gov.ma.detran.examespraticosmobile.dao.db;

import android.content.Context;

import java.util.List;

import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Usuario;
import br.gov.ma.detran.examespraticosmobile.sqlite.operations.AGC_UsuariosOperations;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;


public class AGC_UsuariosDB {

    private AGC_UsuariosOperations agcUsuariosOperations;

    public void inserir(AGC_Usuario agcUsuario, Context context) throws NegocioException {
        agcUsuariosOperations = new AGC_UsuariosOperations(context);
        agcUsuariosOperations.open();
        agcUsuariosOperations.inserir(agcUsuario);
        agcUsuariosOperations.close();
    }

    public AGC_Usuario retornarPorID(long id, Context context){
        agcUsuariosOperations = new AGC_UsuariosOperations(context);
        agcUsuariosOperations.open();
        AGC_Usuario agcUsuario = agcUsuariosOperations.retornarPorID(id);
        agcUsuariosOperations.close();
        return agcUsuario;
    }

    public AGC_Usuario retornarPorCpf(String cpf, Context context) {
        agcUsuariosOperations = new AGC_UsuariosOperations(context);
        agcUsuariosOperations.open();
        AGC_Usuario agcUsuario = agcUsuariosOperations.retornarPorCpf(cpf);
        agcUsuariosOperations.close();
        return agcUsuario;
    }

    public AGC_Usuario retornarPorLoginSenha(String login, String senha, Context context){
        agcUsuariosOperations = new AGC_UsuariosOperations(context);
        agcUsuariosOperations.open();
        AGC_Usuario agcUsuario = agcUsuariosOperations.retornarPorLoginSenha(login, senha);
        agcUsuariosOperations.close();
        return agcUsuario;
    }

    public void limparDados(Context context) throws NegocioException {
        agcUsuariosOperations = new AGC_UsuariosOperations(context);
        agcUsuariosOperations.open();
        agcUsuariosOperations.limparDados();
        agcUsuariosOperations.close();
    }

    public List<AGC_Usuario> retornarTodos(Context context){
        agcUsuariosOperations = new AGC_UsuariosOperations(context);
        agcUsuariosOperations.open();
        List<AGC_Usuario> agcUsuarios = agcUsuariosOperations.retornarTodos();
        agcUsuariosOperations.close();

        return agcUsuarios;
    }
}

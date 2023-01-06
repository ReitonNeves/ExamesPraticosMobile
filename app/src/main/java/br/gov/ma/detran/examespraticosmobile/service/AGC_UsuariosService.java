package br.gov.ma.detran.examespraticosmobile.service;

import android.content.Context;

import java.util.List;

import br.gov.ma.detran.examespraticosmobile.dao.db.AGC_UsuariosDB;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Usuario;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;

public class AGC_UsuariosService {

    AGC_UsuariosDB agcUsuariosDB = new AGC_UsuariosDB();

    public void inserir(AGC_Usuario agcUsuario, Context context) throws NegocioException {

        if (agcUsuario == null){
            throw new NegocioException("Usuário deve ser informado.");
        }

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        agcUsuariosDB.inserir(agcUsuario, context);

    }

    public AGC_Usuario retornarPorID(long id, Context context) throws NegocioException {
        if (id <= 0){
            throw new NegocioException("Id deve ser informado.");
        }

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        return agcUsuariosDB.retornarPorID(id, context);
    }

    public AGC_Usuario retornarPorCpf(String cpf, Context context) throws NegocioException {
        if (cpf.isEmpty()){
            throw new NegocioException("O CPF deve ser informado.");
        }

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        return agcUsuariosDB.retornarPorCpf(cpf, context);
    }

    public AGC_Usuario retornarPorLoginSenha(String login, String senha, Context context) throws NegocioException {
        if (login == null || login.isEmpty()){
            throw new NegocioException("Login deve ser informado.");
        }

        if (senha == null || senha.isEmpty()){
            throw new NegocioException("Senha deve ser informada.");
        }

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        return agcUsuariosDB.retornarPorLoginSenha(login.trim(), senha, context);
    }

    public void limparDados(Context context) throws NegocioException {
        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        agcUsuariosDB.limparDados(context);
    }

    public List<AGC_Usuario> retornarTodos(Context context) throws NegocioException {
        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        return agcUsuariosDB.retornarTodos(context);
    }
}

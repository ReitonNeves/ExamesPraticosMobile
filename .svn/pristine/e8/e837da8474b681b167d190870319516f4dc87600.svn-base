package br.gov.ma.detran.examespraticosmobile.service;

import android.content.Context;

import java.util.List;

import br.gov.ma.detran.examespraticosmobile.dao.db.AGC_LocalDeProvaDB;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_LocalDeProva;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;

public class AGC_LocalDeProvaService {

    AGC_LocalDeProvaDB agcLocalDeProvaDB = new AGC_LocalDeProvaDB();

    public void inserir(AGC_LocalDeProva agcLocalDeProva, Context context) throws NegocioException {
        if (agcLocalDeProva == null){
            throw new NegocioException("Local de Prova deve ser informado.");
        }
        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }
        agcLocalDeProvaDB.inserir(agcLocalDeProva, context);
    }

    public AGC_LocalDeProva retornarPorID(Long id, Context context) throws NegocioException {
        if (id == null || id == 0){
            throw new NegocioException("ID deve ser informado.");
        }
        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }
        return agcLocalDeProvaDB.retornarPorID(id, context);
    }

    public AGC_LocalDeProva retornarPorDescricao(String descricao, Context context) throws NegocioException {
        if (descricao == null || descricao.isEmpty()){
            throw new NegocioException("Descrição deve ser informada.");
        }
        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }
        return agcLocalDeProvaDB.retornarPorDescricao(descricao.toUpperCase(), context);
    }

    public List<AGC_LocalDeProva> retornarTodosOrdenadoPorId(Context context) throws NegocioException {
        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }
        return agcLocalDeProvaDB.retornarTodosOrdenadoPorId(context);
    }

    public List<AGC_LocalDeProva> retornarTodosOrdenadoPorDescricao(Context context) throws NegocioException {
        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }
        return agcLocalDeProvaDB.retornarTodosOrdenadoPorDescricao(context);
    }

    public void limparDados(Context context) throws NegocioException {
        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }
        agcLocalDeProvaDB.limparDados(context);
    }

}

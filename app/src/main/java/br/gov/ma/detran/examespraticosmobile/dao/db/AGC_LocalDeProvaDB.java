package br.gov.ma.detran.examespraticosmobile.dao.db;

import android.content.Context;

import java.util.List;

import br.gov.ma.detran.examespraticosmobile.modelo.AGC_LocalDeProva;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Candidato;
import br.gov.ma.detran.examespraticosmobile.sqlite.operations.AGC_LocalDeProvaOperations;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;

public class AGC_LocalDeProvaDB {

    private AGC_LocalDeProvaOperations agcLocalDeProvaOperations;

    public void inserir(AGC_LocalDeProva agcLocalDeProva, Context context) throws NegocioException {
        agcLocalDeProvaOperations = new AGC_LocalDeProvaOperations(context);
        agcLocalDeProvaOperations.open();
        agcLocalDeProvaOperations.inserir(agcLocalDeProva);
        agcLocalDeProvaOperations.close();
    }

    public AGC_LocalDeProva retornarPorID(long id, Context context){
        agcLocalDeProvaOperations = new AGC_LocalDeProvaOperations(context);
        agcLocalDeProvaOperations.open();
        AGC_LocalDeProva agcLocalDeProva = agcLocalDeProvaOperations.retornarPorID(id);
        agcLocalDeProvaOperations.close();
        return agcLocalDeProva;
    }

    public AGC_LocalDeProva retornarPorDescricao(String descricao, Context context){
        agcLocalDeProvaOperations = new AGC_LocalDeProvaOperations(context);
        agcLocalDeProvaOperations.open();
        AGC_LocalDeProva agcLocalDeProva = agcLocalDeProvaOperations.retornarPorDescricao(descricao);
        agcLocalDeProvaOperations.close();
        return agcLocalDeProva;
    }

    public List<AGC_LocalDeProva> retornarTodosOrdenadoPorId(Context context){
        agcLocalDeProvaOperations = new AGC_LocalDeProvaOperations(context);
        agcLocalDeProvaOperations.open();
        List<AGC_LocalDeProva> agcLocalDeProvaList = agcLocalDeProvaOperations.retornarTodosOrdenadoPorId();
        agcLocalDeProvaOperations.close();
        return agcLocalDeProvaList;
    }

    public List<AGC_LocalDeProva> retornarTodosOrdenadoPorDescricao(Context context){
        agcLocalDeProvaOperations = new AGC_LocalDeProvaOperations(context);
        agcLocalDeProvaOperations.open();
        List<AGC_LocalDeProva> agcLocalDeProvaList = agcLocalDeProvaOperations.retornarTodosOrdenadoPorDescricao();
        agcLocalDeProvaOperations.close();
        return agcLocalDeProvaList;
    }

    public void limparDados(Context context) throws NegocioException {
        agcLocalDeProvaOperations = new AGC_LocalDeProvaOperations(context);
        agcLocalDeProvaOperations.open();
        agcLocalDeProvaOperations.limparDados();
        agcLocalDeProvaOperations.close();
    }

}

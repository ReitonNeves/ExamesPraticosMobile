package br.gov.ma.detran.examespraticosmobile.dao.db;

import android.content.Context;

import java.util.List;

import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Falta;
import br.gov.ma.detran.examespraticosmobile.sqlite.operations.AGC_FaltaOperations;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;

public class AGC_FaltaDB {

    private AGC_FaltaOperations agcFaltaOperations;

    public AGC_Falta retornarFalta(String itemLetra, String tipoFalta, String tipoExame, Context context) {
        agcFaltaOperations = new AGC_FaltaOperations(context);
        agcFaltaOperations.open();
        AGC_Falta agcFalta = agcFaltaOperations.retornarFalta(itemLetra, tipoFalta, tipoExame);
        agcFaltaOperations.close();
        return agcFalta;
    }

    public List<AGC_Falta> listarTodosPorTipo(String tipoExame, Context context){
        agcFaltaOperations = new AGC_FaltaOperations(context);
        agcFaltaOperations.open();
        List<AGC_Falta> lista = agcFaltaOperations.listarTodosPorTipo(tipoExame);
        agcFaltaOperations.close();
        return lista;
    }

    public List<AGC_Falta> listarTodos(Context context){
        agcFaltaOperations = new AGC_FaltaOperations(context);
        agcFaltaOperations.open();
        List<AGC_Falta> lista = agcFaltaOperations.listarTodos();
        agcFaltaOperations.close();
        return lista;
    }

    public void inserir(AGC_Falta agcFalta, Context context) throws NegocioException {
        agcFaltaOperations = new AGC_FaltaOperations(context);
        agcFaltaOperations.open();
        agcFaltaOperations.inserir(agcFalta);
        agcFaltaOperations.close();
    }

    public void limparDados(Context context) throws NegocioException {
        agcFaltaOperations = new AGC_FaltaOperations(context);
        agcFaltaOperations.open();
        agcFaltaOperations.limparDados();
        agcFaltaOperations.close();
    }

}

package br.gov.ma.detran.examespraticosmobile.service;

import android.content.Context;

import java.util.List;

import br.gov.ma.detran.examespraticosmobile.dao.db.AGC_FaltaDB;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Falta;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;

public class AGC_FaltaService {

    AGC_FaltaDB agcFaltaDB = new AGC_FaltaDB();

    public AGC_Falta retornarFalta(String itemLetra, String tipoFalta, String tipoExame, Context context) throws NegocioException {

        if (itemLetra == null || itemLetra.isEmpty()){
            throw new NegocioException("Letra deve ser informada.");
        }

        if (tipoFalta == null || tipoFalta.isEmpty()){
            throw new NegocioException("Tipo de Falta deve ser informada.");
        }

        if (tipoExame == null || tipoExame.isEmpty()){
            throw new NegocioException("Tipo de Exame deve ser informada.");
        }

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        return agcFaltaDB.retornarFalta(itemLetra, tipoFalta, tipoExame, context);

    }

    public List<AGC_Falta> listarTodosPorTipo(String tipoExame, Context context) throws NegocioException {

        if (tipoExame == null || tipoExame.isEmpty()){
            throw new NegocioException("Tipo de Exame deve ser informada.");
        }

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        return agcFaltaDB.listarTodosPorTipo(tipoExame, context);

    }

    public List<AGC_Falta> listarTodos(Context context) throws NegocioException {

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        return agcFaltaDB.listarTodos(context);

    }

    public void inserir(AGC_Falta agcFalta, Context context) throws NegocioException {

        if (agcFalta == null){
            throw new NegocioException("Falta deve ser informada.");
        }

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        agcFaltaDB.inserir(agcFalta, context);

    }

    public void limparDados(Context context) throws NegocioException {

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        agcFaltaDB.limparDados(context);

    }
}

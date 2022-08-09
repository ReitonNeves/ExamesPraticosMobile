package br.gov.ma.detran.examespraticosmobile.dao.db;

import android.content.Context;

import java.util.List;

import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Falta;
import br.gov.ma.detran.examespraticosmobile.modeloEspecializada.ListViewFaltas;
import br.gov.ma.detran.examespraticosmobile.sqlite.operations.AGC_Prova_FaltasOperations;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;

public class AGC_Prova_FaltasDB {

    AGC_Prova_FaltasOperations agcProvaFaltasOperations;

    public void inserir(AGC_Prova_Falta agcProvaFalta, Context context) throws NegocioException {
        agcProvaFaltasOperations = new AGC_Prova_FaltasOperations(context);
        agcProvaFaltasOperations.open();
        agcProvaFaltasOperations.inserir(agcProvaFalta);
        agcProvaFaltasOperations.close();
    }

    public void limparTabela(Context context) throws NegocioException {
        agcProvaFaltasOperations = new AGC_Prova_FaltasOperations(context);
        agcProvaFaltasOperations.open();
        agcProvaFaltasOperations.limparTabela();
        agcProvaFaltasOperations.close();
    }

    public List<ListViewFaltas> retornarQuestoesParaCandidatoETipoDeExame(String cpfCandidato, String tipoExame, String tipoFalta, Context context){
        agcProvaFaltasOperations = new AGC_Prova_FaltasOperations(context);
        agcProvaFaltasOperations.open();
        List<ListViewFaltas> lista = agcProvaFaltasOperations.retornarQuestoesParaCandidatoETipoDeExame(cpfCandidato, tipoExame, tipoFalta);
        agcProvaFaltasOperations.close();
        return lista;
    }

    public List<AGC_Prova_Falta> retornarFaltasParaCandidatoETipoDeExame(String cpfCandidato, String dataExame, String localExame, String tipoExame, String tipoFalta, Context context){
        agcProvaFaltasOperations = new AGC_Prova_FaltasOperations(context);
        agcProvaFaltasOperations.open();
        List<AGC_Prova_Falta> lista = agcProvaFaltasOperations.retornarFaltasParaCandidatoETipoDeExame(cpfCandidato, dataExame, localExame, tipoExame, tipoFalta);
        agcProvaFaltasOperations.close();
        return lista;
    }

    public List<AGC_Prova_Falta> retornarFaltasParaCandidatoETipoDeExame(String cpfCandidato, String dataExame, String localExame, String tipoExame, Context context){
        agcProvaFaltasOperations = new AGC_Prova_FaltasOperations(context);
        agcProvaFaltasOperations.open();
        List<AGC_Prova_Falta> lista = agcProvaFaltasOperations.retornarFaltasParaCandidatoETipoDeExame(cpfCandidato, dataExame, localExame, tipoExame);
        agcProvaFaltasOperations.close();
        return lista;
    }

    public AGC_Prova_Falta retornarQuestaoDoCandidato(String cpfCandidato, String dataExame, String localExame,
                                                      String tipoExame, String tipoFalta, String itemLetra, Context context){
        agcProvaFaltasOperations = new AGC_Prova_FaltasOperations(context);
        agcProvaFaltasOperations.open();
        AGC_Prova_Falta agcProvaFalta = agcProvaFaltasOperations.retornarQuestaoDoCandidato(cpfCandidato, dataExame, localExame,
                tipoExame, tipoFalta, itemLetra);
        agcProvaFaltasOperations.close();
        return agcProvaFalta;

    }

    public void atualizarQuantidadeDeFaltas(AGC_Prova_Falta agcProvaFalta, Context context) throws NegocioException {
        agcProvaFaltasOperations = new AGC_Prova_FaltasOperations(context);
        agcProvaFaltasOperations.open();
        agcProvaFaltasOperations.atualizarQuantidadeDeFaltas(agcProvaFalta);
        agcProvaFaltasOperations.close();
    }

    public void removerProvaFalta(AGC_Prova_Falta agcProvaFalta, Context context) throws NegocioException {
        agcProvaFaltasOperations = new AGC_Prova_FaltasOperations(context);
        agcProvaFaltasOperations.open();
        agcProvaFaltasOperations.removerProvaFalta(agcProvaFalta);
        agcProvaFaltasOperations.close();
    }
}

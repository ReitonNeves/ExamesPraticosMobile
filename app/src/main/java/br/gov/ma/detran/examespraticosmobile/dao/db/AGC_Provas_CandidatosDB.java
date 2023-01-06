package br.gov.ma.detran.examespraticosmobile.dao.db;

import android.content.Context;

import java.util.List;

import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Candidato;
import br.gov.ma.detran.examespraticosmobile.modeloEspecializada.ListViewFechar;
import br.gov.ma.detran.examespraticosmobile.sqlite.operations.AGC_Provas_CandidatosOperations;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;

public class AGC_Provas_CandidatosDB {

    private AGC_Provas_CandidatosOperations agcProvasCandidatosOperations;

    public void inserir(AGC_Prova_Candidato agcProvaCandidato, Context context) throws NegocioException {
        agcProvasCandidatosOperations = new AGC_Provas_CandidatosOperations(context);
        agcProvasCandidatosOperations.open();
        agcProvasCandidatosOperations.inserir(agcProvaCandidato);
        agcProvasCandidatosOperations.close();
    }

    public void limparTabela(Context context) throws NegocioException {
        agcProvasCandidatosOperations = new AGC_Provas_CandidatosOperations(context);
        agcProvasCandidatosOperations.open();
        agcProvasCandidatosOperations.limparTabela();
        agcProvasCandidatosOperations.close();
    }

    public Boolean verificarSeExistemAgendasPendentesDeEnvioNoTablet(Context context){
        agcProvasCandidatosOperations = new AGC_Provas_CandidatosOperations(context);
        agcProvasCandidatosOperations.open();
        Boolean retorno = agcProvasCandidatosOperations.verificarSeExistemAgendasPendentesDeEnvioNoTablet();
        agcProvasCandidatosOperations.close();
        return retorno;
    }

    public List<AGC_Prova_Candidato> retornarTodosAgendadosParaExaminador(String data, String tipoExame, String examinador, Context context){
        agcProvasCandidatosOperations = new AGC_Provas_CandidatosOperations(context);
        agcProvasCandidatosOperations.open();
        List<AGC_Prova_Candidato> retorno = agcProvasCandidatosOperations.retornarTodosAgendadosParaExaminador(data, tipoExame, examinador);
        agcProvasCandidatosOperations.close();
        return retorno;
    }

    public List<AGC_Prova_Candidato> retornarTodosAgendadosParaExaminador(String data, String tipoExame, String turma, String examinador, Context context){
        agcProvasCandidatosOperations = new AGC_Provas_CandidatosOperations(context);
        agcProvasCandidatosOperations.open();
        List<AGC_Prova_Candidato> retorno = agcProvasCandidatosOperations.retornarTodosAgendadosParaExaminador(data, tipoExame, turma, examinador);
        agcProvasCandidatosOperations.close();
        return retorno;
    }

    public List<AGC_Prova_Candidato> listarTodos(Context context){
        agcProvasCandidatosOperations = new AGC_Provas_CandidatosOperations(context);
        agcProvasCandidatosOperations.open();
        List<AGC_Prova_Candidato> retorno = agcProvasCandidatosOperations.listarTodos();
        agcProvasCandidatosOperations.close();
        return retorno;
    }

    public List<AGC_Prova_Candidato> listarTodos(String tipoExame, String cpfExaminador, Context context) {
        agcProvasCandidatosOperations = new AGC_Provas_CandidatosOperations(context);
        agcProvasCandidatosOperations.open();
        List<AGC_Prova_Candidato> retorno = agcProvasCandidatosOperations.listarTodos(tipoExame, cpfExaminador);
        agcProvasCandidatosOperations.close();
        return retorno;
    }

    public AGC_Prova_Candidato retornarPorID(String id, Context context){
        agcProvasCandidatosOperations = new AGC_Provas_CandidatosOperations(context);
        agcProvasCandidatosOperations.open();
        AGC_Prova_Candidato agcProvaCandidato = agcProvasCandidatosOperations.retornarPorID(id);
        agcProvasCandidatosOperations.close();
        return agcProvaCandidato;
    }

    public void iniciarProvaCandidato(AGC_Prova_Candidato agcProvaCandidato, Context context) throws NegocioException {
        agcProvasCandidatosOperations = new AGC_Provas_CandidatosOperations(context);
        agcProvasCandidatosOperations.open();
        agcProvasCandidatosOperations.iniciarProvaCandidato(agcProvaCandidato);
        agcProvasCandidatosOperations.close();
    }

    public AGC_Prova_Candidato retornarProvaIniciada(Context context){
        agcProvasCandidatosOperations = new AGC_Provas_CandidatosOperations(context);
        agcProvasCandidatosOperations.open();
        AGC_Prova_Candidato retorno = agcProvasCandidatosOperations.retornarProvaIniciada();
        agcProvasCandidatosOperations.close();
        return retorno;
    }

    public void atualizar(AGC_Prova_Candidato agcProvaCandidato, Context context) throws NegocioException {
        agcProvasCandidatosOperations = new AGC_Provas_CandidatosOperations(context);
        agcProvasCandidatosOperations.open();
        agcProvasCandidatosOperations.atualizar(agcProvaCandidato);
        agcProvasCandidatosOperations.close();
    }

    public List<ListViewFechar> retornarTodosAgendadosParaFechar(Context context){
        agcProvasCandidatosOperations = new AGC_Provas_CandidatosOperations(context);
        agcProvasCandidatosOperations.open();
        List<ListViewFechar> retorno = agcProvasCandidatosOperations.retornarTodosAgendadosParaFechar();
        agcProvasCandidatosOperations.close();
        return retorno;
    }

    public void inserirDigital(byte[] imagemDigital, String idCandidato, Context context) throws NegocioException {
        agcProvasCandidatosOperations = new AGC_Provas_CandidatosOperations(context);
        agcProvasCandidatosOperations.open();
        agcProvasCandidatosOperations.atualizarDigital(imagemDigital, idCandidato);
        agcProvasCandidatosOperations.close();
    }

    public List<AGC_Prova_Candidato> retornarProvasDisponiveis(Context context){
        agcProvasCandidatosOperations = new AGC_Provas_CandidatosOperations(context);
        agcProvasCandidatosOperations.open();
        List<AGC_Prova_Candidato> retorno = agcProvasCandidatosOperations.retornarProvasDisponiveis();
        agcProvasCandidatosOperations.close();
        return retorno;
    }

}

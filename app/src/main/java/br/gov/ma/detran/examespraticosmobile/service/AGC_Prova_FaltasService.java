package br.gov.ma.detran.examespraticosmobile.service;

import android.content.Context;

import java.text.ParseException;
import java.util.List;

import br.gov.ma.detran.examespraticosmobile.dao.db.AGC_Prova_FaltasDB;
import br.gov.ma.detran.examespraticosmobile.dao.rest.AGC_Prova_FaltaRest;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Candidato;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Falta;
import br.gov.ma.detran.examespraticosmobile.modeloEspecializada.ListViewFaltas;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;

public class AGC_Prova_FaltasService {

    AGC_Prova_FaltasDB agcProvaFaltasDB = new AGC_Prova_FaltasDB();

    public void inserir(AGC_Prova_Falta agcProvaFalta, Context context) throws NegocioException {

        if (agcProvaFalta == null){
            throw new NegocioException("Falta deve ser informada.");
        }

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        agcProvaFaltasDB.inserir(agcProvaFalta, context);

    }

    public void limparTabela(Context context) throws NegocioException {

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        agcProvaFaltasDB.limparTabela(context);

    }

    public List<ListViewFaltas> retornarQuestoesParaCandidatoETipoDeExame(String cpfCandidato, String tipoExame, String tipoFalta, Context context) throws NegocioException {

        if (cpfCandidato == null || cpfCandidato.isEmpty() && cpfCandidato.length() != 11){
            throw new NegocioException("Cpf do Candidato deve ser informado ou não está no formato correto.");
        }

        if (tipoExame == null || tipoExame.isEmpty()){
            throw new NegocioException("Tipo de Exame deve ser informado.");
        }

        if (tipoFalta == null || tipoFalta.isEmpty()){
            throw new NegocioException("Tipo de Falta deve ser informado.");
        }

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        return agcProvaFaltasDB.retornarQuestoesParaCandidatoETipoDeExame(cpfCandidato, tipoExame, tipoFalta, context);
    }

    public List<AGC_Prova_Falta> retornarFaltasParaCandidatoETipoDeExame(String cpfCandidato, String dataExame, String localExame, String tipoExame, String tipoFalta, Context context) throws NegocioException {

        if (cpfCandidato == null || cpfCandidato.isEmpty() && cpfCandidato.length() != 11){
            throw new NegocioException("Cpf do Candidato deve ser informado ou não está no formato correto.");
        }

        if (dataExame == null || dataExame.isEmpty()){
            throw new NegocioException("Tipo de Exame deve ser informado.");
        }

        if (localExame == null || localExame.isEmpty()){
            throw new NegocioException("Tipo de Exame deve ser informado.");
        }

        if (tipoExame == null || tipoExame.isEmpty()){
            throw new NegocioException("Tipo de Exame deve ser informado.");
        }

        if (tipoFalta == null || tipoFalta.isEmpty()){
            throw new NegocioException("Tipo de Falta deve ser informado.");
        }

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        return agcProvaFaltasDB.retornarFaltasParaCandidatoETipoDeExame(cpfCandidato, dataExame, localExame, tipoExame, tipoFalta, context);

    }

    public List<AGC_Prova_Falta> retornarFaltasParaCandidatoETipoDeExame(String cpfCandidato, String dataExame, String localExame, String tipoExame, Context context) throws NegocioException {

        if (cpfCandidato == null || cpfCandidato.isEmpty() && cpfCandidato.length() != 11){
            throw new NegocioException("Cpf do Candidato deve ser informado ou não está no formato correto.");
        }

        if (dataExame == null || dataExame.isEmpty()){
            throw new NegocioException("Tipo de Exame deve ser informado.");
        }

        if (localExame == null || localExame.isEmpty()){
            throw new NegocioException("Tipo de Exame deve ser informado.");
        }

        if (tipoExame == null || tipoExame.isEmpty()){
            throw new NegocioException("Tipo de Exame deve ser informado.");
        }

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        return agcProvaFaltasDB.retornarFaltasParaCandidatoETipoDeExame(cpfCandidato, dataExame, localExame, tipoExame, context);

    }

    public AGC_Prova_Falta retornarQuestaoDoCandidato(String cpfCandidato, String dataExame, String localExame,
                                                      String tipoExame, String tipoFalta, String itemLetra, Context context) throws NegocioException {

        if (cpfCandidato == null || cpfCandidato.isEmpty() && cpfCandidato.length() != 11){
            throw new NegocioException("Cpf do Candidato deve ser informado ou não está no formato correto.");
        }

        if ((dataExame == null || dataExame.isEmpty()) && dataExame.length() != 10){
            throw new NegocioException("Data do Exame deve ser informado.");
        }

        if (localExame == null || localExame.isEmpty()){
            throw new NegocioException("Local de Exame deve ser informado.");
        }

        if (tipoExame == null || tipoExame.isEmpty()){
            throw new NegocioException("Tipo de Exame deve ser informado.");
        }

        if (tipoFalta == null || tipoFalta.isEmpty()){
            throw new NegocioException("Tipo de Falta deve ser informado.");
        }

        if (itemLetra == null || itemLetra.isEmpty()){
            throw new NegocioException("Item Letra deve ser informado.");
        }

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        return agcProvaFaltasDB.retornarQuestaoDoCandidato(cpfCandidato, dataExame, localExame, tipoExame, tipoFalta, itemLetra, context);

    }

    public void atualizarFalta(AGC_Prova_Falta agcProvaFalta, Context context) throws NegocioException {

        String quantidadesDeFaltas = String.valueOf(agcProvaFalta.getQuantidadeDeFaltas());
        String cpfInclusao = agcProvaFalta.getCpfInclusao();
        String dataHoraInclusao = agcProvaFalta.getDataHoraInclusao();
        String tipoFalta = agcProvaFalta.getTipoFalta();
        String itemLetra = agcProvaFalta.getItemLetra();

        if (quantidadesDeFaltas == null || quantidadesDeFaltas.isEmpty()){
            throw new NegocioException("Quantidade de Faltas deve ser informada.");
        }

        try {
            Integer.parseInt(quantidadesDeFaltas);
        } catch (Exception ex){
            ex.printStackTrace();
            throw new NegocioException("Quantidade de Faltas não está no formato correto.");
        }

        if (cpfInclusao == null || cpfInclusao.isEmpty() && cpfInclusao.length() != 11){
            throw new NegocioException("Cpf de Inclusão deve ser informado ou não está no formato correto.");
        }

        if (dataHoraInclusao == null || dataHoraInclusao.isEmpty()){
            throw new NegocioException("Data/Hora de Inclusão deve ser informado ou não está no formato correto.");
        }

        if (agcProvaFalta.getCpfCandidato() == null || agcProvaFalta.getCpfCandidato().isEmpty() && agcProvaFalta.getCpfCandidato().length() != 11){
            throw new NegocioException("Cpf do Candidato deve ser informado ou não está no formato correto.");
        }

        if ((agcProvaFalta.getDataExame() == null || agcProvaFalta.getDataExame().isEmpty()) && agcProvaFalta.getDataExame().length() != 10){
            throw new NegocioException("Data do Exame deve ser informado.");
        }

        if (agcProvaFalta.getLocalExame() == null || agcProvaFalta.getLocalExame().isEmpty()){
            throw new NegocioException("Local de Exame deve ser informado.");
        }

        if (agcProvaFalta.getTipoExame() == null || agcProvaFalta.getTipoExame().isEmpty()){
            throw new NegocioException("Tipo de Exame deve ser informado.");
        }

        if (tipoFalta == null || tipoFalta.isEmpty()){
            throw new NegocioException("Tipo de Falta deve ser informado.");
        }

        if (itemLetra == null || itemLetra.isEmpty()){
            throw new NegocioException("Item Letra deve ser informado.");
        }

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        agcProvaFaltasDB.atualizarFalta(agcProvaFalta, context);

    }

    public void removerProvaFalta(AGC_Prova_Falta agcProvaFalta, Context context) throws NegocioException {

        String tipoFalta = agcProvaFalta.getTipoFalta();
        String itemLetra = agcProvaFalta.getItemLetra();

        if (agcProvaFalta.getCpfCandidato() == null || agcProvaFalta.getCpfCandidato().isEmpty() && agcProvaFalta.getCpfCandidato().length() != 11){
            throw new NegocioException("Cpf do Candidato deve ser informado ou não está no formato correto.");
        }

        if ((agcProvaFalta.getDataExame() == null || agcProvaFalta.getDataExame().isEmpty()) && agcProvaFalta.getDataExame().length() != 10){
            throw new NegocioException("Data do Exame deve ser informado.");
        }

        if (agcProvaFalta.getLocalExame() == null || agcProvaFalta.getLocalExame().isEmpty()){
            throw new NegocioException("Local de Exame deve ser informado.");
        }

        if (agcProvaFalta.getTipoExame() == null || agcProvaFalta.getTipoExame().isEmpty()){
            throw new NegocioException("Tipo de Exame deve ser informado.");
        }

        if (tipoFalta == null || tipoFalta.isEmpty()){
            throw new NegocioException("Tipo de Falta deve ser informado.");
        }

        if (itemLetra == null || itemLetra.isEmpty()){
            throw new NegocioException("Item Letra deve ser informado.");
        }

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        agcProvaFaltasDB.removerProvaFalta(agcProvaFalta, context);

    }

    public void enviarFaltasParaRest(List<AGC_Prova_Falta> agcProvaFaltaList) throws NegocioException {

        AGC_Prova_FaltaRest agcProvaFaltaRest = new AGC_Prova_FaltaRest();
        try {
            if(agcProvaFaltaList != null){
                for (AGC_Prova_Falta a:agcProvaFaltaList) {
                    agcProvaFaltaRest.inserir(a);
                }
            }
        } catch (Exception e){

            e.printStackTrace();

            if(agcProvaFaltaList != null){
                for (AGC_Prova_Falta a : agcProvaFaltaList) {
                    try {
                        agcProvaFaltaRest.remover(a.getCpfCandidato(), a.getDataExame(), a.getLocalExame(), a.getTipoExame());
                    } catch (Exception ex1){
                        ex1.printStackTrace();
                    }
                }
            }
            throw new NegocioException(e.getMessage());
        }
    }
}

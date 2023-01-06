package br.gov.ma.detran.examespraticosmobile.service;

import android.content.Context;

import org.json.JSONException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import br.gov.ma.detran.examespraticosmobile.dao.db.AGC_Provas_CandidatosDB;
import br.gov.ma.detran.examespraticosmobile.dao.rest.AGC_Provas_CandidatosRest;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Falta;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Candidato;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Falta;
import br.gov.ma.detran.examespraticosmobile.modeloEspecializada.ListViewFechar;
import br.gov.ma.detran.examespraticosmobile.util.DataHoraUtil;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;
import br.gov.ma.detran.examespraticosmobile.views.SelecionarCandidatoActivity;

public class AGC_Provas_CandidatosService {

    private AGC_Provas_CandidatosRest agcProvasCandidatosRest = new AGC_Provas_CandidatosRest();
    private AGC_Provas_CandidatosDB agcProvasCandidatosDB = new AGC_Provas_CandidatosDB();

    public List<AGC_Prova_Candidato> retornarAgendaDosCandidatosDistribuidosDoRestService(String dataExameInicial, String dataExameFinal, String cpfExaminador1, String cpfExaminador2, String tipoExame, String localExame) throws JSONException, NegocioException {

        if (dataExameInicial == null || dataExameInicial.isEmpty() || dataExameInicial.length() != 10) {
            throw new NegocioException("Data Exame deve ser informada ou não está no formato correto.");
        }

        if (dataExameFinal == null || dataExameFinal.isEmpty() || dataExameFinal.length() != 10) {
            throw new NegocioException("Data Exame deve ser informada ou não está no formato correto.");
        }

        if (cpfExaminador1 == null || cpfExaminador1.isEmpty() ||
                cpfExaminador1.replace(".", "").replace("-", "").length() != 11) {
            throw new NegocioException("Examinador 1 deve ser informado ou não está no formato correto.");
        }

        if (cpfExaminador2 == null || cpfExaminador2.isEmpty() ||
                cpfExaminador2.replace(".", "").replace("-", "").length() != 11) {
            throw new NegocioException("Examinador 2 deve ser informado ou não está no formato correto.");
        }

        if (tipoExame == null || tipoExame.isEmpty()) {
            throw new NegocioException("Tipo de Exame deve ser informado ou não está no formato correto.");
        }

        if (localExame == null || localExame.isEmpty()) {
            throw new NegocioException("Local de Exame deve ser informado ou não está no formato correto.");
        }
        System.out.println("Resposta: " + agcProvasCandidatosRest.retornarAgendaDosCandidatosDistribuidos(dataExameInicial, dataExameFinal,
                cpfExaminador1.replace(".", "").replace("-", ""),
                cpfExaminador2.replace(".", "").replace("-", ""), tipoExame, localExame));
        return agcProvasCandidatosRest.retornarAgendaDosCandidatosDistribuidos(dataExameInicial, dataExameFinal,
                cpfExaminador1.replace(".", "").replace("-", ""),
                cpfExaminador2.replace(".", "").replace("-", ""), tipoExame, localExame);

    }

    public void inserirNoDB(AGC_Prova_Candidato agcProvaCandidato, Context context) throws NegocioException {
        if (agcProvaCandidato == null){
            throw new NegocioException("Prova do Candidato deve ser informado.");
        }

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }
        agcProvasCandidatosDB.inserir(agcProvaCandidato, context);
    }

    public void limparTabela(Context context) throws NegocioException {

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        agcProvasCandidatosDB.limparTabela(context);
    }

    public Boolean verificarSeExistemAgendasPendentesDeEnvioNoTablet(Context context) throws NegocioException {
        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        return agcProvasCandidatosDB.verificarSeExistemAgendasPendentesDeEnvioNoTablet(context);
    }

    public void alterarSituacaoDoCandidatoParaSincronizadoNoRestService(String listaIdCandidatos) throws NegocioException {
        if (listaIdCandidatos == null || listaIdCandidatos.isEmpty()){
            throw new NegocioException("Lista de Candidatos deve ser informada.");
        }

        agcProvasCandidatosRest.alterarSituacaoDoCandidatoParaSincronizadoNoRestService(listaIdCandidatos);
    }

    public List<AGC_Prova_Candidato> retornarTodosAgendadosParaExaminador(String data, String tipoExame, String turma, String examinador, Context context) throws NegocioException {

        if (data == null || data.isEmpty() || data.length() != 10){
            throw new NegocioException("Data deve ser informada ou não está no formato correto.");
        }

        if (examinador == null || examinador.isEmpty() || examinador.length() != 11){
            throw new NegocioException("Examinador deve ser informado ou não está no formato correto.");
        }

        if (tipoExame == null || tipoExame.isEmpty()){
            throw new NegocioException("Tipo de Exame deve ser informado");
        }

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        if (turma.isEmpty()){
            return ordenarPorTurmaERegistroDePresenca(agcProvasCandidatosDB.retornarTodosAgendadosParaExaminador(data, tipoExame, examinador, context));
        } else {
            return ordenarPorTurmaERegistroDePresenca(agcProvasCandidatosDB.retornarTodosAgendadosParaExaminador(data, tipoExame, turma, examinador, context));
        }

    }

    public List<AGC_Prova_Candidato> listarTodos(Context context) throws NegocioException {

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        return ordenarPorTurmaERegistroDePresenca(agcProvasCandidatosDB.listarTodos(context));

    }

    public List<AGC_Prova_Candidato> listarTodos(String tipoExame, String cpfExaminador, Context context) throws NegocioException {
        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        return ordenarPorTurmaERegistroDePresenca(agcProvasCandidatosDB.listarTodos(tipoExame, cpfExaminador, context));
    }

    public AGC_Prova_Candidato retornarPorID(String id, Context context) throws NegocioException {

        if (id == null || id.isEmpty()){
            throw new NegocioException("Candidato deve ser informado ou não está no formato correto.");
        }

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }

        return agcProvasCandidatosDB.retornarPorID(id, context);

    }

    public void iniciarProvaCandidato(AGC_Prova_Candidato agcProvaCandidato, Context context) throws NegocioException {
        if (agcProvaCandidato == null){
            throw new NegocioException("Candidato deve ser informado ou não está no formato correto.");
        }

        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }
        agcProvasCandidatosDB.iniciarProvaCandidato(agcProvaCandidato, context);
    }

    public AGC_Prova_Candidato retornarProvaIniciada(Context context) throws NegocioException {
        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }
        return agcProvasCandidatosDB.retornarProvaIniciada(context);
    }

    public List <AGC_Prova_Candidato> retornarProvasDisponiveis(Context context) throws NegocioException {
        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }
        return agcProvasCandidatosDB.retornarProvasDisponiveis(context);
    }

    public void alterarSituacaoDoCandidatoParaPendente(AGC_Prova_Candidato agcProvaCandidato, Context context) throws NegocioException {
        if (agcProvaCandidato.getSituacao().isEmpty() || agcProvaCandidato.getSituacao().equals("I") == false){
            throw new NegocioException("Situação inválida.");
        }

        if (!agcProvaCandidato.getResultado().equals("3") && !agcProvaCandidato.getResultado().equals("0")){
            if (agcProvaCandidato.getPlaca().isEmpty() || agcProvaCandidato.getPlaca().length() != 8){
                //throw new NegocioException("Placa inválida.");
            }
        } else {
            if (agcProvaCandidato.getObservacoes().isEmpty()){
                throw new NegocioException("O campo observação deve ser preenchido.");
            }
        }

        if (agcProvaCandidato.getHoraFimExame().isEmpty()){
            throw new NegocioException("Hora Fim do exame deve ser informada");
        }

        if (agcProvaCandidato.getResultado().isEmpty()){
            throw new NegocioException("Resultado deve ser informada.");
        }

        Integer totalDePontos = retornarQuantidadeDePontosDoCandidato(agcProvaCandidato, context);

        if (agcProvaCandidato.getResultado().equals("1") && totalDePontos >= 4){
            throw new NegocioException("Candidato com " + totalDePontos + " não pode ser aprovado. Revise as faltas.");
        }

        if (agcProvaCandidato.getResultado().equals("2") && totalDePontos < 4){
            throw new NegocioException("Candidato com " + totalDePontos + " não pode ser reprovado. Revise as faltas.");
        }

        if (agcProvaCandidato.getResultado().equals("3")){
            AGC_Prova_FaltasService agcProvaFaltasService = new AGC_Prova_FaltasService();
            List<AGC_Prova_Falta> agcProvaFaltaList = agcProvaFaltasService.retornarFaltasParaCandidatoETipoDeExame(agcProvaCandidato.getCpfCandidato(),
                    agcProvaCandidato.getDataExame(), agcProvaCandidato.getLocalExame(), agcProvaCandidato.getTipoExame(), context);
            if (agcProvaFaltaList != null && agcProvaFaltaList.size() > 0){
                throw new NegocioException("Aluno não pode ser colocado como ausente, existem faltas registradas.");
            }
        }

        agcProvaCandidato.setSituacao("P");

        atualizar(agcProvaCandidato, context);

    }

    public void alterarSituacaoDoCandidatoParaFechado(AGC_Prova_Candidato agcProvaCandidato, Context context) throws NegocioException {

        if (agcProvaCandidato.getSituacao().isEmpty() || agcProvaCandidato.getSituacao().equals("P") == false){
            throw new NegocioException("Situação inválida.");
        }

        agcProvaCandidato.setSituacao("F");

        atualizar(agcProvaCandidato, context);

    }

    public void alterarSituacaoDoCandidatoParaDisponivel(AGC_Prova_Candidato agcProvaCandidato, Context context) throws NegocioException {
        if (agcProvaCandidato.getSituacao().isEmpty() || agcProvaCandidato.getSituacao().equals("D")){
            throw new NegocioException("Situação inválida.");
        }

        agcProvaCandidato.setSituacao("D");

        atualizar(agcProvaCandidato, context);
    }

    private void atualizar(AGC_Prova_Candidato agcProvaCandidato, Context context) throws NegocioException {
        if (agcProvaCandidato == null || agcProvaCandidato.getId() == null){
            throw new NegocioException("ID Prova candidato deve ser informado ou está inválido.");
        }
        agcProvasCandidatosDB.atualizar(agcProvaCandidato, context);
    }

    public String retornarResultadoDoCandidato(AGC_Prova_Candidato agcProvaCandidato, Context context) throws NegocioException {

        Integer totalDePontos = retornarQuantidadeDePontosDoCandidato(agcProvaCandidato, context);

        String resultado = "";
        if (totalDePontos >= 4){
            resultado = "Reprovado";
        } else if (totalDePontos < 4){
            resultado = "Aprovado";
        }

        return resultado;
    }

    public final Integer retornarQuantidadeDePontosDoCandidato(AGC_Prova_Candidato agcProvaCandidato, Context context) throws NegocioException {
        AGC_Prova_FaltasService agcProvaFaltasService = new AGC_Prova_FaltasService();
        AGC_FaltaService agcFaltaService = new AGC_FaltaService();

        final List<AGC_Prova_Falta> agcProvaFaltaList = agcProvaFaltasService.retornarFaltasParaCandidatoETipoDeExame(agcProvaCandidato.getCpfCandidato(),
                agcProvaCandidato.getDataExame(),
                agcProvaCandidato.getLocalExame(),
                agcProvaCandidato.getTipoExame(),
                context);

        Integer totalDePontos = 0;

        if (agcProvaFaltaList != null){
            for (AGC_Prova_Falta agcProvaFalta: agcProvaFaltaList) {
                AGC_Falta agcFalta = agcFaltaService.retornarFalta(agcProvaFalta.getItemLetra(), agcProvaFalta.getTipoFalta(), agcProvaFalta.getTipoExame(), context);
                if (agcFalta != null) {
                    totalDePontos = totalDePontos + (agcFalta.getPontos() * agcProvaFalta.getQuantidadeDeFaltas());
                }
            }
        }

        return totalDePontos;
    }

    public List<ListViewFechar> retornarTodosAgendadosParaFechar(Context context) throws NegocioException {
        if (context == null){
            throw new NegocioException("Context deve ser informado.");
        }
        return agcProvasCandidatosDB.retornarTodosAgendadosParaFechar(context);
    }

    public void inserirDigital(byte[] imagemDigital, String idCandidato, Context context) throws NegocioException {
        agcProvasCandidatosDB.inserirDigital(imagemDigital, idCandidato, context);
    }

    private List <AGC_Prova_Candidato> ordenarPorTurmaERegistroDePresenca(List <AGC_Prova_Candidato> exames) throws NegocioException {
        if(Objects.isNull(exames) || exames.isEmpty()){
            throw  new NegocioException(" Não foram encontrados agendamentos com os dados informados.");
        }
        Collections.sort(exames, new Comparator<AGC_Prova_Candidato>() {
            @Override
            public int compare(AGC_Prova_Candidato  exame1, AGC_Prova_Candidato  exame2) {
                int retorno = DataHoraUtil.parseLocalTime(exame1.getTurma()).compareTo(DataHoraUtil.parseLocalTime(exame2.getTurma()));
                System.out.println(exame1.getHorarioRegistroDePresenca());
                /*if(exame1.getHorarioRegistroDePresenca() != null && exame2.getHorarioRegistroDePresenca() != null) {
                    if (retorno == 0) {
                        retorno = exame1.getHorarioRegistroDePresenca().compareTo(exame2.getHorarioRegistroDePresenca());
                    }
                } else if(exame1.getHorarioRegistroDePresenca() == null){
                    retorno = 1;
                } else{
                    retorno = -1;
                }*/
                return retorno;
            }
        });
        return exames;
    }
}

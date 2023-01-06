package br.gov.ma.detran.examespraticosmobile.sincronizacao;

import android.content.Context;

import org.json.JSONException;

import java.util.List;
import java.util.Objects;

import br.gov.ma.detran.examespraticosmobile.dao.rest.AGC_Provas_CandidatosRest;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Candidato;
import br.gov.ma.detran.examespraticosmobile.service.AGC_Prova_FaltasService;
import br.gov.ma.detran.examespraticosmobile.service.AGC_Provas_CandidatosService;
import br.gov.ma.detran.examespraticosmobile.service.AGC_UsuariosService;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;

public class AGC_Provas_CandidatosSinc {

    private AGC_UsuariosSinc agcUsuariosSinc = new AGC_UsuariosSinc();
    private AGC_Provas_CandidatosRest agcProvasCandidatosRest = new AGC_Provas_CandidatosRest();
    private AGC_Provas_CandidatosService agcProvasCandidatosService = new AGC_Provas_CandidatosService();
    private AGC_UsuariosService agcUsuariosService = new AGC_UsuariosService();
    private AGC_Prova_FaltasService agcProvaFaltasService = new AGC_Prova_FaltasService();

    public void sincronizarAgendas(String dataExameInicial, String dataExameFinal, String cpfExaminador1, String cpfExaminador2, String tipoExame, String localExame, Context context) throws NegocioException, JSONException {

        if (agcProvasCandidatosService.verificarSeExistemAgendasPendentesDeEnvioNoTablet(context) == true) {
            throw new NegocioException("Sincronização cancelada. Existem registros pendentes de resultados no Tablet. Use a função -Fechar Agendas- para enviar os resultados dos exames realizados.");
        }

        List<AGC_Prova_Candidato> agcProvaCandidatoList = agcProvasCandidatosService.retornarAgendaDosCandidatosDistribuidosDoRestService(
                dataExameInicial, dataExameFinal, cpfExaminador1, cpfExaminador2, tipoExame, localExame);
        System.out.println("Resposta: " + agcProvaCandidatoList.isEmpty());
        if (agcProvaCandidatoList == null || agcProvaCandidatoList.size() == 0) {
            throw new NegocioException("Sincronização cancelada. Nenhum registro encontrado.");
        }

        //agcProvasCandidatosService.limparTabela(context);
        agcProvaFaltasService.limparTabela(context);

        String listaIdCandidatos = "";

        for (AGC_Prova_Candidato agcProvaCandidato : agcProvaCandidatoList) {
            try {
                agcProvaCandidato.setSituacao("S");
                agcProvasCandidatosService.inserirNoDB(agcProvaCandidato, context);
                listaIdCandidatos = listaIdCandidatos + agcProvaCandidato.getId() + "-";
            } catch (Exception ex) {
                ex.printStackTrace();
                agcProvasCandidatosService.limparTabela(context);
                throw new NegocioException("Sincronização cancelada. Erro ao inserir: " + agcProvaCandidato.toString());
            }
        }

        try {
            agcUsuariosSinc.sincronizarTabelaDeUsuarios(context);
        } catch (Exception ex){
            ex.printStackTrace();
            agcProvasCandidatosService.limparTabela(context);
            agcUsuariosService.limparDados(context);
            throw new NegocioException("Sincronização cancelada. Nâo foi possível sincronizar Tabela de Usuários.");
        }

        try {
            agcProvasCandidatosService.alterarSituacaoDoCandidatoParaSincronizadoNoRestService(listaIdCandidatos.substring(0, listaIdCandidatos.length() - 1));
        } catch (Exception ex){
            ex.printStackTrace();
            agcProvasCandidatosService.limparTabela(context);
            throw new NegocioException("Sincronização cancelada. Nâo foi possível sincronizar Tabela de Agendas.");
        }

    }

    public void sincronizarAgendas(String dataExameInicial, String dataExameFinal, String localExame, String tipoExame, Context context) throws NegocioException {
        if (agcProvasCandidatosService.verificarSeExistemAgendasPendentesDeEnvioNoTablet(context) == true) {
            throw new NegocioException("Sincronização cancelada. Existem registros pendentes de resultados no Tablet. Use a função -Fechar Agendas- para enviar os resultados dos exames realizados.");
        }
        try {
            List<AGC_Prova_Candidato>  agcProvaCandidatoList = agcProvasCandidatosRest.retornarAgendaDosCandidatosPor(dataExameInicial, localExame, tipoExame);
            if(Objects.isNull(agcProvaCandidatoList)){
                throw new NegocioException(" Nenhum agendamento encontrado.");
            }
            for (AGC_Prova_Candidato agcProvaCandidato : agcProvaCandidatoList) {
                try {
                    agcProvaCandidato.setSituacao("S");
                    agcProvasCandidatosService.inserirNoDB(agcProvaCandidato, context);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    agcProvasCandidatosService.limparTabela(context);
                    throw new NegocioException("Sincronização cancelada. Erro ao inserir: " + agcProvaCandidato.toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch(Exception e){
            throw new NegocioException(e.getMessage());
        }

    }
}

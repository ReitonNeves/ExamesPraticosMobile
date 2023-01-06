package br.gov.ma.detran.examespraticosmobile.modeloEspecializada;

import java.util.List;
import java.util.Map;

import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Candidato;

public class AgendamentoLocalView {
    private String local;
    private Map<String, List<AGC_Prova_Candidato>> agendamento;

    public AgendamentoLocalView(String local, Map<String, List<AGC_Prova_Candidato>> agendamento) {
        this.local = local;
        this.agendamento = agendamento;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Map<String, List<AGC_Prova_Candidato>> getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Map<String, List<AGC_Prova_Candidato>> agendamento) {
        this.agendamento = agendamento;
    }
}

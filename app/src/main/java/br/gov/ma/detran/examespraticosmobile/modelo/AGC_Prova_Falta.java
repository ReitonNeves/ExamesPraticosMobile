package br.gov.ma.detran.examespraticosmobile.modelo;

public class AGC_Prova_Falta {

    private Long id;
    private String cpfCandidato;
    private String dataExame;
    private String localExame;
    private String tipoExame;
    private String tipoFalta;
    private String itemLetra;
    private Integer quantidadeDeFaltas;
    private String cpfInclusao;
    private String dataHoraInclusao;
    private String observacoes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpfCandidato() {
        return cpfCandidato;
    }

    public void setCpfCandidato(String cpfCandidato) {
        this.cpfCandidato = cpfCandidato;
    }

    public String getDataExame() {
        return dataExame;
    }

    public void setDataExame(String dataExame) {
        this.dataExame = dataExame;
    }

    public String getLocalExame() {
        return localExame;
    }

    public void setLocalExame(String localExame) {
        this.localExame = localExame;
    }

    public String getTipoExame() {
        return tipoExame;
    }

    public void setTipoExame(String tipoExame) {
        this.tipoExame = tipoExame;
    }

    public String getTipoFalta() {
        return tipoFalta;
    }

    public void setTipoFalta(String tipoFalta) {
        this.tipoFalta = tipoFalta;
    }

    public String getItemLetra() {
        return itemLetra;
    }

    public void setItemLetra(String itemLetra) {
        this.itemLetra = itemLetra;
    }

    public Integer getQuantidadeDeFaltas() {
        return quantidadeDeFaltas;
    }

    public void setQuantidadeDeFaltas(Integer quantidadeDeFaltas) {
        this.quantidadeDeFaltas = quantidadeDeFaltas;
    }

    public String getCpfInclusao() {
        return cpfInclusao;
    }

    public void setCpfInclusao(String cpfInclusao) {
        this.cpfInclusao = cpfInclusao;
    }

    public String getDataHoraInclusao() {
        return dataHoraInclusao;
    }

    public void setDataHoraInclusao(String dataHoraInclusao) {
        this.dataHoraInclusao = dataHoraInclusao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}

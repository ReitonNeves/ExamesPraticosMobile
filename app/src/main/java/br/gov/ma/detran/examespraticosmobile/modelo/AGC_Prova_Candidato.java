package br.gov.ma.detran.examespraticosmobile.modelo;


import java.io.Serializable;

public class AGC_Prova_Candidato implements Serializable {

    private Long id;
    private String cpfCandidato;
    private String dataExame;
    private String localExame;
    private String tipoExame;
    private String turma;
    private String renach;
    private String nome;
    private String categoria;
    private String nomeCfc;
    private String codigoCfc;
    private String validadeLadv;
    private String situacao;
    private String resultado;
    private String cpfExaminador1;
    private String cpfExaminador2;
    private String observacoes;
    private String placa;
    private String horaInicioExame;
    private String horaFimExame;
    private String cpfEnvioExame;
    private String dataHoraEnvioExame;
    private String cpfDistribuicao;
    private String dataHoraDistribuicaoAgenda;
    private String cpfInclusao;
    private String dataHoraInclusaoAgenda;
    private byte[] imagemDigital;

    @Override
    public String toString() {
        return "AGC_Prova_Candidato{" +
                "id=" + id +
                ", cpfCandidato='" + cpfCandidato + '\'' +
                ", dataExame='" + dataExame + '\'' +
                ", localExame='" + localExame + '\'' +
                ", tipoExame='" + tipoExame + '\'' +
                ", turma='" + turma + '\'' +
                ", renach='" + renach + '\'' +
                ", nome='" + nome + '\'' +
                ", categoria='" + categoria + '\'' +
                ", nomeCfc='" + nomeCfc + '\'' +
                ", codigoCfc='" + codigoCfc + '\'' +
                ", validadeLadv='" + validadeLadv + '\'' +
                ", situacao='" + situacao + '\'' +
                ", resultado='" + resultado + '\'' +
                ", cpfExaminador1='" + cpfExaminador1 + '\'' +
                ", cpfExaminador2='" + cpfExaminador2 + '\'' +
                ", observacoes='" + observacoes + '\'' +
                ", placa='" + placa + '\'' +
                ", horaInicioExame='" + horaInicioExame + '\'' +
                ", horaFimExame='" + horaFimExame + '\'' +
                ", cpfEnvioExame='" + cpfEnvioExame + '\'' +
                ", dataHoraEnvioExame='" + dataHoraEnvioExame + '\'' +
                ", cpfDistribuicao='" + cpfDistribuicao + '\'' +
                ", dataHoraDistribuicaoAgenda='" + dataHoraDistribuicaoAgenda + '\'' +
                ", cpfInclusao='" + cpfInclusao + '\'' +
                ", dataHoraInclusaoAgenda='" + dataHoraInclusaoAgenda + '\'' +
                ", imagemDigital=" + imagemDigital +
                '}';
    }

    public AGC_Prova_Candidato(){}

    public AGC_Prova_Candidato(Long id, String cpfCandidato, String dataExame, String localExame, String tipoExame, String turma, String renach, String nome, String categoria, String nomeCfc, String codigoCfc, String validadeLadv, String situacao, String resultado, String cpfExaminador1, String cpfExaminador2, String observacoes, String placa, String horaInicioExame, String horaFimExame, String cpfEnvioExame, String dataHoraEnvioExame, String cpfDistribuicao, String dataHoraDistribuicaoAgenda, String cpfInclusao, String dataHoraInclusaoAgenda) {
        this.id = id;
        this.cpfCandidato = cpfCandidato;
        this.dataExame = dataExame;
        this.localExame = localExame;
        this.tipoExame = tipoExame;
        this.turma = turma;
        this.renach = renach;
        this.nome = nome;
        this.categoria = categoria;
        this.nomeCfc = nomeCfc;
        this.codigoCfc = codigoCfc;
        this.validadeLadv = validadeLadv;
        this.situacao = situacao;
        this.resultado = resultado;
        this.cpfExaminador1 = cpfExaminador1;
        this.cpfExaminador2 = cpfExaminador2;
        this.observacoes = observacoes;
        this.placa = placa;
        this.horaInicioExame = horaInicioExame;
        this.horaFimExame = horaFimExame;
        this.cpfEnvioExame = cpfEnvioExame;
        this.dataHoraEnvioExame = dataHoraEnvioExame;
        this.cpfDistribuicao = cpfDistribuicao;
        this.dataHoraDistribuicaoAgenda = dataHoraDistribuicaoAgenda;
        this.cpfInclusao = cpfInclusao;
        this.dataHoraInclusaoAgenda = dataHoraInclusaoAgenda;
    }


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

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getRenach() {
        return renach;
    }

    public void setRenach(String renach) {
        this.renach = renach;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNomeCfc() {
        return nomeCfc;
    }

    public void setNomeCfc(String nomeCfc) {
        this.nomeCfc = nomeCfc;
    }

    public String getCodigoCfc() {
        return codigoCfc;
    }

    public void setCodigoCfc(String codigoCfc) {
        this.codigoCfc = codigoCfc;
    }

    public String getValidadeLadv() {
        return validadeLadv;
    }

    public void setValidadeLadv(String validadeLadv) {
        this.validadeLadv = validadeLadv;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getCpfExaminador1() {
        return cpfExaminador1;
    }

    public void setCpfExaminador1(String cpfExaminador1) {
        this.cpfExaminador1 = cpfExaminador1;
    }

    public String getCpfExaminador2() {
        return cpfExaminador2;
    }

    public void setCpfExaminador2(String cpfExaminador2) {
        this.cpfExaminador2 = cpfExaminador2;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getHoraInicioExame() {
        return horaInicioExame;
    }

    public void setHoraInicioExame(String horaInicioExame) {
        this.horaInicioExame = horaInicioExame;
    }

    public String getHoraFimExame() {
        return horaFimExame;
    }

    public void setHoraFimExame(String horaFimExame) {
        this.horaFimExame = horaFimExame;
    }

    public String getCpfEnvioExame() {
        return cpfEnvioExame;
    }

    public void setCpfEnvioExame(String cpfEnvioExame) {
        this.cpfEnvioExame = cpfEnvioExame;
    }

    public String getDataHoraEnvioExame() {
        return dataHoraEnvioExame;
    }

    public void setDataHoraEnvioExame(String dataHoraEnvioExame) {
        this.dataHoraEnvioExame = dataHoraEnvioExame;
    }

    public String getCpfDistribuicao() {
        return cpfDistribuicao;
    }

    public void setCpfDistribuicao(String cpfDistribuicao) {
        this.cpfDistribuicao = cpfDistribuicao;
    }

    public String getDataHoraDistribuicaoAgenda() {
        return dataHoraDistribuicaoAgenda;
    }

    public void setDataHoraDistribuicaoAgenda(String dataHoraDistribuicaoAgenda) {
        this.dataHoraDistribuicaoAgenda = dataHoraDistribuicaoAgenda;
    }

    public String getCpfInclusao() {
        return cpfInclusao;
    }

    public void setCpfInclusao(String cpfInclusao) {
        this.cpfInclusao = cpfInclusao;
    }

    public String getDataHoraInclusaoAgenda() {
        return dataHoraInclusaoAgenda;
    }

    public void setDataHoraInclusaoAgenda(String dataHoraInclusaoAgenda) {
        this.dataHoraInclusaoAgenda = dataHoraInclusaoAgenda;
    }

    public byte[] getImagemDigital() {
        return imagemDigital;
    }

    public void setImagemDigital(byte[] imagemDigital) {
        this.imagemDigital = imagemDigital;
    }
}

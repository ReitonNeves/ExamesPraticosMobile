package br.gov.ma.detran.examespraticosmobile.modeloEspecializada;

public class ListViewFaltas {
    private String descricao;
    private String quantidade;

    public ListViewFaltas(){}

    public ListViewFaltas(String descricao, String quantidade) {
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }
}

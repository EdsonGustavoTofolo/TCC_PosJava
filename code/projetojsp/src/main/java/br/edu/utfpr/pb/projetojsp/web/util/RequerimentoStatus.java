package br.edu.utfpr.pb.projetojsp.web.util;

public class RequerimentoStatus {
    private Integer id;
    private String descricao;

    public RequerimentoStatus(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public RequerimentoStatus() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

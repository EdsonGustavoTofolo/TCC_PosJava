package br.edu.utfpr.pb.projetojsp.enumeration;

public class MotivoRequerimento {
    private Integer id;
    private String descricao;

    public MotivoRequerimento(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
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

package br.edu.utfpr.pb.projetojsp.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Edson on 04/07/2017.
 */
@Entity
@Table(name = "requerimentos_turmas")
public class RequerimentoTurma implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tur")
    private Long id;
    @Column(name = "nome_tur", length = 60, nullable = false)
    private String nome;
    @ManyToOne()
    @JoinColumn(name = "requerimento_id_dis", referencedColumnName = "id_req")
    private Requerimento requerimento;

    public RequerimentoTurma() {
    }

    public RequerimentoTurma(String nome, Requerimento requerimento) {
        this.nome = nome;
        this.requerimento = requerimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Requerimento getRequerimento() {
        return requerimento;
    }

    public void setRequerimento(Requerimento requerimento) {
        this.requerimento = requerimento;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}

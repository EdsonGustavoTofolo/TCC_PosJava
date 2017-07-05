package br.edu.utfpr.pb.projetojsp.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Edson on 04/07/2017.
 */
@Entity
@Table(name = "requerimentos_disciplinas")
public class RequerimentoDisciplina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dis")
    private Long id;
    @Column(name = "nome_dis", length = 60, nullable = false)
    private String nome;
    @Column(name = "professor_dis", length = 60)
    private String professor;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_prova_dis")
    private Date dataProva;
    @ManyToOne()
    @JoinColumn(name = "requerimento_id_dis", referencedColumnName = "id_req")
    private Requerimento requerimento;

    public RequerimentoDisciplina() {
    }

    public RequerimentoDisciplina(String nome, String professor, Date dataProva, Requerimento requerimento) {
        this.nome = nome;
        this.professor = professor;
        this.dataProva = dataProva;
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

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public Date getDataProva() {
        return dataProva;
    }

    public void setDataProva(Date dataProva) {
        this.dataProva = dataProva;
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

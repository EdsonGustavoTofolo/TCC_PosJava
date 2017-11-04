package br.edu.utfpr.pb.projetojsp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Edson on 04/07/2017.
 */
@JsonIgnoreProperties(value = { "requerimento" })
@Entity
@Table(name = "requerimentos_disciplinas")
public class RequerimentoDisciplina implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rdis")
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "professor_id_rdis", referencedColumnName = "id_usu")
    private Usuario professor;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_prova_rdis")
    private Date dataProva;
    @ManyToOne()
    @JoinColumn(name = "disciplina_id_rdis", referencedColumnName = "id_dis", nullable = false)
    private Disciplina disciplina;
    @ManyToOne()
    @JoinColumn(name = "requerimento_id_rdis", referencedColumnName = "id_req", nullable = false)
    private Requerimento requerimento;

    public RequerimentoDisciplina() {
    }

    public RequerimentoDisciplina(Usuario professor, Date dataProva, Disciplina disciplina, Requerimento requerimento) {
        this.professor = professor;
        this.dataProva = dataProva;
        this.disciplina = disciplina;
        this.requerimento = requerimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getProfessor() {
        return professor;
    }

    public void setProfessor(Usuario professor) {
        this.professor = professor;
    }

    public Date getDataProva() {
        return dataProva;
    }

    public void setDataProva(Date dataProva) {
        this.dataProva = dataProva;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
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

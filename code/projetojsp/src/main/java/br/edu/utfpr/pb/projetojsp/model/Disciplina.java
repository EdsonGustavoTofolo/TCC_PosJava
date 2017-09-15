package br.edu.utfpr.pb.projetojsp.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Edson on 09/09/2017.
 */
@Entity
@Table(name = "disciplinas")
public class Disciplina implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dis")
    private Long id;
    @Column(name = "codigo_dis", length = 20, nullable = false)
    private String codigo;
    @Column(name = "nome_dis", length = 80, nullable = false)
    private String nome;
    @Column(name = "grade_curricular_dis", length = 20)
    private String gradeCurricular;
    @ManyToOne
    @JoinColumn(name = "curso_id_dis", referencedColumnName = "id_cur")
    private Curso curso;

    public Disciplina() {
    }

    public Disciplina(String codigo, String nome, String gradeCurricular, Curso curso) {
        this.codigo = codigo;
        this.nome = nome;
        this.gradeCurricular = gradeCurricular;
        this.curso = curso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGradeCurricular() {
        return gradeCurricular;
    }

    public void setGradeCurricular(String gradeCurricular) {
        this.gradeCurricular = gradeCurricular;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
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

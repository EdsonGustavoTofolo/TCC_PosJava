package br.edu.utfpr.pb.projetojsp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Edson on 04/11/2017.
 */
@JsonIgnoreProperties(value = { "requerimento" })
@Entity
@Table(name = "requerimentos_convalidacoes")
public class RequerimentoConvalidacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_con")
    private Long id;
    @Column(name = "disciplina_convalidacao_con", length = 100, nullable = false)
    private String disciplinaConvalidacao;
    @Column(name = "carga_horaria_con", precision = 3, nullable = false)
    private Integer cargaHoraria;
    @Column(name = "nota_con", precision = 3, scale = 2, nullable = false)
    private Double nota;
    @Column(name = "frequencia_con", precision = 5, scale = 2, nullable = false)
    private Double frequencia;
    @Column(name = "nota_final_con", precision = 3, scale = 2, nullable = false)
    private Double notaFinal;
    @Column(name = "freq_final_con", precision = 5, scale = 2, nullable = false)
    private Double freqFinal;
    @Column(name = "dispensado_con", nullable = false)
    private Boolean dispensado;
    @ManyToOne()
    @JoinColumn(name = "disciplina_utfpr_id_con", referencedColumnName = "id_dis", nullable = false)
    private Disciplina disciplinaUtfpr;
    @ManyToOne()
    @JoinColumn(name = "professor_id_con", referencedColumnName = "id_usu")
    private Usuario professor;
    @ManyToOne()
    @JoinColumn(name = "requerimento_id_con", referencedColumnName = "id_req", nullable = false)
    private Requerimento requerimento;
    @OneToOne(mappedBy = "convalidacao", targetEntity = ParecerConvalidacao.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ParecerConvalidacao parecer;

    public RequerimentoConvalidacao() {
    }

    public RequerimentoConvalidacao(String disciplinaConvalidacao, Integer cargaHoraria, Double nota, Double frequencia,
                                    Double notaFinal, Double freqFinal, Boolean dispensado, Disciplina disciplinaUtfpr,
                                    Usuario professor, Requerimento requerimento, ParecerConvalidacao parecer) {
        this.disciplinaConvalidacao = disciplinaConvalidacao;
        this.cargaHoraria = cargaHoraria;
        this.nota = nota;
        this.frequencia = frequencia;
        this.notaFinal = notaFinal;
        this.freqFinal = freqFinal;
        this.dispensado = dispensado;
        this.disciplinaUtfpr = disciplinaUtfpr;
        this.professor = professor;
        this.requerimento = requerimento;
        this.parecer = parecer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisciplinaConvalidacao() {
        return disciplinaConvalidacao;
    }

    public void setDisciplinaConvalidacao(String disciplinaConvalidacao) {
        this.disciplinaConvalidacao = disciplinaConvalidacao;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Double getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Double frequencia) {
        this.frequencia = frequencia;
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public Double getFreqFinal() {
        return freqFinal;
    }

    public void setFreqFinal(Double freqFinal) {
        this.freqFinal = freqFinal;
    }

    public Boolean getDispensado() {
        return dispensado;
    }

    public void setDispensado(Boolean dispensado) {
        this.dispensado = dispensado;
    }

    public Disciplina getDisciplinaUtfpr() {
        return disciplinaUtfpr;
    }

    public void setDisciplinaUtfpr(Disciplina disciplinaUtfpr) {
        this.disciplinaUtfpr = disciplinaUtfpr;
    }

    public Usuario getProfessor() {
        return professor;
    }

    public void setProfessor(Usuario professor) {
        this.professor = professor;
    }

    public Requerimento getRequerimento() {
        return requerimento;
    }

    public void setRequerimento(Requerimento requerimento) {
        this.requerimento = requerimento;
    }

    public ParecerConvalidacao getParecer() {
        return parecer;
    }

    public void setParecer(ParecerConvalidacao parecer) {
        this.parecer = parecer;
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

package br.edu.utfpr.pb.projetojsp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Edson on 12/11/2017.
 */
@JsonIgnoreProperties(value = { "convalidacao" })
@Entity
@Table(name = "pareceres_convalidacao")
public class ParecerConvalidacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_par")
    private Long id;
    @Column(name = "deferido_con")
    private Boolean deferido;
    @Column(name = "justificativa_con")
    private String justificativa;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_par", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date data;
    @OneToOne
    @JoinColumn(name = "convalidacao_id_par", referencedColumnName = "id_con", nullable = false)
    private RequerimentoConvalidacao convalidacao;

    public ParecerConvalidacao() {
    }

    public ParecerConvalidacao(Boolean deferido, String justificativa, Date data,
                               RequerimentoConvalidacao convalidacao) {
        this.deferido = deferido;
        this.justificativa = justificativa;
        this.data = data;
        this.convalidacao = convalidacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDeferido() {
        return deferido;
    }

    public void setDeferido(Boolean deferido) {
        this.deferido = deferido;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public RequerimentoConvalidacao getConvalidacao() {
        return convalidacao;
    }

    public void setConvalidacao(RequerimentoConvalidacao convalidacao) {
        this.convalidacao = convalidacao;
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

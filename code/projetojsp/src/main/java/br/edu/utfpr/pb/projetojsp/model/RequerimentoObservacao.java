package br.edu.utfpr.pb.projetojsp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Edson on 27/10/2017.
 */
@JsonIgnoreProperties(value = { "requerimento" })
@Entity
@Table(name = "requerimentos_observacoes")
public class RequerimentoObservacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_obs")
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "requerimento_id_obs", referencedColumnName = "id_req", nullable = false)
    private Requerimento requerimento;
    @ManyToOne()
    @JoinColumn(name = "usuario_id_obs", referencedColumnName = "id_usu")
    private Usuario usuario;
    @Column(name = "texto_obs")
    private String texto;
    @Column(name = "deferido_obs")
    private Boolean deferido;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_obs", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date data;

    public RequerimentoObservacao() {
    }

    public RequerimentoObservacao(Requerimento requerimento, Usuario usuario, String texto, Boolean deferido, Date data) {
        this.requerimento = requerimento;
        this.usuario = usuario;
        this.texto = texto;
        this.deferido = deferido;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Requerimento getRequerimento() {
        return requerimento;
    }

    public void setRequerimento(Requerimento requerimento) {
        this.requerimento = requerimento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Boolean getDeferido() {
        return deferido;
    }

    public void setDeferido(Boolean deferido) {
        this.deferido = deferido;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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

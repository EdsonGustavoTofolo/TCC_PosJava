package br.edu.utfpr.pb.projetojsp.model;

import br.edu.utfpr.pb.projetojsp.enumeration.StatusRequerimentoEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Edson on 04/07/2017.
 */
@Entity
@Table(name = "requerimentos")
public class Requerimento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_req")
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_req")
    private StatusRequerimentoEnum status;
    @Column(name = "motivo_req", columnDefinition = "TINYINT")
    private Integer motivo;
    @Column(name = "obs_req")
    private String observacao;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_req", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date data;
    @ManyToOne()
    @JoinColumn(name = "usuario_id_req", referencedColumnName = "id_usu")
    private Usuario usuario;

    public Requerimento() {
    }

    public Requerimento(StatusRequerimentoEnum status, Integer motivo, String observacao, Date data, Usuario usuario) {
        this.status = status;
        this.motivo = motivo;
        this.observacao = observacao;
        this.data = data;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusRequerimentoEnum getStatus() {
        return status;
    }

    public void setStatus(StatusRequerimentoEnum status) {
        this.status = status;
    }

    public Integer getMotivo() {
        return motivo;
    }

    public void setMotivo(Integer motivo) {
        this.motivo = motivo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

package br.edu.utfpr.pb.projetojsp.model;

import br.edu.utfpr.pb.projetojsp.enumeration.StatusRequerimentoEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_ultimo_status_req", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date dataUltimoStatus;
    @ManyToOne()
    @JoinColumn(name = "usuario_id_req", referencedColumnName = "id_usu")
    private Usuario usuario;
    @OneToMany(mappedBy = "requerimento", targetEntity = RequerimentoDisciplina.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<RequerimentoDisciplina> disciplinas;
    @OneToMany(mappedBy = "requerimento", targetEntity = RequerimentoAnexo.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT) //SE NAO DA ERRO, POIS NAO PODE TER DOIS ONETOMANY COM O FETCHTYPE EAGER
    private List<RequerimentoAnexo> anexos;
    @OneToMany(mappedBy = "requerimento", targetEntity = RequerimentoObservacao.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT) //SE NAO DA ERRO, POIS NAO PODE TER DOIS ONETOMANY COM O FETCHTYPE EAGER
    private List<RequerimentoObservacao> observacoes;
    @OneToMany(mappedBy = "requerimento", targetEntity = RequerimentoConvalidacao.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT) //SE NAO DA ERRO, POIS NAO PODE TER DOIS ONETOMANY COM O FETCHTYPE EAGER
    private List<RequerimentoConvalidacao> convalidacoes;

    public Requerimento() {
    }

    public Requerimento(StatusRequerimentoEnum status, Integer motivo, String observacao, Date data, Date dataUltimoStatus, Usuario usuario,
                        List<RequerimentoDisciplina> requerimentoDisciplinaList, List<RequerimentoAnexo> anexoList,
                        List<RequerimentoObservacao> observacaoList,
                        List<RequerimentoConvalidacao> convalidacaoList) {
        this.status = status;
        this.motivo = motivo;
        this.observacao = observacao;
        this.data = data;
        this.dataUltimoStatus = dataUltimoStatus;
        this.usuario = usuario;
        this.disciplinas = requerimentoDisciplinaList;
        this.anexos = anexoList;
        this.observacoes = observacaoList;
        this.convalidacoes = convalidacaoList;
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

    public Date getDataUltimoStatus() {
        return dataUltimoStatus;
    }

    public void setDataUltimoStatus(Date dataUltimoStatus) {
        this.dataUltimoStatus = dataUltimoStatus;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<RequerimentoDisciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<RequerimentoDisciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public List<RequerimentoAnexo> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<RequerimentoAnexo> anexos) {
        this.anexos = anexos;
    }

    public List<RequerimentoObservacao> getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(List<RequerimentoObservacao> observacoes) {
        this.observacoes = observacoes;
    }

    public List<RequerimentoConvalidacao> getConvalidacoes() {
        return convalidacoes;
    }

    public void setConvalidacoes(List<RequerimentoConvalidacao> convalidacoes) {
        this.convalidacoes = convalidacoes;
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

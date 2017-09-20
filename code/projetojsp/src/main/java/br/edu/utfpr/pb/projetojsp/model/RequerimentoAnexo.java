package br.edu.utfpr.pb.projetojsp.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Edson on 20/09/2017.
 */
@Entity
@Table(name = "requerimentos_anexos")
public class RequerimentoAnexo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome_anx", length = 50, nullable = false)
    private String nome;
    @Column(name = "tipo_anx", length = 50, nullable = false)
    private String tipo;
    @Column(name = "content_type_anx", length = 250, nullable = false)
    private String contentType;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] arquivo;
    @ManyToOne()
    @JoinColumn(name = "requerimento_id_anx", referencedColumnName = "id_req")
    private Requerimento requerimento;

    public RequerimentoAnexo(String nome, String tipo, String contentType, byte[] arquivo, Requerimento requerimento) {
        this.nome = nome;
        this.tipo = tipo;
        this.contentType = contentType;
        this.arquivo = arquivo;
        this.requerimento = requerimento;
    }

    public RequerimentoAnexo() {
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
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

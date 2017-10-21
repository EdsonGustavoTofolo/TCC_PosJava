package br.edu.utfpr.pb.projetojsp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Edson on 20/09/2017.
 */
@JsonIgnoreProperties(value = { "requerimento", "arquivo" })
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
    @Column(name = "tamanho_anx", nullable = false)
    private Long tamanho;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] arquivo;
    @ManyToOne()
    @JoinColumn(name = "requerimento_id_anx", referencedColumnName = "id_req")
    private Requerimento requerimento;

    private transient double size;
    private transient String unitSize;

    public RequerimentoAnexo(String nome, String tipo, String contentType, Long tamanho, byte[] arquivo, Requerimento requerimento) {
        this.nome = nome;
        this.tipo = tipo;
        this.contentType = contentType;
        this.tamanho = tamanho;
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

    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
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

    public double getSize() {
        if (this.size == 0.0) {
            assignSizeFormat();
        }
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getUnitSize() {
        if ("".equals(this.unitSize)) {
            assignSizeFormat();
        }
        return unitSize;
    }

    public void setUnitSize(String unitSize) {
        this.unitSize = unitSize;
    }

    private void assignSizeFormat() {
        double selectedSize = 0;
        String selectedUnit = "";
        if (this.tamanho > 0) {
            String[] units = {"TB", "GB", "MB", "KB", "b"};
            for (int i = 0; i < units.length; i++) {
                String unit = units[i];
                double cutoff = Math.pow(1000, 4 - i);
                if (this.tamanho > cutoff) {
                    selectedSize = this.tamanho / Math.pow(1000, 4 - i);
                    selectedUnit = unit;
                    break;
                }
            }
            selectedSize = Math.floor(10 * selectedSize) / 10;
        }
        this.size = selectedSize;
        this.unitSize = selectedUnit;
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

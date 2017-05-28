package br.edu.utfpr.pb.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Edson on 05/03/2017.
 */
@Entity
public class Pessoa implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String fone;

    public Pessoa(String nome, String fone) {
        this.nome = nome;
        this.fone = fone;
    }

    public Pessoa() {
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

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }
}

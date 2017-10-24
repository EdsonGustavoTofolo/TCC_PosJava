package br.edu.utfpr.pb.projetojsp.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Edson on 04/07/2017.
 * Três Perfis de Usuário:
 - ROLE_ALUNO - Vai poder cadastrar os requerimentos e verificar o Status
 - ROLE_DERAC - Vai visualizar e administrar os requerimentos
 - ROLE_PROFESSOR -
 - ROLE_COORDENACAO - Vai visualizar e editar os requerimentos a ele atribuídos (essa parte vai ficar para depois,
 inicialmente faremos apenas a tela de Aluno e DERAC, depois vemos os demais requisitos).
 */
@Entity
@Table(name = "permissoes")
public class Permissao implements Serializable, GrantedAuthority {
    public static final String ROLE_ALUNO = "ROLE_ALUNO";
    public static final String ROLE_PROFESSOR = "ROLE_PROFESSOR";
    public static final String ROLE_DERAC = "ROLE_DERAC";
    public static final String ROLE_COORDENACAO = "ROLE_COORDENACAO";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_per")
    private Long id;
    @Column(name = "permissao_per", length = 45, nullable = false)
    private String permissao;

    public Permissao() {
    }

    public Permissao(String permissao) {
        this.permissao = permissao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String getAuthority() {
        return this.getPermissao();
    }
}

package br.edu.utfpr.pb.projetojsp.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Edson on 04/07/2017.
 */

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable, UserDetails {

    private static final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usu")
    private Long id;
    @Column(name = "codigo_usu", length = 25, nullable = false, unique = true)
    private String codigo; //RA
    @Column(name = "email_usu", length = 60, nullable = false)
    private String email;
    @Column(name = "senha_usu", length = 256, nullable = false)
    private String senha;
    @Column(name = "nome_usu", length = 60, nullable = false)
    private String nome;
    @Column(name = "telefone_usu", length = 11, nullable = false)
    private String telefone;
    @Column(name = "curso_usu", length = 60, nullable = false)
    private String curso;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_cadastro_usu", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date dataCadastro;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Permissao> permissoes;

    public Usuario() {
    }

    public Usuario(String codigo, String email, String senha, String nome, String telefone, String curso,
                   Date dataCadastro, Set<Permissao> permissoes) {
        this.codigo = codigo;
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.telefone = telefone;
        this.curso = curso;
        this.dataCadastro = dataCadastro;
        this.permissoes = permissoes;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Set<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(Set<Permissao> permissoes) {
        this.permissoes = permissoes;
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auto = new ArrayList<>();
        auto.addAll(getPermissoes());
        return auto;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void addPermissao(Permissao permissao){
        if (permissoes == null){
            permissoes = new HashSet<>();
        }
        permissoes.add(permissao);
    }

    public String getEncodedPassword(String pass){
        if (!pass.isEmpty()){
            return bCrypt.encode(pass);
        }
        return pass;
    }
}

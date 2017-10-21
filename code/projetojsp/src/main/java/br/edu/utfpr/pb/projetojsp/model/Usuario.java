package br.edu.utfpr.pb.projetojsp.model;

import br.edu.utfpr.pb.projetojsp.enumeration.TipoUsuarioEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(value = { "senha", "telefone", "email", "permissoes" })
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable, UserDetails {

    private static final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder(10);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usu")
    private Long id;
    @Column(name = "username_usu", length = 60, nullable = false, unique = true)
    private String username; //RA e para professores a primeira parte do email, antes do @
    @Column(name = "email_usu", length = 60, nullable = false)
    private String email;
    @Column(name = "senha_usu", length = 256, nullable = false)
    private String senha;
    @Column(name = "nome_usu", length = 60, nullable = false)
    private String nome;
    @Column(name = "telefone_usu", length = 11)
    private String telefone;
    @ManyToOne()
    @JoinColumn(name = "curso_id_usu", referencedColumnName = "id_cur")
    private Curso curso; //curso referente ao que o usuário faz
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo_usu")
    private TipoUsuarioEnum tipo;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_cadastro_usu", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date dataCadastro;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Permissao> permissoes;

    public Usuario() {
    }

    public Usuario(String username, String email, String senha, String nome, String telefone, Curso curso, TipoUsuarioEnum tipo, Date dataCadastro, Set<Permissao> permissoes) {
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.telefone = telefone;
        this.curso = curso;
        this.tipo = tipo;
        this.dataCadastro = dataCadastro;
        this.permissoes = permissoes;
    }

    public Usuario(String username, String email, String senha, String nome, String telefone, Curso curso, TipoUsuarioEnum tipo, Date dataCadastro) {
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.telefone = telefone;
        this.curso = curso;
        this.tipo = tipo;
        this.dataCadastro = dataCadastro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public TipoUsuarioEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuarioEnum tipo) {
        this.tipo = tipo;
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

    public void setUsername(String username) {
        this.username = username;
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
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

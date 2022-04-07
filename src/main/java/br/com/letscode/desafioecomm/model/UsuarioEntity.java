package br.com.letscode.desafioecomm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

@Entity(name = "USUARIO")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "SENHA")
    private String senha;

    @Column(name = "DATA_Nascimento")
    private Date dataNascimento;

    @Column(name = "DATA_CRIACAO")
    private ZonedDateTime dataCriacao;

    @Column(name = "DATA_ATUALIZACAO")
    private ZonedDateTime dataAtualizacao;

    public UsuarioEntity() {
    }

    public UsuarioEntity(String nome,
                         String senha,
                         Date dataNascimento) {
        this.nome = nome;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.dataCriacao = ZonedDateTime.now();
        this.dataAtualizacao = ZonedDateTime.now();
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public ZonedDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(ZonedDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public ZonedDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(ZonedDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioEntity that = (UsuarioEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(senha, that.senha) && Objects.equals(dataNascimento, that.dataNascimento) && Objects.equals(dataCriacao, that.dataCriacao) && Objects.equals(dataAtualizacao, that.dataAtualizacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, senha, dataNascimento, dataCriacao, dataAtualizacao);
    }

    @Override
    public String toString() {
        return "UsuarioEntity{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", dataCriacao=" + dataCriacao +
                ", dataAtualizacao=" + dataAtualizacao +
                '}';
    }
}

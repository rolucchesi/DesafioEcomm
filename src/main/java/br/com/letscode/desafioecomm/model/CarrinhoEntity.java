package br.com.letscode.desafioecomm.model;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "CARRINHO")
public class CarrinhoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "idUsuario")
    private UsuarioEntity usuario;

    @Column(name = "DATA_CRIACAO")
    private ZonedDateTime dataCriacao;

    @Column(name = "DATA_ATUALIZACAO")
    private ZonedDateTime dataAtualizacao;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProdutoCarrinhoEntity> produtos;

    public CarrinhoEntity(final UsuarioEntity usuario) {
        this.usuario = usuario;
        this.dataCriacao = ZonedDateTime.now();
        this.dataAtualizacao = ZonedDateTime.now();
        this.produtos = new ArrayList<>();
    }

    public CarrinhoEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
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

    public List<ProdutoCarrinhoEntity> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoCarrinhoEntity> produtos) {
        this.produtos = produtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarrinhoEntity)) return false;
        CarrinhoEntity that = (CarrinhoEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(usuario, that.usuario) && Objects.equals(dataCriacao, that.dataCriacao) && Objects.equals(dataAtualizacao, that.dataAtualizacao) && Objects.equals(produtos, that.produtos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuario, dataCriacao, dataAtualizacao, produtos);
    }

    @Override
    public String toString() {
        return "CarrinhoEntity{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", dataCriacao=" + dataCriacao +
                ", dataAtualizacao=" + dataAtualizacao +
                ", produtos=" + produtos +
                '}';
    }
}

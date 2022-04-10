package br.com.letscode.desafioecomm.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CARRINHO_PRODUTO")
public class ProdutoCarrinhoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "PRODUTO")
    private ProdutoEntity produto;

    @Column(name = "QUANTIDADE")
    private Integer quantidade;

    public ProdutoCarrinhoEntity() {
    }

    public ProdutoCarrinhoEntity(final ProdutoEntity produto, final Integer quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProdutoEntity getProduto() {
        return produto;
    }

    public void setProduto(ProdutoEntity produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProdutoCarrinhoEntity)) return false;
        ProdutoCarrinhoEntity that = (ProdutoCarrinhoEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(produto, that.produto) && Objects.equals(quantidade, that.quantidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, produto, quantidade);
    }

    @Override
    public String toString() {
        return "ProdutoCarrinhoEntity{" +
                "id=" + id +
                ", produto=" + produto +
                ", quantidade=" + quantidade +
                '}';
    }
}

package br.com.letscode.desafioecomm.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ProdutoCarrinhoDto {

    private Long idProduto;
    private Integer quantidade;

    @JsonCreator
    public ProdutoCarrinhoDto(@JsonProperty("id") Long idProduto,
                              @JsonProperty("quantidade") Integer quantidade) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
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
        if (!(o instanceof ProdutoCarrinhoDto)) return false;
        ProdutoCarrinhoDto that = (ProdutoCarrinhoDto) o;
        return Objects.equals(idProduto, that.idProduto) && Objects.equals(quantidade, that.quantidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduto, quantidade);
    }

    @Override
    public String toString() {
        return "ProdutoCarrinhoDto{" +
                "idProduto=" + idProduto +
                ", quantidade=" + quantidade +
                '}';
    }
}

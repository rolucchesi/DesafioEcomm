package br.com.letscode.desafioecomm.model;

import lombok.AccessLevel;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

public class CarrinhoEntity {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATA_CRIACAO")
    private ZonedDateTime dataCriacao;

    @Column(name = "DATA_ATUALIZACAO")
    private ZonedDateTime dataAtualizacao;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProdutoEntity> produtos;
}

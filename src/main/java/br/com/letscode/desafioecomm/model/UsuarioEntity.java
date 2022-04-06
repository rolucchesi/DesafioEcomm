package br.com.letscode.desafioecomm.model;

import br.com.letscode.desafioecomm.enums.ProdutoStatus;
import lombok.AccessLevel;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public class UsuarioEntity {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "DATA_Nascimento")
    private ZonedDateTime dataNascimento;

    @Column(name = "DATA_CRIACAO")
    private ZonedDateTime dataCriacao;

    @Column(name = "DATA_ATUALIZACAO")
    private ZonedDateTime dataAtualizacao;

}

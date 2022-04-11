package br.com.letscode.desafioecomm.model;

import br.com.letscode.desafioecomm.dto.ProdutoDTO;
import br.com.letscode.desafioecomm.enums.ProdutoStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity(name = "PRODUTO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoEntity {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODIGO")
    private UUID codigo;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "VALOR")
    private BigDecimal valor;

    @Column(name = "CODIGO_BARRA")
    private String codigoBarra;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private ProdutoStatus status;

//    @ManyToOne
//    @JoinColumn(name = "ID_FABRICANTE")
//    private FabricanteEntity fabricante;

    @Column(name = "PESO")
    private Integer peso;

    @Column(name = "PESO_UNIDADE_MEDIDA")
    private String pesoUnidadeMedida;

    @Column(name = "DATA_CRIACAO")
    private ZonedDateTime dataCriacao;

    @Column(name = "DATA_ATUALIZACAO")
    private ZonedDateTime dataAtualizacao;


    public  ProdutoEntity(String nome, String descricao,
                         BigDecimal valor, String codigoBarra,
                         Integer peso,
                         String pesoUnidadeMedida) {
        this.codigo = UUID.randomUUID();
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.codigoBarra = codigoBarra;
        this.status = ProdutoStatus.ATIVO;
        this.peso = peso;
        this.pesoUnidadeMedida = pesoUnidadeMedida;
        this.dataCriacao = ZonedDateTime.now();
        this.dataAtualizacao = ZonedDateTime.now();
    }

    public ProdutoEntity(ProdutoDTO produtoDTO){
        this.codigo = UUID.randomUUID();
        this.nome = produtoDTO.getNome();
        this.descricao = produtoDTO.getDescricao();
        this.valor = produtoDTO.getValor();
        this.codigoBarra = produtoDTO.getCodigoBarra();
        this.status = ProdutoStatus.ATIVO;
        this.peso = produtoDTO.getPeso();
        this.pesoUnidadeMedida = produtoDTO.getPesoUnidadeMedida();
        this.dataCriacao = ZonedDateTime.now();
        this.dataAtualizacao = ZonedDateTime.now();
    }

}

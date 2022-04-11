package br.com.letscode.desafioecomm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {

    private String nome;

    private String descricao;

    private BigDecimal valor;

    private String codigoBarra;

    private Integer peso;

    private String pesoUnidadeMedida;
}

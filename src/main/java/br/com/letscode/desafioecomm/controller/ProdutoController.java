package br.com.letscode.desafioecomm.controller;

import br.com.letscode.desafioecomm.dto.ProdutoDTO;
import br.com.letscode.desafioecomm.model.ProdutoEntity;
import br.com.letscode.desafioecomm.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@AllArgsConstructor
@RequestMapping("produtos")
@RestController
public class ProdutoController {
    private ProdutoService produtoService;

    @GetMapping()
    public ResponseEntity<Page<ProdutoEntity>> get(
            @RequestParam(name = "offset") Integer offset,
            @RequestParam(name = "limit") Integer limit
//            @RequestParam(name = "nome", required = false) String nome,
//            @RequestParam(name = "valor_maximo", required = false) BigDecimal valorMaximo
    ){
        Page<ProdutoEntity> produtos = produtoService.buscarTodos(offset, limit);
        return ResponseEntity.ok(produtos);
    }

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoEntity> create(
            @RequestBody ProdutoDTO request
    ){
        ProdutoEntity produto = produtoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

}

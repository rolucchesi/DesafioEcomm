package br.com.letscode.desafioecomm.controller;

import br.com.letscode.desafioecomm.dto.ProdutoCarrinhoDto;
import br.com.letscode.desafioecomm.model.CarrinhoEntity;
import br.com.letscode.desafioecomm.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping
    public ResponseEntity<List<CarrinhoEntity>> listarTodosCarrinhos() {
        List<CarrinhoEntity> carrinhos = carrinhoService.listarTodosCarrinhos();
        return ResponseEntity.ok(carrinhos);
    }

    @GetMapping("/{nome}")
    public ResponseEntity<CarrinhoEntity> buscarCarrinhoUsuario(@PathVariable("nome") String nome) {
        CarrinhoEntity carrinho = carrinhoService.buscarCarrinhoPorNome(nome);
        return ResponseEntity.ok(carrinho);
    }

    @PostMapping("/{nome}")
    public ResponseEntity<CarrinhoEntity> adicionarProdutoAoCarrinho(@PathVariable("nome") String nome,
                                                                     @RequestBody ProdutoCarrinhoDto dto) {
        CarrinhoEntity carrinho = carrinhoService.adicionarProdutoAoCarrinho(nome, dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(carrinho);
    }

    @DeleteMapping("/{nome}/{idProduto}")
    public ResponseEntity<Void> removerProdutoDoCarrinho(@PathVariable("nome") String nome,
                                                         @PathVariable("idProduto") Long idProduto) {
        carrinhoService.removerProdutoDoCarrinho(nome, idProduto);
        return ResponseEntity.accepted().build();
    }
}

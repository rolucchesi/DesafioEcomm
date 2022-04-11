package br.com.letscode.desafioecomm.service;

import br.com.letscode.desafioecomm.dto.ProdutoDTO;
import br.com.letscode.desafioecomm.model.ProdutoEntity;
import br.com.letscode.desafioecomm.repository.ProdutoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class ProdutoServiceTest {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    private ProdutoEntity criarProdutoParaTeste() {
        ProdutoDTO dto = new ProdutoDTO("Bola", "Bola redonda", BigDecimal.TEN, "1234", 1, "kg");
        return produtoService.criarProduto(dto);
    }

    @Test
    void deveCriarProduto() {
        ProdutoDTO dto = new ProdutoDTO("Bola", "Bola redonda", BigDecimal.TEN, "1234", 1, "kg");
        ProdutoEntity produto = produtoService.criarProduto(dto);

        Optional<ProdutoEntity> produtoSalvo = produtoRepository.findById(produto.getId());

        Assertions.assertThat(produtoSalvo).isPresent();
        Assertions.assertThat(produto.getCodigo()).isEqualTo(produtoSalvo.get().getCodigo());
    }

    @Test
    void deveBuscarTodosOsProdutos() {
        criarProdutoParaTeste();
        Page<ProdutoEntity> produtos = produtoService.buscarTodos(0, 10);

        Assertions.assertThat(produtos.getTotalElements()).isEqualTo(1L);
    }

    @Test
    void buscarPorId() {
        ProdutoEntity produto = criarProdutoParaTeste();
        ProdutoEntity produtoSalvo = produtoService.buscarPorId(produto.getId());

        Assertions.assertThat(produto.getCodigo()).isEqualTo(produtoSalvo.getCodigo());
    }

    @Test
    void deveLancarExcecaoAoBuscarProdutoQueNaoExistePorId() {
        criarProdutoParaTeste();

        Assertions.assertThatThrownBy(() -> {
            produtoService.buscarPorId(10L);
        }).hasMessage("Não existe produto com este ID!");
    }

    @Test
    void deveBuscarProdutoPorCodigoDeBarra() {
        criarProdutoParaTeste();
        ProdutoEntity produtoSalvo = produtoService.buscarPorCodigoBarra("1234");

        Assertions.assertThat(produtoSalvo).isNotNull();
    }

    @Test
    void deveRetornarProdutoNuloAoBuscarPorCodigoDeBarra() {
        criarProdutoParaTeste();
        ProdutoEntity produtoSalvo = produtoService.buscarPorCodigoBarra("2345");

        Assertions.assertThat(produtoSalvo).isNull();
    }

    @Test
    void deveDeletarProduto() {
        ProdutoEntity produto = criarProdutoParaTeste();

        produtoService.deletarProduto(produto.getId());

        Optional<ProdutoEntity> produtoSalvo = produtoRepository.findById(produto.getId());

        Assertions.assertThat(produtoSalvo).isEmpty();
    }

    @Test
    void deveEditarProduto() {
        ProdutoEntity produto = criarProdutoParaTeste();
        ProdutoDTO dto = new ProdutoDTO("Armario", "Armario de madeira", BigDecimal.ONE, "2345", 20, "kg");

        produtoService.editarProduto(produto.getId(), dto);

        Optional<ProdutoEntity> produtoEditado = produtoRepository.findById(produto.getId());

        Assertions.assertThat(produtoEditado).isPresent();
        Assertions.assertThat(produtoEditado.get().getNome()).isEqualTo("Armario");
    }

    @AfterEach
    void limparBaseDeDados() {
        produtoRepository.deleteAll();
    }
}
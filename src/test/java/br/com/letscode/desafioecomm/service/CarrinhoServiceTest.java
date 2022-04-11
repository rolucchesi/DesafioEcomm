package br.com.letscode.desafioecomm.service;

import br.com.letscode.desafioecomm.dto.ProdutoCarrinhoDto;
import br.com.letscode.desafioecomm.model.CarrinhoEntity;
import br.com.letscode.desafioecomm.model.ProdutoEntity;
import br.com.letscode.desafioecomm.model.UsuarioEntity;
import br.com.letscode.desafioecomm.repository.CarrinhoRepository;
import br.com.letscode.desafioecomm.repository.ProdutoCarrinhoRepository;
import br.com.letscode.desafioecomm.repository.ProdutoRepository;
import br.com.letscode.desafioecomm.repository.UsuarioRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class CarrinhoServiceTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoCarrinhoRepository produtoCarrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @BeforeAll
    void criarDadosParaTeste() {
        System.out.println("dados criados");
        UsuarioEntity usuario = new UsuarioEntity("lucas", "123", LocalDate.of(1991, 3, 24));
        usuario.setId(1L);
        usuarioRepository.save(usuario);
        UsuarioEntity usuario2 = new UsuarioEntity("rodrigo", "123", LocalDate.of(1991, 3, 24));
        usuario2.setId(2L);
        usuarioRepository.save(usuario2);
        ProdutoEntity produto = new ProdutoEntity("Bola", "Bola redonda", BigDecimal.TEN, "1234", 1, "kg");
        produto.setId(1L);
        produtoRepository.save(produto);
        ProdutoEntity produto2 = new ProdutoEntity("Armario", "Armario de madeira", BigDecimal.TEN, "1234", 1, "kg");
        produto2.setId(2L);
        produtoRepository.save(produto2);
    }

    @Test
    void adicionarProdutoAoCarrinho() {
        UsuarioEntity usuario = usuarioRepository.findByNome("lucas").get();

        ProdutoCarrinhoDto produtoCarrinho = new ProdutoCarrinhoDto(1L, 1);

        carrinhoService.adicionarProdutoAoCarrinho("lucas", produtoCarrinho);

        Optional<CarrinhoEntity> carrinho = carrinhoRepository.findByUsuario(usuario);

        Assertions.assertThat(carrinho).isPresent();
        Assertions.assertThat(carrinho.get().getProdutos()).hasSize(1);

        carrinhoRepository.deleteAll();
    }

    @Test
    void naoDeveAdicionarProdutoQueNaoExisteAoCarrinho() {
        ProdutoCarrinhoDto produtoCarrinho = new ProdutoCarrinhoDto(10L, 1);

        Assertions.assertThatThrownBy(() -> {
            carrinhoService.adicionarProdutoAoCarrinho("lucas", produtoCarrinho);
        }).hasMessage("Não existe produto com este ID!");
    }

    @Test
    void naoDeveAdicionarProdutoAoCarrinhoDeUsuarioQueNaoExiste() {
        ProdutoCarrinhoDto produtoCarrinho = new ProdutoCarrinhoDto(1L, 1);

        Assertions.assertThatThrownBy(() -> {
            carrinhoService.adicionarProdutoAoCarrinho("rafael", produtoCarrinho);
        }).hasMessage("Não existe um usuário com este nome!");
    }

    @Test
    void deveBuscarCarrinhoPorNomeDeUsuario() {
        ProdutoCarrinhoDto produtoCarrinho = new ProdutoCarrinhoDto(1L, 1);

        carrinhoService.adicionarProdutoAoCarrinho("lucas", produtoCarrinho);

        CarrinhoEntity carrinho = carrinhoService.buscarCarrinhoPorNome("lucas");

        Assertions.assertThat(carrinho).isNotNull();
        Assertions.assertThat(carrinho.getProdutos()).hasSize(1);

        carrinhoRepository.deleteAll();
    }

    @Test
    void deveLancarExcecaoSeCarrinhoEstiverVazioAoBuscarCarrinhoPorNomeDeUsuario() {
        Assertions.assertThatThrownBy(() -> {
            carrinhoService.buscarCarrinhoPorNome("rodrigo");
        }).hasMessage("Este usuário ainda não possui um carrinho!");
    }

    @Test
    void deveLancarExcecaoSeUsuarioNaoExisteAoBuscarCarrinhoPorNomeDeUsuario() {
        Assertions.assertThatThrownBy(() -> {
            carrinhoService.buscarCarrinhoPorNome("rafael");
        }).hasMessage("Não existe um usuário com este nome!");
    }

    @Test
    void deveRemoverProdutoDoCarrinho() {
        UsuarioEntity usuario = usuarioRepository.findByNome("lucas").get();

        ProdutoCarrinhoDto produtoCarrinho = new ProdutoCarrinhoDto(1L, 1);
        ProdutoCarrinhoDto produtoCarrinho2 = new ProdutoCarrinhoDto(2L, 1);

        carrinhoService.adicionarProdutoAoCarrinho("lucas", produtoCarrinho);
        carrinhoService.adicionarProdutoAoCarrinho("lucas", produtoCarrinho2);

        Optional<CarrinhoEntity> carrinho = carrinhoRepository.findByUsuario(usuario);

        Assertions.assertThat(carrinho).isPresent();
        Assertions.assertThat(carrinho.get().getProdutos()).hasSize(2);

        carrinhoService.removerProdutoDoCarrinho("lucas", 1L);

        Optional<CarrinhoEntity> carrinhoAlterado = carrinhoRepository.findByUsuario(usuario);
        Assertions.assertThat(carrinhoAlterado).isPresent();
        Assertions.assertThat(carrinhoAlterado.get().getProdutos()).hasSize(1);

        carrinhoRepository.deleteAll();
    }

    @AfterAll
    void limparBaseDeDados() {
        usuarioRepository.deleteAll();
        produtoCarrinhoRepository.deleteAll();
        produtoRepository.deleteAll();
    }
}
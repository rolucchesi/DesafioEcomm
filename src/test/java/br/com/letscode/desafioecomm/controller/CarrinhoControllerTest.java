package br.com.letscode.desafioecomm.controller;

import br.com.letscode.desafioecomm.dto.ProdutoCarrinhoDto;
import br.com.letscode.desafioecomm.dto.UsuarioDTO;
import br.com.letscode.desafioecomm.model.ProdutoEntity;
import br.com.letscode.desafioecomm.model.UsuarioEntity;
import br.com.letscode.desafioecomm.repository.CarrinhoRepository;
import br.com.letscode.desafioecomm.repository.ProdutoCarrinhoRepository;
import br.com.letscode.desafioecomm.repository.ProdutoRepository;
import br.com.letscode.desafioecomm.repository.UsuarioRepository;
import br.com.letscode.desafioecomm.service.CarrinhoService;
import br.com.letscode.desafioecomm.service.UsuarioService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CarrinhoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ProdutoCarrinhoRepository produtoCarrinhoRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    private UsuarioEntity criarUsuarioParaTeste() {
        UsuarioDTO usuario = new UsuarioDTO("lucas", "123", LocalDate.of(1991, 3, 24));
        return usuarioService.criarUsuario(usuario);
    }

    private ProdutoEntity criarProdutoParaTeste() {
        ProdutoEntity produto = new ProdutoEntity("Bola", "Bola redonda", BigDecimal.TEN, "1234", 1, "kg");
        return produtoRepository.save(produto);
    }

    @Test
    void deveListarTodosCarrinhos() throws Exception {
        criarUsuarioParaTeste();
        ProdutoEntity produto = criarProdutoParaTeste();
        ProdutoCarrinhoDto dto = new ProdutoCarrinhoDto(produto.getId(), 1);
        carrinhoService.adicionarProdutoAoCarrinho("lucas", dto);

        mockMvc.perform(get("/carrinho").with(httpBasic("lucas","123")))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void deveBuscarCarrinhoUsuario() throws Exception {
        criarUsuarioParaTeste();
        ProdutoEntity produto = criarProdutoParaTeste();
        ProdutoCarrinhoDto dto = new ProdutoCarrinhoDto(produto.getId(), 1);
        carrinhoService.adicionarProdutoAoCarrinho("lucas", dto);

        mockMvc.perform(get("/carrinho/lucas").with(httpBasic("lucas","123")))
                .andExpect(status().is(200));
    }

    @Test
    void deveAdicionarProdutoAoCarrinho() throws Exception {
        criarUsuarioParaTeste();
        ProdutoEntity produto = criarProdutoParaTeste();

        final String feedPayload = "{\n" +
                "  \"id\": " + produto.getId() + ",\n" +
                "  \"quantidade\": 1\n" +
                "}";

        mockMvc.perform(post("/carrinho/lucas").with(httpBasic("lucas","123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(feedPayload))
                .andExpect(status().is(202));
    }

    @Test
    void deveRemoverProdutoDoCarrinho() throws Exception {
        criarUsuarioParaTeste();
        ProdutoEntity produto = criarProdutoParaTeste();
        ProdutoCarrinhoDto dto = new ProdutoCarrinhoDto(produto.getId(), 1);
        carrinhoService.adicionarProdutoAoCarrinho("lucas", dto);

        mockMvc.perform(delete("/carrinho/lucas/" + produto.getId()).with(httpBasic("lucas","123")))
                .andExpect(status().is(202));
    }

    @AfterEach
    void limparBaseDeDados() {
        carrinhoRepository.deleteAll();
        produtoCarrinhoRepository.deleteAll();
        usuarioRepository.deleteAll();
        produtoRepository.deleteAll();
    }
}
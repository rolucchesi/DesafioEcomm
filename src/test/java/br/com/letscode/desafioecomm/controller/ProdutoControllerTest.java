package br.com.letscode.desafioecomm.controller;

import br.com.letscode.desafioecomm.dto.ProdutoDTO;
import br.com.letscode.desafioecomm.dto.UsuarioDTO;
import br.com.letscode.desafioecomm.model.ProdutoEntity;
import br.com.letscode.desafioecomm.repository.ProdutoRepository;
import br.com.letscode.desafioecomm.repository.UsuarioRepository;
import br.com.letscode.desafioecomm.service.ProdutoService;
import br.com.letscode.desafioecomm.service.UsuarioService;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @BeforeEach
    private void criarUsuarioParaTeste() {
        UsuarioDTO usuario = new UsuarioDTO("lucas", "123", LocalDate.of(1991, 3, 24));
        usuarioService.criarUsuario(usuario);
    }

    private ProdutoEntity criarProdutoParaTeste() {
        ProdutoDTO dto = new ProdutoDTO("Armario", "Armario de madeira", BigDecimal.TEN, "1234", 1, "kg");
        return produtoService.criarProduto(dto);
    }

    private static String getJsonAsString(final String path) throws IOException {
        final URL resource = ProdutoControllerTest.class.getResource("/jsons/" + path);
        return IOUtils.toString(resource, "UTF-8");
    }

    @Test
    void deveListarProdutos() throws Exception {
        criarProdutoParaTeste();

        mockMvc.perform(get("/produtos?offset=0&limit=10").with(httpBasic("lucas","123")))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    void criarProduto() throws Exception {
        final String feedPayload = getJsonAsString("criarProduto.json");

        mockMvc.perform(post("/produtos").with(httpBasic("lucas","123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(feedPayload))
                .andExpect(status().is(201));
    }

    @Test
    void deletarProduto() throws Exception {
        ProdutoEntity produto = criarProdutoParaTeste();

        mockMvc.perform(delete("/produtos/" + produto.getId()).with(httpBasic("lucas","123")))
                .andExpect(status().is(200));
    }

    @Test
    void editarProduto() throws Exception {
        ProdutoEntity produto = criarProdutoParaTeste();

        final String feedPayload = getJsonAsString("criarProduto.json");

        mockMvc.perform(put("/produtos/" + produto.getId()).with(httpBasic("lucas","123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(feedPayload))
                .andExpect(status().is(200));
    }

    @AfterEach
    void limparBaseDeDados() {
        produtoRepository.deleteAll();
        usuarioRepository.deleteAll();
    }
}
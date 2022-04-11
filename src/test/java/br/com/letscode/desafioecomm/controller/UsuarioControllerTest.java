package br.com.letscode.desafioecomm.controller;

import br.com.letscode.desafioecomm.dto.UsuarioDTO;
import br.com.letscode.desafioecomm.repository.UsuarioRepository;
import br.com.letscode.desafioecomm.service.UsuarioService;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    private static String getJsonAsString(final String path) throws IOException {
        final URL resource = UsuarioControllerTest.class.getResource("/jsons/" + path);
        return IOUtils.toString(resource, "UTF-8");
    }

    private void criarUsuarioParaTeste() {
        UsuarioDTO dto = new UsuarioDTO("lucas", "123", LocalDate.of(1991, 3, 24));
        usuarioService.criarUsuario(dto);
    }

    @Test
    void criarUsuario() throws Exception {
        final String feedPayload = getJsonAsString("cadastrarUsuario.json");

        mockMvc.perform(post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(feedPayload))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.nome", equalTo("lucas")));
    }

    @Test
    void listarUsuarios() throws Exception {
        criarUsuarioParaTeste();

        mockMvc.perform(get("/usuario"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("[0].nome", equalTo("lucas")));
    }

    @Test
    void alterarSenhaUsuario() throws Exception {
        criarUsuarioParaTeste();

        final String feedPayload = getJsonAsString("alterarSenhaUsuario.json");

        mockMvc.perform(put("/usuario/lucas").with(httpBasic("lucas","123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(feedPayload))
                .andExpect(status().is(202));
    }

    @Test
    void deletarUsuario() throws Exception {
        criarUsuarioParaTeste();

        mockMvc.perform(delete("/usuario/lucas").with(httpBasic("lucas","123")))
                .andExpect(status().is(200));
    }

    @AfterEach
    void limparBaseDeDados() {
        usuarioRepository.deleteAll();
    }
}
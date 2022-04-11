package br.com.letscode.desafioecomm.service;

import br.com.letscode.desafioecomm.dto.UsuarioDTO;
import br.com.letscode.desafioecomm.model.UsuarioEntity;
import br.com.letscode.desafioecomm.repository.UsuarioRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private void criarUsuarioParaTeste() {
        UsuarioDTO dto = new UsuarioDTO("lucas", "123", LocalDate.of(1991, 3, 24));
        usuarioService.criarUsuario(dto);
    }

    @Test
    void deveCriarUsuarioNovo() {
        criarUsuarioParaTeste();

        Optional<UsuarioEntity> usuarioSalvo = usuarioRepository.findByNome("lucas");

        Assertions.assertThat(usuarioSalvo).isPresent();
    }

    @Test
    void naoDeveCriarUsuarioComNomeRepetido() {
        criarUsuarioParaTeste();

        UsuarioDTO dto = new UsuarioDTO("lucas", "123", LocalDate.of(1991, 3, 24));

        Assertions.assertThatThrownBy(() -> {
            usuarioService.criarUsuario(dto);
        }).hasMessage("Já existe um usuário com este nome!");
    }

    @Test
    void deveBuscarUmUsuarioSalvo() {
        criarUsuarioParaTeste();

        UserDetails usuario = usuarioService.loadUserByUsername("lucas");

        Assertions.assertThat(usuario).isNotNull();
    }

    @Test
    void deveLancarExcecaoAoBuscarUmUsuarioNaoSalvo() {
        criarUsuarioParaTeste();

        Assertions.assertThatThrownBy(() -> {
            usuarioService.loadUserByUsername("usuario");
        }).hasMessage("Usuario não encontrado!");
    }

    @Test
    void deveDeletarUsuario() {
        criarUsuarioParaTeste();
        usuarioService.deletarUsuario("lucas");

        Optional<UsuarioEntity> usuario = usuarioRepository.findByNome("lucas");

        Assertions.assertThat(usuario).isEmpty();
    }

    @Test
    void naoDeveDeletarUsuarioQueNaoExiste() {
        criarUsuarioParaTeste();
        usuarioService.deletarUsuario("lucas");

        Optional<UsuarioEntity> usuario = usuarioRepository.findByNome("lucas");

        Assertions.assertThat(usuario).isEmpty();
    }

    @Test
    void deveAlterarSenhaDoUsuario() {
        criarUsuarioParaTeste();
        UsuarioEntity usuarioSalvo = usuarioRepository.findByNome("lucas").get();
        UsuarioDTO dto = new UsuarioDTO("lucas", "234", LocalDate.of(1991, 3, 24));

        usuarioService.alterarSenhaUsuario("lucas", dto, "lucas");

        UsuarioEntity usuarioSalvo2 = usuarioRepository.findByNome("lucas").get();

        Assertions.assertThat(usuarioSalvo.getSenha()).isNotEqualTo(usuarioSalvo2.getSenha());
    }

    @Test
    void naoDeveAlterarSenhaDoUsuarioPorOutroUsuario() {
        criarUsuarioParaTeste();
        UsuarioEntity usuarioSalvo = usuarioRepository.findByNome("lucas").get();
        UsuarioDTO dto = new UsuarioDTO("lucas", "234", LocalDate.of(1991, 3, 24));

        Assertions.assertThatThrownBy(() -> {
            usuarioService.alterarSenhaUsuario("lucas", dto, "rodrigo");
        }).hasMessage("Não é possível alterar a senha de outro usuário!");
    }

    @Test
    void naoDeveAlterarSenhaDoUsuarioComSenhaVazia() {
        criarUsuarioParaTeste();
        UsuarioDTO dto = new UsuarioDTO("lucas", "", LocalDate.of(1991, 3, 24));

        Assertions.assertThatThrownBy(() -> {
            usuarioService.alterarSenhaUsuario("lucas", dto, "lucas");
        }).hasMessage("A senha não poder ser vazia!");
    }

    @Test
    void naoDeveAlterarSenhaDoUsuarioQueNaoExiste() {
        criarUsuarioParaTeste();
        UsuarioDTO dto = new UsuarioDTO("lucas", "234", LocalDate.of(1991, 3, 24));

        Assertions.assertThatThrownBy(() -> {
            usuarioService.alterarSenhaUsuario("rodrigo", dto, "rodrigo");
        }).hasMessage("Não existe um usuário com este nome!");
    }

    @AfterEach
    void limparBaseDeDados() {
        usuarioRepository.deleteAll();
    }
}
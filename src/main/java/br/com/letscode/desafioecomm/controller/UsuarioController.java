package br.com.letscode.desafioecomm.controller;

import br.com.letscode.desafioecomm.dto.UsuarioDTO;
import br.com.letscode.desafioecomm.model.UsuarioEntity;
import br.com.letscode.desafioecomm.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioEntity usuario = usuarioService.criarUsuario(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<UsuarioEntity>> listarUsuarios() {
        List<UsuarioEntity> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{nome}")
    public ResponseEntity alterarSenhaUsuario(@PathVariable("nome") String nome,
                                         @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioEntity usuario = usuarioService.alterarSenhaUsuario(nome, usuarioDTO);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{nome}")
    public ResponseEntity<String> deletarUsuario(@PathVariable("nome") String nome) {
        usuarioService.deletarUsuario(nome);
        return ResponseEntity.ok("Usuário deletado com sucesso!");
    }

}

package br.com.letscode.desafioecomm.service;

import br.com.letscode.desafioecomm.dto.UsuarioDTO;
import br.com.letscode.desafioecomm.model.UsuarioDetalhe;
import br.com.letscode.desafioecomm.model.UsuarioEntity;
import br.com.letscode.desafioecomm.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UsuarioEntity usuario = usuarioRepository.findByNome(username)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Usuario não encontrado!");
                });

        return new UsuarioDetalhe(usuario);
    }

    public UsuarioEntity criarUsuario(UsuarioDTO usuarioDTO) {
        Optional<UsuarioEntity> usuarioSalvo = usuarioRepository.findByNome(usuarioDTO.getNome());

        if(usuarioSalvo.isPresent()) {
            throw new RuntimeException("Já existe um usuário com este nome!");
        }

        UsuarioEntity usuario = new UsuarioEntity(usuarioDTO.getNome(),
                passwordEncoder.encode(usuarioDTO.getSenha()),
                usuarioDTO.getDataNascimento());

        usuarioRepository.save(usuario);

        return usuario;
    }

    public UsuarioEntity alterarSenhaUsuario(String nome, UsuarioDTO usuarioDTO, String nomeAuth) {

        if(!nome.equals(nomeAuth)) {
            throw new RuntimeException("Não é possível alterar a senha de outro usuário!");
        }

        UsuarioEntity usuario = usuarioRepository.findByNome(nome)
                .orElseThrow(() -> {
                    throw new RuntimeException("Não existe um usuário com este nome!");
                });

        if(usuarioDTO.getSenha().isBlank()) {
            throw new RuntimeException("A senha não poder ser vazia!");
        }

        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        usuarioRepository.save(usuario);

        return usuario;
    }

    public void deletarUsuario(String nome) {
        UsuarioEntity usuario = usuarioRepository.findByNome(nome)
                .orElseThrow(() -> {
                    throw new RuntimeException("Não existe usuário com este nome!");
                });

        usuarioRepository.delete(usuario);
    }

    public List<UsuarioEntity> listarUsuarios() {
        return usuarioRepository.findAll();
    }

}

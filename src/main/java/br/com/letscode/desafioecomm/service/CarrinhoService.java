package br.com.letscode.desafioecomm.service;

import br.com.letscode.desafioecomm.dto.ProdutoCarrinhoDto;
import br.com.letscode.desafioecomm.model.CarrinhoEntity;
import br.com.letscode.desafioecomm.model.ProdutoCarrinhoEntity;
import br.com.letscode.desafioecomm.model.ProdutoEntity;
import br.com.letscode.desafioecomm.model.UsuarioEntity;
import br.com.letscode.desafioecomm.repository.CarrinhoRepository;
import br.com.letscode.desafioecomm.repository.ProdutoRepository;
import br.com.letscode.desafioecomm.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<CarrinhoEntity> listarTodosCarrinhos() {
        return carrinhoRepository.findAll();
    }

    public CarrinhoEntity buscarCarrinhoPorNome(final String nome) {
        UsuarioEntity usuario = usuarioRepository.findByNome(nome).orElseThrow(() -> {
            throw new RuntimeException("Não existe um usuário com este nome!");
        });

        return carrinhoRepository.findByUsuario(usuario).orElseThrow(() -> {
            throw new RuntimeException("Este usuário ainda não possui um carrinho!");
        });
    }

    public CarrinhoEntity adicionarProdutoAoCarrinho(final String nome, final ProdutoCarrinhoDto dto) {
        //TODO adicionar verificação do usuário?
//        if(!authNome.equals(nome)) {
//            throw new RuntimeException("Você não pode adicionar um produto em um carrinho de outra pessoa!");
//        }

        UsuarioEntity usuario = usuarioRepository.findByNome(nome).orElseThrow(() -> {
            throw new RuntimeException("Não existe um usuário com este nome!");
        });

        CarrinhoEntity carrinho = carrinhoRepository.findByUsuario(usuario).orElse(new CarrinhoEntity(usuario));

        ProdutoEntity produto = produtoRepository.findById(dto.getIdProduto()).orElseThrow(() -> {
            throw new RuntimeException("Não existe produto com este ID!");
        });//TODO buscar o produto pelo ID é a melhor forma?

        carrinho.getProdutos().add(new ProdutoCarrinhoEntity(produto, dto.getQuantidade()));

        return carrinhoRepository.save(carrinho);
    }

    public void removerProdutoDoCarrinho(final String nome, final Long idProduto) {
        //TODO adicionar verificação do usuário?

        UsuarioEntity usuario = usuarioRepository.findByNome(nome).orElseThrow(() -> {
            throw new RuntimeException("Não existe um usuário com este nome!");
        });

        CarrinhoEntity carrinho = carrinhoRepository.findByUsuario(usuario).orElseThrow(() -> {
            throw new RuntimeException("Este usuário ainda não possui um carrinho!");
        });

        produtoRepository.findById(idProduto).orElseThrow(() -> {
            throw new RuntimeException("Não existe produto com este ID!");
        });

        List<ProdutoCarrinhoEntity> novaLista = carrinho.getProdutos()
                .stream()
                .filter(produtos -> !produtos.getId().equals(idProduto))
                .collect(Collectors.toList());

        carrinho.setProdutos(novaLista);
        carrinhoRepository.save(carrinho);
    }
}

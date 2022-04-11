package br.com.letscode.desafioecomm.service;

import br.com.letscode.desafioecomm.dto.ProdutoDTO;
import br.com.letscode.desafioecomm.enums.ProdutoStatus;
import br.com.letscode.desafioecomm.model.OffsetLimitPageable;
import br.com.letscode.desafioecomm.model.ProdutoEntity;
import br.com.letscode.desafioecomm.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;

    public Page<ProdutoEntity> buscarTodos(Integer offset,
                                           Integer limit) {
        Pageable pageable = new OffsetLimitPageable(offset, limit);
        return produtoRepository.findAll(pageable);
    }

    public ProdutoEntity buscarPorId(Long id){
        return produtoRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Não existe um produto com este id!");
        });

//        return produto;
    }

    public ProdutoEntity buscarPorCodigoBarra(String codigoBarra){
        return produtoRepository.findByCodigoBarra(codigoBarra);
    }

    public ProdutoEntity criarProduto(ProdutoDTO produtoDTO){
        //TODO implementar exception para o sistema
        ProdutoEntity produtoEntity = new ProdutoEntity(produtoDTO);
        return produtoRepository.save(produtoEntity);
    }

    public void deletarProduto(Long id){
        ProdutoEntity produtoEntity = buscarPorId(id);
        produtoRepository.delete(produtoEntity);
//        return null;
    }

    public ProdutoEntity editarProduto(Long id, ProdutoDTO produtoDTO){

        ProdutoEntity produtoEntity = buscarPorId(id);

        produtoEntity.setNome(produtoDTO.getNome());
        produtoEntity.setDescricao(produtoDTO.getDescricao());
        produtoEntity.setValor(produtoDTO.getValor());
        produtoEntity.setCodigoBarra(produtoDTO.getCodigoBarra());
        produtoEntity.setStatus(ProdutoStatus.ATIVO);
        produtoEntity.setPeso(produtoDTO.getPeso());
        produtoEntity.setPesoUnidadeMedida(produtoDTO.getPesoUnidadeMedida());
        produtoEntity.setDataAtualizacao(ZonedDateTime.now());
        return produtoRepository.save(produtoEntity);
    }

}

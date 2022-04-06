package br.com.letscode.desafioecomm.service;

import br.com.letscode.desafioecomm.dto.ProdutoDTO;
import br.com.letscode.desafioecomm.model.OffsetLimitPageable;
import br.com.letscode.desafioecomm.model.ProdutoEntity;
import br.com.letscode.desafioecomm.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        return produtoRepository.findById(id).get();//TODO adicionar tratativa para optional empty
    }

    public ProdutoEntity buscarPorCodigoBarra(String codigoBarra){
        return produtoRepository.findByCodigoBarra(codigoBarra);
    }

    public ProdutoEntity criar(ProdutoDTO produtoDTO){

        //valida descricao, sanitizacao... retira HTML, scripts de campos texto. pode ser na view.
//        Optional<FabricanteEntity> fabricanteEntity = fabricanteRepository.findById(produtoRequest.getIdFabricante());
        //TODO implementar exception para o sistema
        ProdutoEntity produtoEntity = new ProdutoEntity(produtoDTO);

        return produtoRepository.save(produtoEntity);
    }

}

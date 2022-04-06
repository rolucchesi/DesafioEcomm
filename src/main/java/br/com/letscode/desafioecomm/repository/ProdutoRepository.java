package br.com.letscode.desafioecomm.repository;

import br.com.letscode.desafioecomm.model.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity,Long>{

    @Query(value = "SELECT * FROM PRODUTO where codigo_barra = ?1", nativeQuery = true)
    ProdutoEntity findByCodigoBarra(String codigoBarra);

}

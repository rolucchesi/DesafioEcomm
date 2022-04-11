package br.com.letscode.desafioecomm.repository;

import br.com.letscode.desafioecomm.model.ProdutoCarrinhoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoCarrinhoRepository extends JpaRepository<ProdutoCarrinhoEntity, Long> {
}

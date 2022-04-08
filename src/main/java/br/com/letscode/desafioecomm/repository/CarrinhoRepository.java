package br.com.letscode.desafioecomm.repository;

import br.com.letscode.desafioecomm.model.CarrinhoEntity;
import br.com.letscode.desafioecomm.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarrinhoRepository extends JpaRepository<CarrinhoEntity, Long> {

    Optional<CarrinhoEntity> findByUsuario(UsuarioEntity usuario);

}

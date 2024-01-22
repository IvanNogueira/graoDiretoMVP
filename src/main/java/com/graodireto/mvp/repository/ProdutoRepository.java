package com.graodireto.mvp.repository;

import com.graodireto.mvp.domain.Estabelecimento;
import com.graodireto.mvp.domain.Produto;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Produto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {
    @Query(
        "SELECT p FROM Produto p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :pesquisar, '%')) OR LOWER(p.descricao) LIKE LOWER(CONCAT('%', :pesquisar, '%'))"
    )
    List<Produto> findByNomeOrDescricaoProduto(@Param("pesquisar") String pesquisar);
}

package com.graodireto.mvp.repository;

import com.graodireto.mvp.domain.CategoriaProduto;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CategoriaProduto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long>, JpaSpecificationExecutor<CategoriaProduto> {
    @Query(
        "SELECT cp FROM CategoriaProduto cp " +
        "JOIN cp.produtos p " +
        "JOIN p.cardapio c " +
        "JOIN c.estabelecimento e " +
        "WHERE e.user.id = :userId"
    )
    List<CategoriaProduto> findCategoriasByUserId(@Param("userId") Long userId);
}

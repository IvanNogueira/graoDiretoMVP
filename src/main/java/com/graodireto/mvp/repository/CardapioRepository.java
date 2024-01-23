package com.graodireto.mvp.repository;

import com.graodireto.mvp.domain.Cardapio;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cardapio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CardapioRepository extends JpaRepository<Cardapio, Long>, JpaSpecificationExecutor<Cardapio> {
    @Query("SELECT c FROM Cardapio c JOIN c.estabelecimento e WHERE e.user.id = :userId")
    List<Cardapio> findCardapiosByUserId(@Param("userId") Long userId);
}

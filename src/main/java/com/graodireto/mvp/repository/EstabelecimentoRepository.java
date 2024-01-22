package com.graodireto.mvp.repository;

import com.graodireto.mvp.domain.Estabelecimento;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Estabelecimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long>, JpaSpecificationExecutor<Estabelecimento> {
    @Query("SELECT e FROM Estabelecimento e WHERE LOWER(e.nome) LIKE LOWER(CONCAT('%', :pesquisar, '%'))")
    List<Estabelecimento> findByNomeContaining(@Param("pesquisar") String pesquisar);
}

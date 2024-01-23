package com.graodireto.mvp.repository;

import com.graodireto.mvp.domain.CupomDesconto;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CupomDesconto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CupomDescontoRepository extends JpaRepository<CupomDesconto, Long>, JpaSpecificationExecutor<CupomDesconto> {
    @Query("SELECT cd FROM CupomDesconto cd " + "JOIN cd.estabelecimento e " + "WHERE e.user.id = :userId")
    List<CupomDesconto> findCuponsDescontoByUserId(@Param("userId") Long userId);
}

package com.graodireto.mvp.repository;

import com.graodireto.mvp.domain.CupomDesconto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CupomDesconto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CupomDescontoRepository extends JpaRepository<CupomDesconto, Long>, JpaSpecificationExecutor<CupomDesconto> {}

package com.graodireto.mvp.repository;

import com.graodireto.mvp.domain.Estabelecimento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Estabelecimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long>, JpaSpecificationExecutor<Estabelecimento> {}

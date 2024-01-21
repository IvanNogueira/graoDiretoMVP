package com.graodireto.mvp.repository;

import com.graodireto.mvp.domain.Imagens;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Imagens entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImagensRepository extends JpaRepository<Imagens, Long>, JpaSpecificationExecutor<Imagens> {}

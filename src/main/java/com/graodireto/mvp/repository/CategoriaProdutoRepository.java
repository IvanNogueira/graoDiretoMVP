package com.graodireto.mvp.repository;

import com.graodireto.mvp.domain.CategoriaProduto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CategoriaProduto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long>, JpaSpecificationExecutor<CategoriaProduto> {}

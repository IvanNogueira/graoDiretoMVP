package com.graodireto.mvp.service;

import com.graodireto.mvp.domain.CategoriaProduto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.graodireto.mvp.domain.CategoriaProduto}.
 */
public interface CategoriaProdutoService {
    /**
     * Save a categoriaProduto.
     *
     * @param categoriaProduto the entity to save.
     * @return the persisted entity.
     */
    CategoriaProduto save(CategoriaProduto categoriaProduto);

    /**
     * Updates a categoriaProduto.
     *
     * @param categoriaProduto the entity to update.
     * @return the persisted entity.
     */
    CategoriaProduto update(CategoriaProduto categoriaProduto);

    /**
     * Partially updates a categoriaProduto.
     *
     * @param categoriaProduto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategoriaProduto> partialUpdate(CategoriaProduto categoriaProduto);

    /**
     * Get all the categoriaProdutos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoriaProduto> findAll(Pageable pageable);

    /**
     * Get the "id" categoriaProduto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoriaProduto> findOne(Long id);

    /**
     * Delete the "id" categoriaProduto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

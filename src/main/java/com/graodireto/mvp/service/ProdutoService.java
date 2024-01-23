package com.graodireto.mvp.service;

import com.graodireto.mvp.domain.Produto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.graodireto.mvp.domain.Produto}.
 */
public interface ProdutoService {
    /**
     * Save a produto.
     *
     * @param produto the entity to save.
     * @return the persisted entity.
     */
    Produto save(Produto produto);

    /**
     * Updates a produto.
     *
     * @param produto the entity to update.
     * @return the persisted entity.
     */
    Produto update(Produto produto);

    /**
     * Partially updates a produto.
     *
     * @param produto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Produto> partialUpdate(Produto produto);

    /**
     * Get all the produtos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Produto> findAll(Pageable pageable);

    List<Produto> findProdutosByUserId(Long userId);

    /**
     * Get the "id" produto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Produto> findOne(Long id);

    /**
     * Delete the "id" produto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

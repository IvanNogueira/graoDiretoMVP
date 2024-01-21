package com.graodireto.mvp.service;

import com.graodireto.mvp.domain.Cidade;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.graodireto.mvp.domain.Cidade}.
 */
public interface CidadeService {
    /**
     * Save a cidade.
     *
     * @param cidade the entity to save.
     * @return the persisted entity.
     */
    Cidade save(Cidade cidade);

    /**
     * Updates a cidade.
     *
     * @param cidade the entity to update.
     * @return the persisted entity.
     */
    Cidade update(Cidade cidade);

    /**
     * Partially updates a cidade.
     *
     * @param cidade the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Cidade> partialUpdate(Cidade cidade);

    /**
     * Get all the cidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Cidade> findAll(Pageable pageable);

    /**
     * Get the "id" cidade.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Cidade> findOne(Long id);

    /**
     * Delete the "id" cidade.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

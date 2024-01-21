package com.graodireto.mvp.service;

import com.graodireto.mvp.domain.Imagens;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.graodireto.mvp.domain.Imagens}.
 */
public interface ImagensService {
    /**
     * Save a imagens.
     *
     * @param imagens the entity to save.
     * @return the persisted entity.
     */
    Imagens save(Imagens imagens);

    /**
     * Updates a imagens.
     *
     * @param imagens the entity to update.
     * @return the persisted entity.
     */
    Imagens update(Imagens imagens);

    /**
     * Partially updates a imagens.
     *
     * @param imagens the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Imagens> partialUpdate(Imagens imagens);

    /**
     * Get all the imagens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Imagens> findAll(Pageable pageable);

    /**
     * Get the "id" imagens.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Imagens> findOne(Long id);

    /**
     * Delete the "id" imagens.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

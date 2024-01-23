package com.graodireto.mvp.service;

import com.graodireto.mvp.domain.Cardapio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.graodireto.mvp.domain.Cardapio}.
 */
public interface CardapioService {
    /**
     * Save a cardapio.
     *
     * @param cardapio the entity to save.
     * @return the persisted entity.
     */
    Cardapio save(Cardapio cardapio);

    /**
     * Updates a cardapio.
     *
     * @param cardapio the entity to update.
     * @return the persisted entity.
     */
    Cardapio update(Cardapio cardapio);

    /**
     * Partially updates a cardapio.
     *
     * @param cardapio the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Cardapio> partialUpdate(Cardapio cardapio);

    /**
     * Get all the cardapios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Cardapio> findAll(Pageable pageable);

    List<Cardapio> findAllUserEstabelicimento(Long userId);

    /**
     * Get the "id" cardapio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Cardapio> findOne(Long id);

    /**
     * Delete the "id" cardapio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

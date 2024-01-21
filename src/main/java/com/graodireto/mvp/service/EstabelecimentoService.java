package com.graodireto.mvp.service;

import com.graodireto.mvp.domain.Estabelecimento;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.graodireto.mvp.domain.Estabelecimento}.
 */
public interface EstabelecimentoService {
    /**
     * Save a estabelecimento.
     *
     * @param estabelecimento the entity to save.
     * @return the persisted entity.
     */
    Estabelecimento save(Estabelecimento estabelecimento);

    /**
     * Updates a estabelecimento.
     *
     * @param estabelecimento the entity to update.
     * @return the persisted entity.
     */
    Estabelecimento update(Estabelecimento estabelecimento);

    /**
     * Partially updates a estabelecimento.
     *
     * @param estabelecimento the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Estabelecimento> partialUpdate(Estabelecimento estabelecimento);

    /**
     * Get all the estabelecimentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Estabelecimento> findAll(Pageable pageable);

    /**
     * Get the "id" estabelecimento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Estabelecimento> findOne(Long id);

    /**
     * Delete the "id" estabelecimento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

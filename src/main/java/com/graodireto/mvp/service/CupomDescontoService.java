package com.graodireto.mvp.service;

import com.graodireto.mvp.domain.CupomDesconto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.graodireto.mvp.domain.CupomDesconto}.
 */
public interface CupomDescontoService {
    /**
     * Save a cupomDesconto.
     *
     * @param cupomDesconto the entity to save.
     * @return the persisted entity.
     */
    CupomDesconto save(CupomDesconto cupomDesconto);

    /**
     * Updates a cupomDesconto.
     *
     * @param cupomDesconto the entity to update.
     * @return the persisted entity.
     */
    CupomDesconto update(CupomDesconto cupomDesconto);

    /**
     * Partially updates a cupomDesconto.
     *
     * @param cupomDesconto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CupomDesconto> partialUpdate(CupomDesconto cupomDesconto);

    /**
     * Get all the cupomDescontos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CupomDesconto> findAll(Pageable pageable);

    /**
     * Get the "id" cupomDesconto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CupomDesconto> findOne(Long id);

    /**
     * Delete the "id" cupomDesconto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

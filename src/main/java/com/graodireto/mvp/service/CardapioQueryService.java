package com.graodireto.mvp.service;

import com.graodireto.mvp.domain.*; // for static metamodels
import com.graodireto.mvp.domain.Cardapio;
import com.graodireto.mvp.repository.CardapioRepository;
import com.graodireto.mvp.service.criteria.CardapioCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Cardapio} entities in the database.
 * The main input is a {@link CardapioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Cardapio} or a {@link Page} of {@link Cardapio} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CardapioQueryService extends QueryService<Cardapio> {

    private final Logger log = LoggerFactory.getLogger(CardapioQueryService.class);

    private final CardapioRepository cardapioRepository;

    public CardapioQueryService(CardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }

    /**
     * Return a {@link List} of {@link Cardapio} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Cardapio> findByCriteria(CardapioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cardapio> specification = createSpecification(criteria);
        return cardapioRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Cardapio} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Cardapio> findByCriteria(CardapioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cardapio> specification = createSpecification(criteria);
        return cardapioRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CardapioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cardapio> specification = createSpecification(criteria);
        return cardapioRepository.count(specification);
    }

    /**
     * Function to convert {@link CardapioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cardapio> createSpecification(CardapioCriteria criteria) {
        Specification<Cardapio> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Cardapio_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Cardapio_.nome));
            }
            if (criteria.getDataCriacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataCriacao(), Cardapio_.dataCriacao));
            }
            if (criteria.getAtivo() != null) {
                specification = specification.and(buildSpecification(criteria.getAtivo(), Cardapio_.ativo));
            }
            if (criteria.getProdutoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProdutoId(), root -> root.join(Cardapio_.produtos, JoinType.LEFT).get(Produto_.id))
                    );
            }
            if (criteria.getEstabelecimentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEstabelecimentoId(),
                            root -> root.join(Cardapio_.estabelecimento, JoinType.INNER).get(Estabelecimento_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

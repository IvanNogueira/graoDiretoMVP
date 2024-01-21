package com.graodireto.mvp.service;

import com.graodireto.mvp.domain.*; // for static metamodels
import com.graodireto.mvp.domain.CupomDesconto;
import com.graodireto.mvp.repository.CupomDescontoRepository;
import com.graodireto.mvp.service.criteria.CupomDescontoCriteria;
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
 * Service for executing complex queries for {@link CupomDesconto} entities in the database.
 * The main input is a {@link CupomDescontoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CupomDesconto} or a {@link Page} of {@link CupomDesconto} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CupomDescontoQueryService extends QueryService<CupomDesconto> {

    private final Logger log = LoggerFactory.getLogger(CupomDescontoQueryService.class);

    private final CupomDescontoRepository cupomDescontoRepository;

    public CupomDescontoQueryService(CupomDescontoRepository cupomDescontoRepository) {
        this.cupomDescontoRepository = cupomDescontoRepository;
    }

    /**
     * Return a {@link List} of {@link CupomDesconto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CupomDesconto> findByCriteria(CupomDescontoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CupomDesconto> specification = createSpecification(criteria);
        return cupomDescontoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CupomDesconto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CupomDesconto> findByCriteria(CupomDescontoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CupomDesconto> specification = createSpecification(criteria);
        return cupomDescontoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CupomDescontoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CupomDesconto> specification = createSpecification(criteria);
        return cupomDescontoRepository.count(specification);
    }

    /**
     * Function to convert {@link CupomDescontoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CupomDesconto> createSpecification(CupomDescontoCriteria criteria) {
        Specification<CupomDesconto> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CupomDesconto_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), CupomDesconto_.nome));
            }
            if (criteria.getValorDesconto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorDesconto(), CupomDesconto_.valorDesconto));
            }
            if (criteria.getValorMinimo() != null) {
                specification = specification.and(buildSpecification(criteria.getValorMinimo(), CupomDesconto_.valorMinimo));
            }
            if (criteria.getValorMinimoRegra() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorMinimoRegra(), CupomDesconto_.valorMinimoRegra));
            }
            if (criteria.getDescricaoRegras() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricaoRegras(), CupomDesconto_.descricaoRegras));
            }
            if (criteria.getValido() != null) {
                specification = specification.and(buildSpecification(criteria.getValido(), CupomDesconto_.valido));
            }
            if (criteria.getEstabelecimentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEstabelecimentoId(),
                            root -> root.join(CupomDesconto_.estabelecimento, JoinType.INNER).get(Estabelecimento_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

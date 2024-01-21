package com.graodireto.mvp.service;

import com.graodireto.mvp.domain.*; // for static metamodels
import com.graodireto.mvp.domain.CategoriaProduto;
import com.graodireto.mvp.repository.CategoriaProdutoRepository;
import com.graodireto.mvp.service.criteria.CategoriaProdutoCriteria;
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
 * Service for executing complex queries for {@link CategoriaProduto} entities in the database.
 * The main input is a {@link CategoriaProdutoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategoriaProduto} or a {@link Page} of {@link CategoriaProduto} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoriaProdutoQueryService extends QueryService<CategoriaProduto> {

    private final Logger log = LoggerFactory.getLogger(CategoriaProdutoQueryService.class);

    private final CategoriaProdutoRepository categoriaProdutoRepository;

    public CategoriaProdutoQueryService(CategoriaProdutoRepository categoriaProdutoRepository) {
        this.categoriaProdutoRepository = categoriaProdutoRepository;
    }

    /**
     * Return a {@link List} of {@link CategoriaProduto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategoriaProduto> findByCriteria(CategoriaProdutoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CategoriaProduto> specification = createSpecification(criteria);
        return categoriaProdutoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CategoriaProduto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaProduto> findByCriteria(CategoriaProdutoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CategoriaProduto> specification = createSpecification(criteria);
        return categoriaProdutoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoriaProdutoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CategoriaProduto> specification = createSpecification(criteria);
        return categoriaProdutoRepository.count(specification);
    }

    /**
     * Function to convert {@link CategoriaProdutoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CategoriaProduto> createSpecification(CategoriaProdutoCriteria criteria) {
        Specification<CategoriaProduto> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CategoriaProduto_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), CategoriaProduto_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), CategoriaProduto_.descricao));
            }
        }
        return specification;
    }
}

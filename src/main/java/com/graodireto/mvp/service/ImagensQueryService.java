package com.graodireto.mvp.service;

import com.graodireto.mvp.domain.*; // for static metamodels
import com.graodireto.mvp.domain.Imagens;
import com.graodireto.mvp.repository.ImagensRepository;
import com.graodireto.mvp.service.criteria.ImagensCriteria;
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
 * Service for executing complex queries for {@link Imagens} entities in the database.
 * The main input is a {@link ImagensCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Imagens} or a {@link Page} of {@link Imagens} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ImagensQueryService extends QueryService<Imagens> {

    private final Logger log = LoggerFactory.getLogger(ImagensQueryService.class);

    private final ImagensRepository imagensRepository;

    public ImagensQueryService(ImagensRepository imagensRepository) {
        this.imagensRepository = imagensRepository;
    }

    /**
     * Return a {@link List} of {@link Imagens} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Imagens> findByCriteria(ImagensCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Imagens> specification = createSpecification(criteria);
        return imagensRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Imagens} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Imagens> findByCriteria(ImagensCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Imagens> specification = createSpecification(criteria);
        return imagensRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ImagensCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Imagens> specification = createSpecification(criteria);
        return imagensRepository.count(specification);
    }

    /**
     * Function to convert {@link ImagensCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Imagens> createSpecification(ImagensCriteria criteria) {
        Specification<Imagens> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Imagens_.id));
            }
            if (criteria.getImagemContentType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImagemContentType(), Imagens_.imagemContentType));
            }
            if (criteria.getLocalImagem() != null) {
                specification = specification.and(buildSpecification(criteria.getLocalImagem(), Imagens_.localImagem));
            }
            if (criteria.getEstabelecimentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEstabelecimentoId(),
                            root -> root.join(Imagens_.estabelecimento, JoinType.LEFT).get(Estabelecimento_.id)
                        )
                    );
            }
            if (criteria.getProdutoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProdutoId(), root -> root.join(Imagens_.produto, JoinType.LEFT).get(Produto_.id))
                    );
            }
        }
        return specification;
    }
}

package com.graodireto.mvp.service;

import com.graodireto.mvp.domain.*; // for static metamodels
import com.graodireto.mvp.domain.Estabelecimento;
import com.graodireto.mvp.repository.EstabelecimentoRepository;
import com.graodireto.mvp.service.criteria.EstabelecimentoCriteria;
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
 * Service for executing complex queries for {@link Estabelecimento} entities in the database.
 * The main input is a {@link EstabelecimentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Estabelecimento} or a {@link Page} of {@link Estabelecimento} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EstabelecimentoQueryService extends QueryService<Estabelecimento> {

    private final Logger log = LoggerFactory.getLogger(EstabelecimentoQueryService.class);

    private final EstabelecimentoRepository estabelecimentoRepository;

    public EstabelecimentoQueryService(EstabelecimentoRepository estabelecimentoRepository) {
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    /**
     * Return a {@link List} of {@link Estabelecimento} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Estabelecimento> findByCriteria(EstabelecimentoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Estabelecimento> specification = createSpecification(criteria);
        return estabelecimentoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Estabelecimento} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Estabelecimento> findByCriteria(EstabelecimentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Estabelecimento> specification = createSpecification(criteria);
        return estabelecimentoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EstabelecimentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Estabelecimento> specification = createSpecification(criteria);
        return estabelecimentoRepository.count(specification);
    }

    /**
     * Function to convert {@link EstabelecimentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Estabelecimento> createSpecification(EstabelecimentoCriteria criteria) {
        Specification<Estabelecimento> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Estabelecimento_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Estabelecimento_.nome));
            }
            if (criteria.getTelefone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefone(), Estabelecimento_.telefone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Estabelecimento_.email));
            }
            if (criteria.getTipoEstabelecimento() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getTipoEstabelecimento(), Estabelecimento_.tipoEstabelecimento));
            }
            if (criteria.getCriadoEm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCriadoEm(), Estabelecimento_.criadoEm));
            }
            if (criteria.getLogradouro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogradouro(), Estabelecimento_.logradouro));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), Estabelecimento_.numero));
            }
            if (criteria.getComplemento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComplemento(), Estabelecimento_.complemento));
            }
            if (criteria.getBairro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBairro(), Estabelecimento_.bairro));
            }
            if (criteria.getCep() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCep(), Estabelecimento_.cep));
            }
            if (criteria.getCardapioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCardapioId(),
                            root -> root.join(Estabelecimento_.cardapios, JoinType.LEFT).get(Cardapio_.id)
                        )
                    );
            }
            if (criteria.getCupomDescontoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCupomDescontoId(),
                            root -> root.join(Estabelecimento_.cupomDescontos, JoinType.LEFT).get(CupomDesconto_.id)
                        )
                    );
            }
            if (criteria.getCidadeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCidadeId(),
                            root -> root.join(Estabelecimento_.cidade, JoinType.LEFT).get(Cidade_.id)
                        )
                    );
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(Estabelecimento_.user, JoinType.INNER).get(User_.id))
                    );
            }
        }
        return specification;
    }
}

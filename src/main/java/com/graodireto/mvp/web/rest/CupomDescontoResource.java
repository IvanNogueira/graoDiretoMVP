package com.graodireto.mvp.web.rest;

import com.graodireto.mvp.domain.CategoriaProduto;
import com.graodireto.mvp.domain.CupomDesconto;
import com.graodireto.mvp.repository.CupomDescontoRepository;
import com.graodireto.mvp.security.SecurityUtils;
import com.graodireto.mvp.service.CupomDescontoQueryService;
import com.graodireto.mvp.service.CupomDescontoService;
import com.graodireto.mvp.service.criteria.CupomDescontoCriteria;
import com.graodireto.mvp.service.dto.AdminUserDTO;
import com.graodireto.mvp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.graodireto.mvp.domain.CupomDesconto}.
 */
@RestController
@RequestMapping("/api/cupom-descontos")
public class CupomDescontoResource {

    private final Logger log = LoggerFactory.getLogger(CupomDescontoResource.class);

    private static final String ENTITY_NAME = "cupomDesconto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CupomDescontoService cupomDescontoService;

    private final CupomDescontoRepository cupomDescontoRepository;

    private final CupomDescontoQueryService cupomDescontoQueryService;

    private final AccountResource accountResource;

    public CupomDescontoResource(
        CupomDescontoService cupomDescontoService,
        CupomDescontoRepository cupomDescontoRepository,
        CupomDescontoQueryService cupomDescontoQueryService,
        AccountResource accountResource
    ) {
        this.cupomDescontoService = cupomDescontoService;
        this.cupomDescontoRepository = cupomDescontoRepository;
        this.cupomDescontoQueryService = cupomDescontoQueryService;
        this.accountResource = accountResource;
    }

    /**
     * {@code POST  /cupom-descontos} : Create a new cupomDesconto.
     *
     * @param cupomDesconto the cupomDesconto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cupomDesconto, or with status {@code 400 (Bad Request)} if the cupomDesconto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CupomDesconto> createCupomDesconto(@RequestBody CupomDesconto cupomDesconto) throws URISyntaxException {
        log.debug("REST request to save CupomDesconto : {}", cupomDesconto);
        if (cupomDesconto.getId() != null) {
            throw new BadRequestAlertException("A new cupomDesconto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CupomDesconto result = cupomDescontoService.save(cupomDesconto);
        return ResponseEntity
            .created(new URI("/api/cupom-descontos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cupom-descontos/:id} : Updates an existing cupomDesconto.
     *
     * @param id the id of the cupomDesconto to save.
     * @param cupomDesconto the cupomDesconto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cupomDesconto,
     * or with status {@code 400 (Bad Request)} if the cupomDesconto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cupomDesconto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CupomDesconto> updateCupomDesconto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CupomDesconto cupomDesconto
    ) throws URISyntaxException {
        log.debug("REST request to update CupomDesconto : {}, {}", id, cupomDesconto);
        if (cupomDesconto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cupomDesconto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cupomDescontoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CupomDesconto result = cupomDescontoService.update(cupomDesconto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cupomDesconto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cupom-descontos/:id} : Partial updates given fields of an existing cupomDesconto, field will ignore if it is null
     *
     * @param id the id of the cupomDesconto to save.
     * @param cupomDesconto the cupomDesconto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cupomDesconto,
     * or with status {@code 400 (Bad Request)} if the cupomDesconto is not valid,
     * or with status {@code 404 (Not Found)} if the cupomDesconto is not found,
     * or with status {@code 500 (Internal Server Error)} if the cupomDesconto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CupomDesconto> partialUpdateCupomDesconto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CupomDesconto cupomDesconto
    ) throws URISyntaxException {
        log.debug("REST request to partial update CupomDesconto partially : {}, {}", id, cupomDesconto);
        if (cupomDesconto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cupomDesconto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cupomDescontoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CupomDesconto> result = cupomDescontoService.partialUpdate(cupomDesconto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cupomDesconto.getId().toString())
        );
    }

    /**
     * {@code GET  /cupom-descontos} : get all the cupomDescontos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cupomDescontos in body.
     */
    @GetMapping("/user")
    public ResponseEntity<List<CupomDesconto>> getAllCupomDescontos(
        CupomDescontoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CupomDescontos by criteria: {}", criteria);

        Page<CupomDesconto> page = cupomDescontoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cupom-descontos} : get all the cupomDescontos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cupomDescontos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CupomDesconto>> getAllCupomDescontosUser(
        CupomDescontoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CupomDescontos by criteria: {}", criteria);
        AdminUserDTO user = accountResource.getAccount();

        if (SecurityUtils.hasCurrentUserAnyOfAuthorities("ROLE_ADMIN")) {
            Page<CupomDesconto> page = cupomDescontoQueryService.findByCriteria(criteria, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } else {
            List<CupomDesconto> cupomDesconto = cupomDescontoService.findCuponsDescontoByUserId(user.getId());
            return ResponseEntity.ok().body(cupomDesconto);
        }
    }

    /**
     * {@code GET  /cupom-descontos/count} : count all the cupomDescontos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCupomDescontos(CupomDescontoCriteria criteria) {
        log.debug("REST request to count CupomDescontos by criteria: {}", criteria);
        return ResponseEntity.ok().body(cupomDescontoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cupom-descontos/:id} : get the "id" cupomDesconto.
     *
     * @param id the id of the cupomDesconto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cupomDesconto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CupomDesconto> getCupomDesconto(@PathVariable("id") Long id) {
        log.debug("REST request to get CupomDesconto : {}", id);
        Optional<CupomDesconto> cupomDesconto = cupomDescontoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cupomDesconto);
    }

    /**
     * {@code DELETE  /cupom-descontos/:id} : delete the "id" cupomDesconto.
     *
     * @param id the id of the cupomDesconto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCupomDesconto(@PathVariable("id") Long id) {
        log.debug("REST request to delete CupomDesconto : {}", id);
        cupomDescontoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

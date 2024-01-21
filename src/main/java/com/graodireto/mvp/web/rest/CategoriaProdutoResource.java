package com.graodireto.mvp.web.rest;

import com.graodireto.mvp.domain.CategoriaProduto;
import com.graodireto.mvp.repository.CategoriaProdutoRepository;
import com.graodireto.mvp.service.CategoriaProdutoQueryService;
import com.graodireto.mvp.service.CategoriaProdutoService;
import com.graodireto.mvp.service.criteria.CategoriaProdutoCriteria;
import com.graodireto.mvp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.graodireto.mvp.domain.CategoriaProduto}.
 */
@RestController
@RequestMapping("/api/categoria-produtos")
public class CategoriaProdutoResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaProdutoResource.class);

    private static final String ENTITY_NAME = "categoriaProduto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriaProdutoService categoriaProdutoService;

    private final CategoriaProdutoRepository categoriaProdutoRepository;

    private final CategoriaProdutoQueryService categoriaProdutoQueryService;

    public CategoriaProdutoResource(
        CategoriaProdutoService categoriaProdutoService,
        CategoriaProdutoRepository categoriaProdutoRepository,
        CategoriaProdutoQueryService categoriaProdutoQueryService
    ) {
        this.categoriaProdutoService = categoriaProdutoService;
        this.categoriaProdutoRepository = categoriaProdutoRepository;
        this.categoriaProdutoQueryService = categoriaProdutoQueryService;
    }

    /**
     * {@code POST  /categoria-produtos} : Create a new categoriaProduto.
     *
     * @param categoriaProduto the categoriaProduto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoriaProduto, or with status {@code 400 (Bad Request)} if the categoriaProduto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CategoriaProduto> createCategoriaProduto(@Valid @RequestBody CategoriaProduto categoriaProduto)
        throws URISyntaxException {
        log.debug("REST request to save CategoriaProduto : {}", categoriaProduto);
        if (categoriaProduto.getId() != null) {
            throw new BadRequestAlertException("A new categoriaProduto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoriaProduto result = categoriaProdutoService.save(categoriaProduto);
        return ResponseEntity
            .created(new URI("/api/categoria-produtos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categoria-produtos/:id} : Updates an existing categoriaProduto.
     *
     * @param id the id of the categoriaProduto to save.
     * @param categoriaProduto the categoriaProduto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriaProduto,
     * or with status {@code 400 (Bad Request)} if the categoriaProduto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoriaProduto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProduto> updateCategoriaProduto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CategoriaProduto categoriaProduto
    ) throws URISyntaxException {
        log.debug("REST request to update CategoriaProduto : {}, {}", id, categoriaProduto);
        if (categoriaProduto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoriaProduto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoriaProdutoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategoriaProduto result = categoriaProdutoService.update(categoriaProduto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriaProduto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /categoria-produtos/:id} : Partial updates given fields of an existing categoriaProduto, field will ignore if it is null
     *
     * @param id the id of the categoriaProduto to save.
     * @param categoriaProduto the categoriaProduto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriaProduto,
     * or with status {@code 400 (Bad Request)} if the categoriaProduto is not valid,
     * or with status {@code 404 (Not Found)} if the categoriaProduto is not found,
     * or with status {@code 500 (Internal Server Error)} if the categoriaProduto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CategoriaProduto> partialUpdateCategoriaProduto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CategoriaProduto categoriaProduto
    ) throws URISyntaxException {
        log.debug("REST request to partial update CategoriaProduto partially : {}, {}", id, categoriaProduto);
        if (categoriaProduto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoriaProduto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoriaProdutoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategoriaProduto> result = categoriaProdutoService.partialUpdate(categoriaProduto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriaProduto.getId().toString())
        );
    }

    /**
     * {@code GET  /categoria-produtos} : get all the categoriaProdutos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoriaProdutos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CategoriaProduto>> getAllCategoriaProdutos(
        CategoriaProdutoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CategoriaProdutos by criteria: {}", criteria);

        Page<CategoriaProduto> page = categoriaProdutoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categoria-produtos/count} : count all the categoriaProdutos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCategoriaProdutos(CategoriaProdutoCriteria criteria) {
        log.debug("REST request to count CategoriaProdutos by criteria: {}", criteria);
        return ResponseEntity.ok().body(categoriaProdutoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /categoria-produtos/:id} : get the "id" categoriaProduto.
     *
     * @param id the id of the categoriaProduto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriaProduto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProduto> getCategoriaProduto(@PathVariable("id") Long id) {
        log.debug("REST request to get CategoriaProduto : {}", id);
        Optional<CategoriaProduto> categoriaProduto = categoriaProdutoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriaProduto);
    }

    /**
     * {@code DELETE  /categoria-produtos/:id} : delete the "id" categoriaProduto.
     *
     * @param id the id of the categoriaProduto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoriaProduto(@PathVariable("id") Long id) {
        log.debug("REST request to delete CategoriaProduto : {}", id);
        categoriaProdutoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

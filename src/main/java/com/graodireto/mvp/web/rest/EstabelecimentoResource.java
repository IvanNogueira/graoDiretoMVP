package com.graodireto.mvp.web.rest;

import com.graodireto.mvp.domain.Estabelecimento;
import com.graodireto.mvp.repository.EstabelecimentoRepository;
import com.graodireto.mvp.service.EstabelecimentoQueryService;
import com.graodireto.mvp.service.EstabelecimentoService;
import com.graodireto.mvp.service.criteria.EstabelecimentoCriteria;
import com.graodireto.mvp.service.dto.EstabelecimentoProdutoDTO;
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
 * REST controller for managing {@link com.graodireto.mvp.domain.Estabelecimento}.
 */
@RestController
@RequestMapping("/api/estabelecimentos")
public class EstabelecimentoResource {

    private final Logger log = LoggerFactory.getLogger(EstabelecimentoResource.class);

    private static final String ENTITY_NAME = "estabelecimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstabelecimentoService estabelecimentoService;

    private final EstabelecimentoRepository estabelecimentoRepository;

    private final EstabelecimentoQueryService estabelecimentoQueryService;

    public EstabelecimentoResource(
        EstabelecimentoService estabelecimentoService,
        EstabelecimentoRepository estabelecimentoRepository,
        EstabelecimentoQueryService estabelecimentoQueryService
    ) {
        this.estabelecimentoService = estabelecimentoService;
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.estabelecimentoQueryService = estabelecimentoQueryService;
    }

    /**
     * {@code POST  /estabelecimentos} : Create a new estabelecimento.
     *
     * @param estabelecimento the estabelecimento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estabelecimento, or with status {@code 400 (Bad Request)} if the estabelecimento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Estabelecimento> createEstabelecimento(@Valid @RequestBody Estabelecimento estabelecimento)
        throws URISyntaxException {
        log.debug("REST request to save Estabelecimento : {}", estabelecimento);
        if (estabelecimento.getId() != null) {
            throw new BadRequestAlertException("A new estabelecimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Estabelecimento result = estabelecimentoService.save(estabelecimento);
        return ResponseEntity
            .created(new URI("/api/estabelecimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estabelecimentos/:id} : Updates an existing estabelecimento.
     *
     * @param id the id of the estabelecimento to save.
     * @param estabelecimento the estabelecimento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estabelecimento,
     * or with status {@code 400 (Bad Request)} if the estabelecimento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estabelecimento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Estabelecimento> updateEstabelecimento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Estabelecimento estabelecimento
    ) throws URISyntaxException {
        log.debug("REST request to update Estabelecimento : {}, {}", id, estabelecimento);
        if (estabelecimento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estabelecimento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estabelecimentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Estabelecimento result = estabelecimentoService.update(estabelecimento);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estabelecimento.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /estabelecimentos/:id} : Partial updates given fields of an existing estabelecimento, field will ignore if it is null
     *
     * @param id the id of the estabelecimento to save.
     * @param estabelecimento the estabelecimento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estabelecimento,
     * or with status {@code 400 (Bad Request)} if the estabelecimento is not valid,
     * or with status {@code 404 (Not Found)} if the estabelecimento is not found,
     * or with status {@code 500 (Internal Server Error)} if the estabelecimento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Estabelecimento> partialUpdateEstabelecimento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Estabelecimento estabelecimento
    ) throws URISyntaxException {
        log.debug("REST request to partial update Estabelecimento partially : {}, {}", id, estabelecimento);
        if (estabelecimento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estabelecimento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estabelecimentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Estabelecimento> result = estabelecimentoService.partialUpdate(estabelecimento);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estabelecimento.getId().toString())
        );
    }

    /**
     * {@code GET  /estabelecimentos} : get all the estabelecimentos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estabelecimentos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Estabelecimento>> getAllEstabelecimentos(
        EstabelecimentoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Estabelecimentos by criteria: {}", criteria);

        Page<Estabelecimento> page = estabelecimentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /estabelecimentos} : get all the estabelecimentos.
     *
     * @param pesquisar the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estabelecimentos in body.
     */
    @GetMapping("pesquisar/{pesquisar}")
    public ResponseEntity<List<EstabelecimentoProdutoDTO>> getAllEstabelecimentos(@PathVariable("pesquisar") String pesquisar) {
        List<EstabelecimentoProdutoDTO> estabelecimentos = estabelecimentoService.findByNomeContaining(pesquisar);
        return ResponseEntity.ok().body(estabelecimentos);
    }

    /**
     * {@code GET  /estabelecimentos/count} : count all the estabelecimentos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEstabelecimentos(EstabelecimentoCriteria criteria) {
        log.debug("REST request to count Estabelecimentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(estabelecimentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /estabelecimentos/:id} : get the "id" estabelecimento.
     *
     * @param id the id of the estabelecimento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estabelecimento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Estabelecimento> getEstabelecimento(@PathVariable("id") Long id) {
        log.debug("REST request to get Estabelecimento : {}", id);
        Optional<Estabelecimento> estabelecimento = estabelecimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estabelecimento);
    }

    /**
     * {@code DELETE  /estabelecimentos/:id} : delete the "id" estabelecimento.
     *
     * @param id the id of the estabelecimento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstabelecimento(@PathVariable("id") Long id) {
        log.debug("REST request to delete Estabelecimento : {}", id);
        estabelecimentoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

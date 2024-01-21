package com.graodireto.mvp.web.rest;

import com.graodireto.mvp.domain.Imagens;
import com.graodireto.mvp.repository.ImagensRepository;
import com.graodireto.mvp.service.ImagensQueryService;
import com.graodireto.mvp.service.ImagensService;
import com.graodireto.mvp.service.criteria.ImagensCriteria;
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
 * REST controller for managing {@link com.graodireto.mvp.domain.Imagens}.
 */
@RestController
@RequestMapping("/api/imagens")
public class ImagensResource {

    private final Logger log = LoggerFactory.getLogger(ImagensResource.class);

    private static final String ENTITY_NAME = "imagens";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImagensService imagensService;

    private final ImagensRepository imagensRepository;

    private final ImagensQueryService imagensQueryService;

    public ImagensResource(ImagensService imagensService, ImagensRepository imagensRepository, ImagensQueryService imagensQueryService) {
        this.imagensService = imagensService;
        this.imagensRepository = imagensRepository;
        this.imagensQueryService = imagensQueryService;
    }

    /**
     * {@code POST  /imagens} : Create a new imagens.
     *
     * @param imagens the imagens to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new imagens, or with status {@code 400 (Bad Request)} if the imagens has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Imagens> createImagens(@RequestBody Imagens imagens) throws URISyntaxException {
        log.debug("REST request to save Imagens : {}", imagens);
        if (imagens.getId() != null) {
            throw new BadRequestAlertException("A new imagens cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Imagens result = imagensService.save(imagens);
        return ResponseEntity
            .created(new URI("/api/imagens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /imagens/:id} : Updates an existing imagens.
     *
     * @param id the id of the imagens to save.
     * @param imagens the imagens to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imagens,
     * or with status {@code 400 (Bad Request)} if the imagens is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imagens couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Imagens> updateImagens(@PathVariable(value = "id", required = false) final Long id, @RequestBody Imagens imagens)
        throws URISyntaxException {
        log.debug("REST request to update Imagens : {}, {}", id, imagens);
        if (imagens.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, imagens.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!imagensRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Imagens result = imagensService.update(imagens);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imagens.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /imagens/:id} : Partial updates given fields of an existing imagens, field will ignore if it is null
     *
     * @param id the id of the imagens to save.
     * @param imagens the imagens to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imagens,
     * or with status {@code 400 (Bad Request)} if the imagens is not valid,
     * or with status {@code 404 (Not Found)} if the imagens is not found,
     * or with status {@code 500 (Internal Server Error)} if the imagens couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Imagens> partialUpdateImagens(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Imagens imagens
    ) throws URISyntaxException {
        log.debug("REST request to partial update Imagens partially : {}, {}", id, imagens);
        if (imagens.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, imagens.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!imagensRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Imagens> result = imagensService.partialUpdate(imagens);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imagens.getId().toString())
        );
    }

    /**
     * {@code GET  /imagens} : get all the imagens.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of imagens in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Imagens>> getAllImagens(
        ImagensCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Imagens by criteria: {}", criteria);

        Page<Imagens> page = imagensQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /imagens/count} : count all the imagens.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countImagens(ImagensCriteria criteria) {
        log.debug("REST request to count Imagens by criteria: {}", criteria);
        return ResponseEntity.ok().body(imagensQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /imagens/:id} : get the "id" imagens.
     *
     * @param id the id of the imagens to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imagens, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Imagens> getImagens(@PathVariable("id") Long id) {
        log.debug("REST request to get Imagens : {}", id);
        Optional<Imagens> imagens = imagensService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imagens);
    }

    /**
     * {@code DELETE  /imagens/:id} : delete the "id" imagens.
     *
     * @param id the id of the imagens to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImagens(@PathVariable("id") Long id) {
        log.debug("REST request to delete Imagens : {}", id);
        imagensService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package com.graodireto.mvp.web.rest;

import com.graodireto.mvp.domain.Cardapio;
import com.graodireto.mvp.repository.CardapioRepository;
import com.graodireto.mvp.service.CardapioQueryService;
import com.graodireto.mvp.service.CardapioService;
import com.graodireto.mvp.service.criteria.CardapioCriteria;
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
 * REST controller for managing {@link com.graodireto.mvp.domain.Cardapio}.
 */
@RestController
@RequestMapping("/api/cardapios")
public class CardapioResource {

    private final Logger log = LoggerFactory.getLogger(CardapioResource.class);

    private static final String ENTITY_NAME = "cardapio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CardapioService cardapioService;

    private final CardapioRepository cardapioRepository;

    private final CardapioQueryService cardapioQueryService;

    public CardapioResource(
        CardapioService cardapioService,
        CardapioRepository cardapioRepository,
        CardapioQueryService cardapioQueryService
    ) {
        this.cardapioService = cardapioService;
        this.cardapioRepository = cardapioRepository;
        this.cardapioQueryService = cardapioQueryService;
    }

    /**
     * {@code POST  /cardapios} : Create a new cardapio.
     *
     * @param cardapio the cardapio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cardapio, or with status {@code 400 (Bad Request)} if the cardapio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Cardapio> createCardapio(@Valid @RequestBody Cardapio cardapio) throws URISyntaxException {
        log.debug("REST request to save Cardapio : {}", cardapio);
        if (cardapio.getId() != null) {
            throw new BadRequestAlertException("A new cardapio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cardapio result = cardapioService.save(cardapio);
        return ResponseEntity
            .created(new URI("/api/cardapios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cardapios/:id} : Updates an existing cardapio.
     *
     * @param id the id of the cardapio to save.
     * @param cardapio the cardapio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cardapio,
     * or with status {@code 400 (Bad Request)} if the cardapio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cardapio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cardapio> updateCardapio(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Cardapio cardapio
    ) throws URISyntaxException {
        log.debug("REST request to update Cardapio : {}, {}", id, cardapio);
        if (cardapio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cardapio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cardapioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Cardapio result = cardapioService.update(cardapio);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cardapio.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cardapios/:id} : Partial updates given fields of an existing cardapio, field will ignore if it is null
     *
     * @param id the id of the cardapio to save.
     * @param cardapio the cardapio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cardapio,
     * or with status {@code 400 (Bad Request)} if the cardapio is not valid,
     * or with status {@code 404 (Not Found)} if the cardapio is not found,
     * or with status {@code 500 (Internal Server Error)} if the cardapio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Cardapio> partialUpdateCardapio(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Cardapio cardapio
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cardapio partially : {}, {}", id, cardapio);
        if (cardapio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cardapio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cardapioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cardapio> result = cardapioService.partialUpdate(cardapio);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cardapio.getId().toString())
        );
    }

    /**
     * {@code GET  /cardapios} : get all the cardapios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cardapios in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Cardapio>> getAllCardapios(
        CardapioCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Cardapios by criteria: {}", criteria);

        Page<Cardapio> page = cardapioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cardapios/count} : count all the cardapios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCardapios(CardapioCriteria criteria) {
        log.debug("REST request to count Cardapios by criteria: {}", criteria);
        return ResponseEntity.ok().body(cardapioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cardapios/:id} : get the "id" cardapio.
     *
     * @param id the id of the cardapio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cardapio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cardapio> getCardapio(@PathVariable("id") Long id) {
        log.debug("REST request to get Cardapio : {}", id);
        Optional<Cardapio> cardapio = cardapioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cardapio);
    }

    /**
     * {@code DELETE  /cardapios/:id} : delete the "id" cardapio.
     *
     * @param id the id of the cardapio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardapio(@PathVariable("id") Long id) {
        log.debug("REST request to delete Cardapio : {}", id);
        cardapioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

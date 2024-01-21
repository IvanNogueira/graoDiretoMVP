package com.graodireto.mvp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.graodireto.mvp.IntegrationTest;
import com.graodireto.mvp.domain.Cardapio;
import com.graodireto.mvp.domain.Estabelecimento;
import com.graodireto.mvp.domain.Produto;
import com.graodireto.mvp.repository.CardapioRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CardapioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CardapioResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_CRIACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CRIACAO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_CRIACAO = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final String ENTITY_API_URL = "/api/cardapios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CardapioRepository cardapioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCardapioMockMvc;

    private Cardapio cardapio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cardapio createEntity(EntityManager em) {
        Cardapio cardapio = new Cardapio().nome(DEFAULT_NOME).dataCriacao(DEFAULT_DATA_CRIACAO).ativo(DEFAULT_ATIVO);
        return cardapio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cardapio createUpdatedEntity(EntityManager em) {
        Cardapio cardapio = new Cardapio().nome(UPDATED_NOME).dataCriacao(UPDATED_DATA_CRIACAO).ativo(UPDATED_ATIVO);
        return cardapio;
    }

    @BeforeEach
    public void initTest() {
        cardapio = createEntity(em);
    }

    @Test
    @Transactional
    void createCardapio() throws Exception {
        int databaseSizeBeforeCreate = cardapioRepository.findAll().size();
        // Create the Cardapio
        restCardapioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cardapio)))
            .andExpect(status().isCreated());

        // Validate the Cardapio in the database
        List<Cardapio> cardapioList = cardapioRepository.findAll();
        assertThat(cardapioList).hasSize(databaseSizeBeforeCreate + 1);
        Cardapio testCardapio = cardapioList.get(cardapioList.size() - 1);
        assertThat(testCardapio.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCardapio.getDataCriacao()).isEqualTo(DEFAULT_DATA_CRIACAO);
        assertThat(testCardapio.getAtivo()).isEqualTo(DEFAULT_ATIVO);
    }

    @Test
    @Transactional
    void createCardapioWithExistingId() throws Exception {
        // Create the Cardapio with an existing ID
        cardapio.setId(1L);

        int databaseSizeBeforeCreate = cardapioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCardapioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cardapio)))
            .andExpect(status().isBadRequest());

        // Validate the Cardapio in the database
        List<Cardapio> cardapioList = cardapioRepository.findAll();
        assertThat(cardapioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cardapioRepository.findAll().size();
        // set the field null
        cardapio.setNome(null);

        // Create the Cardapio, which fails.

        restCardapioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cardapio)))
            .andExpect(status().isBadRequest());

        List<Cardapio> cardapioList = cardapioRepository.findAll();
        assertThat(cardapioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCardapios() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        // Get all the cardapioList
        restCardapioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cardapio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataCriacao").value(hasItem(DEFAULT_DATA_CRIACAO.toString())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getCardapio() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        // Get the cardapio
        restCardapioMockMvc
            .perform(get(ENTITY_API_URL_ID, cardapio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cardapio.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.dataCriacao").value(DEFAULT_DATA_CRIACAO.toString()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getCardapiosByIdFiltering() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        Long id = cardapio.getId();

        defaultCardapioShouldBeFound("id.equals=" + id);
        defaultCardapioShouldNotBeFound("id.notEquals=" + id);

        defaultCardapioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCardapioShouldNotBeFound("id.greaterThan=" + id);

        defaultCardapioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCardapioShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCardapiosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        // Get all the cardapioList where nome equals to DEFAULT_NOME
        defaultCardapioShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the cardapioList where nome equals to UPDATED_NOME
        defaultCardapioShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCardapiosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        // Get all the cardapioList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultCardapioShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the cardapioList where nome equals to UPDATED_NOME
        defaultCardapioShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCardapiosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        // Get all the cardapioList where nome is not null
        defaultCardapioShouldBeFound("nome.specified=true");

        // Get all the cardapioList where nome is null
        defaultCardapioShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllCardapiosByNomeContainsSomething() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        // Get all the cardapioList where nome contains DEFAULT_NOME
        defaultCardapioShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the cardapioList where nome contains UPDATED_NOME
        defaultCardapioShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCardapiosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        // Get all the cardapioList where nome does not contain DEFAULT_NOME
        defaultCardapioShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the cardapioList where nome does not contain UPDATED_NOME
        defaultCardapioShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCardapiosByDataCriacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        // Get all the cardapioList where dataCriacao equals to DEFAULT_DATA_CRIACAO
        defaultCardapioShouldBeFound("dataCriacao.equals=" + DEFAULT_DATA_CRIACAO);

        // Get all the cardapioList where dataCriacao equals to UPDATED_DATA_CRIACAO
        defaultCardapioShouldNotBeFound("dataCriacao.equals=" + UPDATED_DATA_CRIACAO);
    }

    @Test
    @Transactional
    void getAllCardapiosByDataCriacaoIsInShouldWork() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        // Get all the cardapioList where dataCriacao in DEFAULT_DATA_CRIACAO or UPDATED_DATA_CRIACAO
        defaultCardapioShouldBeFound("dataCriacao.in=" + DEFAULT_DATA_CRIACAO + "," + UPDATED_DATA_CRIACAO);

        // Get all the cardapioList where dataCriacao equals to UPDATED_DATA_CRIACAO
        defaultCardapioShouldNotBeFound("dataCriacao.in=" + UPDATED_DATA_CRIACAO);
    }

    @Test
    @Transactional
    void getAllCardapiosByDataCriacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        // Get all the cardapioList where dataCriacao is not null
        defaultCardapioShouldBeFound("dataCriacao.specified=true");

        // Get all the cardapioList where dataCriacao is null
        defaultCardapioShouldNotBeFound("dataCriacao.specified=false");
    }

    @Test
    @Transactional
    void getAllCardapiosByDataCriacaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        // Get all the cardapioList where dataCriacao is greater than or equal to DEFAULT_DATA_CRIACAO
        defaultCardapioShouldBeFound("dataCriacao.greaterThanOrEqual=" + DEFAULT_DATA_CRIACAO);

        // Get all the cardapioList where dataCriacao is greater than or equal to UPDATED_DATA_CRIACAO
        defaultCardapioShouldNotBeFound("dataCriacao.greaterThanOrEqual=" + UPDATED_DATA_CRIACAO);
    }

    @Test
    @Transactional
    void getAllCardapiosByDataCriacaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        // Get all the cardapioList where dataCriacao is less than or equal to DEFAULT_DATA_CRIACAO
        defaultCardapioShouldBeFound("dataCriacao.lessThanOrEqual=" + DEFAULT_DATA_CRIACAO);

        // Get all the cardapioList where dataCriacao is less than or equal to SMALLER_DATA_CRIACAO
        defaultCardapioShouldNotBeFound("dataCriacao.lessThanOrEqual=" + SMALLER_DATA_CRIACAO);
    }

    @Test
    @Transactional
    void getAllCardapiosByDataCriacaoIsLessThanSomething() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        // Get all the cardapioList where dataCriacao is less than DEFAULT_DATA_CRIACAO
        defaultCardapioShouldNotBeFound("dataCriacao.lessThan=" + DEFAULT_DATA_CRIACAO);

        // Get all the cardapioList where dataCriacao is less than UPDATED_DATA_CRIACAO
        defaultCardapioShouldBeFound("dataCriacao.lessThan=" + UPDATED_DATA_CRIACAO);
    }

    @Test
    @Transactional
    void getAllCardapiosByDataCriacaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        // Get all the cardapioList where dataCriacao is greater than DEFAULT_DATA_CRIACAO
        defaultCardapioShouldNotBeFound("dataCriacao.greaterThan=" + DEFAULT_DATA_CRIACAO);

        // Get all the cardapioList where dataCriacao is greater than SMALLER_DATA_CRIACAO
        defaultCardapioShouldBeFound("dataCriacao.greaterThan=" + SMALLER_DATA_CRIACAO);
    }

    @Test
    @Transactional
    void getAllCardapiosByAtivoIsEqualToSomething() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        // Get all the cardapioList where ativo equals to DEFAULT_ATIVO
        defaultCardapioShouldBeFound("ativo.equals=" + DEFAULT_ATIVO);

        // Get all the cardapioList where ativo equals to UPDATED_ATIVO
        defaultCardapioShouldNotBeFound("ativo.equals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    void getAllCardapiosByAtivoIsInShouldWork() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        // Get all the cardapioList where ativo in DEFAULT_ATIVO or UPDATED_ATIVO
        defaultCardapioShouldBeFound("ativo.in=" + DEFAULT_ATIVO + "," + UPDATED_ATIVO);

        // Get all the cardapioList where ativo equals to UPDATED_ATIVO
        defaultCardapioShouldNotBeFound("ativo.in=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    void getAllCardapiosByAtivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        // Get all the cardapioList where ativo is not null
        defaultCardapioShouldBeFound("ativo.specified=true");

        // Get all the cardapioList where ativo is null
        defaultCardapioShouldNotBeFound("ativo.specified=false");
    }

    @Test
    @Transactional
    void getAllCardapiosByProdutoIsEqualToSomething() throws Exception {
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            cardapioRepository.saveAndFlush(cardapio);
            produto = ProdutoResourceIT.createEntity(em);
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        em.persist(produto);
        em.flush();
        cardapio.addProduto(produto);
        cardapioRepository.saveAndFlush(cardapio);
        Long produtoId = produto.getId();
        // Get all the cardapioList where produto equals to produtoId
        defaultCardapioShouldBeFound("produtoId.equals=" + produtoId);

        // Get all the cardapioList where produto equals to (produtoId + 1)
        defaultCardapioShouldNotBeFound("produtoId.equals=" + (produtoId + 1));
    }

    @Test
    @Transactional
    void getAllCardapiosByEstabelecimentoIsEqualToSomething() throws Exception {
        Estabelecimento estabelecimento;
        if (TestUtil.findAll(em, Estabelecimento.class).isEmpty()) {
            cardapioRepository.saveAndFlush(cardapio);
            estabelecimento = EstabelecimentoResourceIT.createEntity(em);
        } else {
            estabelecimento = TestUtil.findAll(em, Estabelecimento.class).get(0);
        }
        em.persist(estabelecimento);
        em.flush();
        cardapio.setEstabelecimento(estabelecimento);
        cardapioRepository.saveAndFlush(cardapio);
        Long estabelecimentoId = estabelecimento.getId();
        // Get all the cardapioList where estabelecimento equals to estabelecimentoId
        defaultCardapioShouldBeFound("estabelecimentoId.equals=" + estabelecimentoId);

        // Get all the cardapioList where estabelecimento equals to (estabelecimentoId + 1)
        defaultCardapioShouldNotBeFound("estabelecimentoId.equals=" + (estabelecimentoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCardapioShouldBeFound(String filter) throws Exception {
        restCardapioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cardapio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataCriacao").value(hasItem(DEFAULT_DATA_CRIACAO.toString())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));

        // Check, that the count call also returns 1
        restCardapioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCardapioShouldNotBeFound(String filter) throws Exception {
        restCardapioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCardapioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCardapio() throws Exception {
        // Get the cardapio
        restCardapioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCardapio() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        int databaseSizeBeforeUpdate = cardapioRepository.findAll().size();

        // Update the cardapio
        Cardapio updatedCardapio = cardapioRepository.findById(cardapio.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCardapio are not directly saved in db
        em.detach(updatedCardapio);
        updatedCardapio.nome(UPDATED_NOME).dataCriacao(UPDATED_DATA_CRIACAO).ativo(UPDATED_ATIVO);

        restCardapioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCardapio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCardapio))
            )
            .andExpect(status().isOk());

        // Validate the Cardapio in the database
        List<Cardapio> cardapioList = cardapioRepository.findAll();
        assertThat(cardapioList).hasSize(databaseSizeBeforeUpdate);
        Cardapio testCardapio = cardapioList.get(cardapioList.size() - 1);
        assertThat(testCardapio.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCardapio.getDataCriacao()).isEqualTo(UPDATED_DATA_CRIACAO);
        assertThat(testCardapio.getAtivo()).isEqualTo(UPDATED_ATIVO);
    }

    @Test
    @Transactional
    void putNonExistingCardapio() throws Exception {
        int databaseSizeBeforeUpdate = cardapioRepository.findAll().size();
        cardapio.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardapioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cardapio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cardapio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cardapio in the database
        List<Cardapio> cardapioList = cardapioRepository.findAll();
        assertThat(cardapioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCardapio() throws Exception {
        int databaseSizeBeforeUpdate = cardapioRepository.findAll().size();
        cardapio.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardapioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cardapio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cardapio in the database
        List<Cardapio> cardapioList = cardapioRepository.findAll();
        assertThat(cardapioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCardapio() throws Exception {
        int databaseSizeBeforeUpdate = cardapioRepository.findAll().size();
        cardapio.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardapioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cardapio)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cardapio in the database
        List<Cardapio> cardapioList = cardapioRepository.findAll();
        assertThat(cardapioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCardapioWithPatch() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        int databaseSizeBeforeUpdate = cardapioRepository.findAll().size();

        // Update the cardapio using partial update
        Cardapio partialUpdatedCardapio = new Cardapio();
        partialUpdatedCardapio.setId(cardapio.getId());

        partialUpdatedCardapio.nome(UPDATED_NOME).dataCriacao(UPDATED_DATA_CRIACAO).ativo(UPDATED_ATIVO);

        restCardapioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCardapio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCardapio))
            )
            .andExpect(status().isOk());

        // Validate the Cardapio in the database
        List<Cardapio> cardapioList = cardapioRepository.findAll();
        assertThat(cardapioList).hasSize(databaseSizeBeforeUpdate);
        Cardapio testCardapio = cardapioList.get(cardapioList.size() - 1);
        assertThat(testCardapio.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCardapio.getDataCriacao()).isEqualTo(UPDATED_DATA_CRIACAO);
        assertThat(testCardapio.getAtivo()).isEqualTo(UPDATED_ATIVO);
    }

    @Test
    @Transactional
    void fullUpdateCardapioWithPatch() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        int databaseSizeBeforeUpdate = cardapioRepository.findAll().size();

        // Update the cardapio using partial update
        Cardapio partialUpdatedCardapio = new Cardapio();
        partialUpdatedCardapio.setId(cardapio.getId());

        partialUpdatedCardapio.nome(UPDATED_NOME).dataCriacao(UPDATED_DATA_CRIACAO).ativo(UPDATED_ATIVO);

        restCardapioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCardapio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCardapio))
            )
            .andExpect(status().isOk());

        // Validate the Cardapio in the database
        List<Cardapio> cardapioList = cardapioRepository.findAll();
        assertThat(cardapioList).hasSize(databaseSizeBeforeUpdate);
        Cardapio testCardapio = cardapioList.get(cardapioList.size() - 1);
        assertThat(testCardapio.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCardapio.getDataCriacao()).isEqualTo(UPDATED_DATA_CRIACAO);
        assertThat(testCardapio.getAtivo()).isEqualTo(UPDATED_ATIVO);
    }

    @Test
    @Transactional
    void patchNonExistingCardapio() throws Exception {
        int databaseSizeBeforeUpdate = cardapioRepository.findAll().size();
        cardapio.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardapioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cardapio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cardapio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cardapio in the database
        List<Cardapio> cardapioList = cardapioRepository.findAll();
        assertThat(cardapioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCardapio() throws Exception {
        int databaseSizeBeforeUpdate = cardapioRepository.findAll().size();
        cardapio.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardapioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cardapio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cardapio in the database
        List<Cardapio> cardapioList = cardapioRepository.findAll();
        assertThat(cardapioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCardapio() throws Exception {
        int databaseSizeBeforeUpdate = cardapioRepository.findAll().size();
        cardapio.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardapioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cardapio)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cardapio in the database
        List<Cardapio> cardapioList = cardapioRepository.findAll();
        assertThat(cardapioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCardapio() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        int databaseSizeBeforeDelete = cardapioRepository.findAll().size();

        // Delete the cardapio
        restCardapioMockMvc
            .perform(delete(ENTITY_API_URL_ID, cardapio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cardapio> cardapioList = cardapioRepository.findAll();
        assertThat(cardapioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

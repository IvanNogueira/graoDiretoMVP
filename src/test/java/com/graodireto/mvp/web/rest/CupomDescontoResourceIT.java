package com.graodireto.mvp.web.rest;

import static com.graodireto.mvp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.graodireto.mvp.IntegrationTest;
import com.graodireto.mvp.domain.CupomDesconto;
import com.graodireto.mvp.domain.Estabelecimento;
import com.graodireto.mvp.repository.CupomDescontoRepository;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link CupomDescontoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CupomDescontoResourceIT {

    private static final BigDecimal DEFAULT_VALOR_DESCONTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_DESCONTO = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_DESCONTO = new BigDecimal(1 - 1);

    private static final Boolean DEFAULT_VALOR_MINIMO = false;
    private static final Boolean UPDATED_VALOR_MINIMO = true;

    private static final BigDecimal DEFAULT_VALOR_MINIMO_REGRA = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_MINIMO_REGRA = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_MINIMO_REGRA = new BigDecimal(1 - 1);

    private static final String DEFAULT_DESCRICAO_REGRAS = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_REGRAS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VALIDO = false;
    private static final Boolean UPDATED_VALIDO = true;

    private static final String ENTITY_API_URL = "/api/cupom-descontos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CupomDescontoRepository cupomDescontoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCupomDescontoMockMvc;

    private CupomDesconto cupomDesconto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CupomDesconto createEntity(EntityManager em) {
        CupomDesconto cupomDesconto = new CupomDesconto()
            .valorDesconto(DEFAULT_VALOR_DESCONTO)
            .valorMinimo(DEFAULT_VALOR_MINIMO)
            .valorMinimoRegra(DEFAULT_VALOR_MINIMO_REGRA)
            .descricaoRegras(DEFAULT_DESCRICAO_REGRAS)
            .valido(DEFAULT_VALIDO);
        return cupomDesconto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CupomDesconto createUpdatedEntity(EntityManager em) {
        CupomDesconto cupomDesconto = new CupomDesconto()
            .valorDesconto(UPDATED_VALOR_DESCONTO)
            .valorMinimo(UPDATED_VALOR_MINIMO)
            .valorMinimoRegra(UPDATED_VALOR_MINIMO_REGRA)
            .descricaoRegras(UPDATED_DESCRICAO_REGRAS)
            .valido(UPDATED_VALIDO);
        return cupomDesconto;
    }

    @BeforeEach
    public void initTest() {
        cupomDesconto = createEntity(em);
    }

    @Test
    @Transactional
    void createCupomDesconto() throws Exception {
        int databaseSizeBeforeCreate = cupomDescontoRepository.findAll().size();
        // Create the CupomDesconto
        restCupomDescontoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cupomDesconto)))
            .andExpect(status().isCreated());

        // Validate the CupomDesconto in the database
        List<CupomDesconto> cupomDescontoList = cupomDescontoRepository.findAll();
        assertThat(cupomDescontoList).hasSize(databaseSizeBeforeCreate + 1);
        CupomDesconto testCupomDesconto = cupomDescontoList.get(cupomDescontoList.size() - 1);
        assertThat(testCupomDesconto.getValorDesconto()).isEqualByComparingTo(DEFAULT_VALOR_DESCONTO);
        assertThat(testCupomDesconto.getValorMinimo()).isEqualTo(DEFAULT_VALOR_MINIMO);
        assertThat(testCupomDesconto.getValorMinimoRegra()).isEqualByComparingTo(DEFAULT_VALOR_MINIMO_REGRA);
        assertThat(testCupomDesconto.getDescricaoRegras()).isEqualTo(DEFAULT_DESCRICAO_REGRAS);
        assertThat(testCupomDesconto.getValido()).isEqualTo(DEFAULT_VALIDO);
    }

    @Test
    @Transactional
    void createCupomDescontoWithExistingId() throws Exception {
        // Create the CupomDesconto with an existing ID
        cupomDesconto.setId(1L);

        int databaseSizeBeforeCreate = cupomDescontoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCupomDescontoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cupomDesconto)))
            .andExpect(status().isBadRequest());

        // Validate the CupomDesconto in the database
        List<CupomDesconto> cupomDescontoList = cupomDescontoRepository.findAll();
        assertThat(cupomDescontoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCupomDescontos() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList
        restCupomDescontoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cupomDesconto.getId().intValue())))
            .andExpect(jsonPath("$.[*].valorDesconto").value(hasItem(sameNumber(DEFAULT_VALOR_DESCONTO))))
            .andExpect(jsonPath("$.[*].valorMinimo").value(hasItem(DEFAULT_VALOR_MINIMO.booleanValue())))
            .andExpect(jsonPath("$.[*].valorMinimoRegra").value(hasItem(sameNumber(DEFAULT_VALOR_MINIMO_REGRA))))
            .andExpect(jsonPath("$.[*].descricaoRegras").value(hasItem(DEFAULT_DESCRICAO_REGRAS)))
            .andExpect(jsonPath("$.[*].valido").value(hasItem(DEFAULT_VALIDO.booleanValue())));
    }

    @Test
    @Transactional
    void getCupomDesconto() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get the cupomDesconto
        restCupomDescontoMockMvc
            .perform(get(ENTITY_API_URL_ID, cupomDesconto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cupomDesconto.getId().intValue()))
            .andExpect(jsonPath("$.valorDesconto").value(sameNumber(DEFAULT_VALOR_DESCONTO)))
            .andExpect(jsonPath("$.valorMinimo").value(DEFAULT_VALOR_MINIMO.booleanValue()))
            .andExpect(jsonPath("$.valorMinimoRegra").value(sameNumber(DEFAULT_VALOR_MINIMO_REGRA)))
            .andExpect(jsonPath("$.descricaoRegras").value(DEFAULT_DESCRICAO_REGRAS))
            .andExpect(jsonPath("$.valido").value(DEFAULT_VALIDO.booleanValue()));
    }

    @Test
    @Transactional
    void getCupomDescontosByIdFiltering() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        Long id = cupomDesconto.getId();

        defaultCupomDescontoShouldBeFound("id.equals=" + id);
        defaultCupomDescontoShouldNotBeFound("id.notEquals=" + id);

        defaultCupomDescontoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCupomDescontoShouldNotBeFound("id.greaterThan=" + id);

        defaultCupomDescontoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCupomDescontoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValorDescontoIsEqualToSomething() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valorDesconto equals to DEFAULT_VALOR_DESCONTO
        defaultCupomDescontoShouldBeFound("valorDesconto.equals=" + DEFAULT_VALOR_DESCONTO);

        // Get all the cupomDescontoList where valorDesconto equals to UPDATED_VALOR_DESCONTO
        defaultCupomDescontoShouldNotBeFound("valorDesconto.equals=" + UPDATED_VALOR_DESCONTO);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValorDescontoIsInShouldWork() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valorDesconto in DEFAULT_VALOR_DESCONTO or UPDATED_VALOR_DESCONTO
        defaultCupomDescontoShouldBeFound("valorDesconto.in=" + DEFAULT_VALOR_DESCONTO + "," + UPDATED_VALOR_DESCONTO);

        // Get all the cupomDescontoList where valorDesconto equals to UPDATED_VALOR_DESCONTO
        defaultCupomDescontoShouldNotBeFound("valorDesconto.in=" + UPDATED_VALOR_DESCONTO);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValorDescontoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valorDesconto is not null
        defaultCupomDescontoShouldBeFound("valorDesconto.specified=true");

        // Get all the cupomDescontoList where valorDesconto is null
        defaultCupomDescontoShouldNotBeFound("valorDesconto.specified=false");
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValorDescontoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valorDesconto is greater than or equal to DEFAULT_VALOR_DESCONTO
        defaultCupomDescontoShouldBeFound("valorDesconto.greaterThanOrEqual=" + DEFAULT_VALOR_DESCONTO);

        // Get all the cupomDescontoList where valorDesconto is greater than or equal to UPDATED_VALOR_DESCONTO
        defaultCupomDescontoShouldNotBeFound("valorDesconto.greaterThanOrEqual=" + UPDATED_VALOR_DESCONTO);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValorDescontoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valorDesconto is less than or equal to DEFAULT_VALOR_DESCONTO
        defaultCupomDescontoShouldBeFound("valorDesconto.lessThanOrEqual=" + DEFAULT_VALOR_DESCONTO);

        // Get all the cupomDescontoList where valorDesconto is less than or equal to SMALLER_VALOR_DESCONTO
        defaultCupomDescontoShouldNotBeFound("valorDesconto.lessThanOrEqual=" + SMALLER_VALOR_DESCONTO);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValorDescontoIsLessThanSomething() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valorDesconto is less than DEFAULT_VALOR_DESCONTO
        defaultCupomDescontoShouldNotBeFound("valorDesconto.lessThan=" + DEFAULT_VALOR_DESCONTO);

        // Get all the cupomDescontoList where valorDesconto is less than UPDATED_VALOR_DESCONTO
        defaultCupomDescontoShouldBeFound("valorDesconto.lessThan=" + UPDATED_VALOR_DESCONTO);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValorDescontoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valorDesconto is greater than DEFAULT_VALOR_DESCONTO
        defaultCupomDescontoShouldNotBeFound("valorDesconto.greaterThan=" + DEFAULT_VALOR_DESCONTO);

        // Get all the cupomDescontoList where valorDesconto is greater than SMALLER_VALOR_DESCONTO
        defaultCupomDescontoShouldBeFound("valorDesconto.greaterThan=" + SMALLER_VALOR_DESCONTO);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValorMinimoIsEqualToSomething() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valorMinimo equals to DEFAULT_VALOR_MINIMO
        defaultCupomDescontoShouldBeFound("valorMinimo.equals=" + DEFAULT_VALOR_MINIMO);

        // Get all the cupomDescontoList where valorMinimo equals to UPDATED_VALOR_MINIMO
        defaultCupomDescontoShouldNotBeFound("valorMinimo.equals=" + UPDATED_VALOR_MINIMO);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValorMinimoIsInShouldWork() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valorMinimo in DEFAULT_VALOR_MINIMO or UPDATED_VALOR_MINIMO
        defaultCupomDescontoShouldBeFound("valorMinimo.in=" + DEFAULT_VALOR_MINIMO + "," + UPDATED_VALOR_MINIMO);

        // Get all the cupomDescontoList where valorMinimo equals to UPDATED_VALOR_MINIMO
        defaultCupomDescontoShouldNotBeFound("valorMinimo.in=" + UPDATED_VALOR_MINIMO);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValorMinimoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valorMinimo is not null
        defaultCupomDescontoShouldBeFound("valorMinimo.specified=true");

        // Get all the cupomDescontoList where valorMinimo is null
        defaultCupomDescontoShouldNotBeFound("valorMinimo.specified=false");
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValorMinimoRegraIsEqualToSomething() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valorMinimoRegra equals to DEFAULT_VALOR_MINIMO_REGRA
        defaultCupomDescontoShouldBeFound("valorMinimoRegra.equals=" + DEFAULT_VALOR_MINIMO_REGRA);

        // Get all the cupomDescontoList where valorMinimoRegra equals to UPDATED_VALOR_MINIMO_REGRA
        defaultCupomDescontoShouldNotBeFound("valorMinimoRegra.equals=" + UPDATED_VALOR_MINIMO_REGRA);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValorMinimoRegraIsInShouldWork() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valorMinimoRegra in DEFAULT_VALOR_MINIMO_REGRA or UPDATED_VALOR_MINIMO_REGRA
        defaultCupomDescontoShouldBeFound("valorMinimoRegra.in=" + DEFAULT_VALOR_MINIMO_REGRA + "," + UPDATED_VALOR_MINIMO_REGRA);

        // Get all the cupomDescontoList where valorMinimoRegra equals to UPDATED_VALOR_MINIMO_REGRA
        defaultCupomDescontoShouldNotBeFound("valorMinimoRegra.in=" + UPDATED_VALOR_MINIMO_REGRA);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValorMinimoRegraIsNullOrNotNull() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valorMinimoRegra is not null
        defaultCupomDescontoShouldBeFound("valorMinimoRegra.specified=true");

        // Get all the cupomDescontoList where valorMinimoRegra is null
        defaultCupomDescontoShouldNotBeFound("valorMinimoRegra.specified=false");
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValorMinimoRegraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valorMinimoRegra is greater than or equal to DEFAULT_VALOR_MINIMO_REGRA
        defaultCupomDescontoShouldBeFound("valorMinimoRegra.greaterThanOrEqual=" + DEFAULT_VALOR_MINIMO_REGRA);

        // Get all the cupomDescontoList where valorMinimoRegra is greater than or equal to UPDATED_VALOR_MINIMO_REGRA
        defaultCupomDescontoShouldNotBeFound("valorMinimoRegra.greaterThanOrEqual=" + UPDATED_VALOR_MINIMO_REGRA);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValorMinimoRegraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valorMinimoRegra is less than or equal to DEFAULT_VALOR_MINIMO_REGRA
        defaultCupomDescontoShouldBeFound("valorMinimoRegra.lessThanOrEqual=" + DEFAULT_VALOR_MINIMO_REGRA);

        // Get all the cupomDescontoList where valorMinimoRegra is less than or equal to SMALLER_VALOR_MINIMO_REGRA
        defaultCupomDescontoShouldNotBeFound("valorMinimoRegra.lessThanOrEqual=" + SMALLER_VALOR_MINIMO_REGRA);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValorMinimoRegraIsLessThanSomething() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valorMinimoRegra is less than DEFAULT_VALOR_MINIMO_REGRA
        defaultCupomDescontoShouldNotBeFound("valorMinimoRegra.lessThan=" + DEFAULT_VALOR_MINIMO_REGRA);

        // Get all the cupomDescontoList where valorMinimoRegra is less than UPDATED_VALOR_MINIMO_REGRA
        defaultCupomDescontoShouldBeFound("valorMinimoRegra.lessThan=" + UPDATED_VALOR_MINIMO_REGRA);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValorMinimoRegraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valorMinimoRegra is greater than DEFAULT_VALOR_MINIMO_REGRA
        defaultCupomDescontoShouldNotBeFound("valorMinimoRegra.greaterThan=" + DEFAULT_VALOR_MINIMO_REGRA);

        // Get all the cupomDescontoList where valorMinimoRegra is greater than SMALLER_VALOR_MINIMO_REGRA
        defaultCupomDescontoShouldBeFound("valorMinimoRegra.greaterThan=" + SMALLER_VALOR_MINIMO_REGRA);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByDescricaoRegrasIsEqualToSomething() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where descricaoRegras equals to DEFAULT_DESCRICAO_REGRAS
        defaultCupomDescontoShouldBeFound("descricaoRegras.equals=" + DEFAULT_DESCRICAO_REGRAS);

        // Get all the cupomDescontoList where descricaoRegras equals to UPDATED_DESCRICAO_REGRAS
        defaultCupomDescontoShouldNotBeFound("descricaoRegras.equals=" + UPDATED_DESCRICAO_REGRAS);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByDescricaoRegrasIsInShouldWork() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where descricaoRegras in DEFAULT_DESCRICAO_REGRAS or UPDATED_DESCRICAO_REGRAS
        defaultCupomDescontoShouldBeFound("descricaoRegras.in=" + DEFAULT_DESCRICAO_REGRAS + "," + UPDATED_DESCRICAO_REGRAS);

        // Get all the cupomDescontoList where descricaoRegras equals to UPDATED_DESCRICAO_REGRAS
        defaultCupomDescontoShouldNotBeFound("descricaoRegras.in=" + UPDATED_DESCRICAO_REGRAS);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByDescricaoRegrasIsNullOrNotNull() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where descricaoRegras is not null
        defaultCupomDescontoShouldBeFound("descricaoRegras.specified=true");

        // Get all the cupomDescontoList where descricaoRegras is null
        defaultCupomDescontoShouldNotBeFound("descricaoRegras.specified=false");
    }

    @Test
    @Transactional
    void getAllCupomDescontosByDescricaoRegrasContainsSomething() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where descricaoRegras contains DEFAULT_DESCRICAO_REGRAS
        defaultCupomDescontoShouldBeFound("descricaoRegras.contains=" + DEFAULT_DESCRICAO_REGRAS);

        // Get all the cupomDescontoList where descricaoRegras contains UPDATED_DESCRICAO_REGRAS
        defaultCupomDescontoShouldNotBeFound("descricaoRegras.contains=" + UPDATED_DESCRICAO_REGRAS);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByDescricaoRegrasNotContainsSomething() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where descricaoRegras does not contain DEFAULT_DESCRICAO_REGRAS
        defaultCupomDescontoShouldNotBeFound("descricaoRegras.doesNotContain=" + DEFAULT_DESCRICAO_REGRAS);

        // Get all the cupomDescontoList where descricaoRegras does not contain UPDATED_DESCRICAO_REGRAS
        defaultCupomDescontoShouldBeFound("descricaoRegras.doesNotContain=" + UPDATED_DESCRICAO_REGRAS);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValidoIsEqualToSomething() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valido equals to DEFAULT_VALIDO
        defaultCupomDescontoShouldBeFound("valido.equals=" + DEFAULT_VALIDO);

        // Get all the cupomDescontoList where valido equals to UPDATED_VALIDO
        defaultCupomDescontoShouldNotBeFound("valido.equals=" + UPDATED_VALIDO);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValidoIsInShouldWork() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valido in DEFAULT_VALIDO or UPDATED_VALIDO
        defaultCupomDescontoShouldBeFound("valido.in=" + DEFAULT_VALIDO + "," + UPDATED_VALIDO);

        // Get all the cupomDescontoList where valido equals to UPDATED_VALIDO
        defaultCupomDescontoShouldNotBeFound("valido.in=" + UPDATED_VALIDO);
    }

    @Test
    @Transactional
    void getAllCupomDescontosByValidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        // Get all the cupomDescontoList where valido is not null
        defaultCupomDescontoShouldBeFound("valido.specified=true");

        // Get all the cupomDescontoList where valido is null
        defaultCupomDescontoShouldNotBeFound("valido.specified=false");
    }

    @Test
    @Transactional
    void getAllCupomDescontosByEstabelecimentoIsEqualToSomething() throws Exception {
        Estabelecimento estabelecimento;
        if (TestUtil.findAll(em, Estabelecimento.class).isEmpty()) {
            cupomDescontoRepository.saveAndFlush(cupomDesconto);
            estabelecimento = EstabelecimentoResourceIT.createEntity(em);
        } else {
            estabelecimento = TestUtil.findAll(em, Estabelecimento.class).get(0);
        }
        em.persist(estabelecimento);
        em.flush();
        cupomDesconto.setEstabelecimento(estabelecimento);
        cupomDescontoRepository.saveAndFlush(cupomDesconto);
        Long estabelecimentoId = estabelecimento.getId();
        // Get all the cupomDescontoList where estabelecimento equals to estabelecimentoId
        defaultCupomDescontoShouldBeFound("estabelecimentoId.equals=" + estabelecimentoId);

        // Get all the cupomDescontoList where estabelecimento equals to (estabelecimentoId + 1)
        defaultCupomDescontoShouldNotBeFound("estabelecimentoId.equals=" + (estabelecimentoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCupomDescontoShouldBeFound(String filter) throws Exception {
        restCupomDescontoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cupomDesconto.getId().intValue())))
            .andExpect(jsonPath("$.[*].valorDesconto").value(hasItem(sameNumber(DEFAULT_VALOR_DESCONTO))))
            .andExpect(jsonPath("$.[*].valorMinimo").value(hasItem(DEFAULT_VALOR_MINIMO.booleanValue())))
            .andExpect(jsonPath("$.[*].valorMinimoRegra").value(hasItem(sameNumber(DEFAULT_VALOR_MINIMO_REGRA))))
            .andExpect(jsonPath("$.[*].descricaoRegras").value(hasItem(DEFAULT_DESCRICAO_REGRAS)))
            .andExpect(jsonPath("$.[*].valido").value(hasItem(DEFAULT_VALIDO.booleanValue())));

        // Check, that the count call also returns 1
        restCupomDescontoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCupomDescontoShouldNotBeFound(String filter) throws Exception {
        restCupomDescontoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCupomDescontoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCupomDesconto() throws Exception {
        // Get the cupomDesconto
        restCupomDescontoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCupomDesconto() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        int databaseSizeBeforeUpdate = cupomDescontoRepository.findAll().size();

        // Update the cupomDesconto
        CupomDesconto updatedCupomDesconto = cupomDescontoRepository.findById(cupomDesconto.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCupomDesconto are not directly saved in db
        em.detach(updatedCupomDesconto);
        updatedCupomDesconto
            .valorDesconto(UPDATED_VALOR_DESCONTO)
            .valorMinimo(UPDATED_VALOR_MINIMO)
            .valorMinimoRegra(UPDATED_VALOR_MINIMO_REGRA)
            .descricaoRegras(UPDATED_DESCRICAO_REGRAS)
            .valido(UPDATED_VALIDO);

        restCupomDescontoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCupomDesconto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCupomDesconto))
            )
            .andExpect(status().isOk());

        // Validate the CupomDesconto in the database
        List<CupomDesconto> cupomDescontoList = cupomDescontoRepository.findAll();
        assertThat(cupomDescontoList).hasSize(databaseSizeBeforeUpdate);
        CupomDesconto testCupomDesconto = cupomDescontoList.get(cupomDescontoList.size() - 1);
        assertThat(testCupomDesconto.getValorDesconto()).isEqualByComparingTo(UPDATED_VALOR_DESCONTO);
        assertThat(testCupomDesconto.getValorMinimo()).isEqualTo(UPDATED_VALOR_MINIMO);
        assertThat(testCupomDesconto.getValorMinimoRegra()).isEqualByComparingTo(UPDATED_VALOR_MINIMO_REGRA);
        assertThat(testCupomDesconto.getDescricaoRegras()).isEqualTo(UPDATED_DESCRICAO_REGRAS);
        assertThat(testCupomDesconto.getValido()).isEqualTo(UPDATED_VALIDO);
    }

    @Test
    @Transactional
    void putNonExistingCupomDesconto() throws Exception {
        int databaseSizeBeforeUpdate = cupomDescontoRepository.findAll().size();
        cupomDesconto.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCupomDescontoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cupomDesconto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cupomDesconto))
            )
            .andExpect(status().isBadRequest());

        // Validate the CupomDesconto in the database
        List<CupomDesconto> cupomDescontoList = cupomDescontoRepository.findAll();
        assertThat(cupomDescontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCupomDesconto() throws Exception {
        int databaseSizeBeforeUpdate = cupomDescontoRepository.findAll().size();
        cupomDesconto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCupomDescontoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cupomDesconto))
            )
            .andExpect(status().isBadRequest());

        // Validate the CupomDesconto in the database
        List<CupomDesconto> cupomDescontoList = cupomDescontoRepository.findAll();
        assertThat(cupomDescontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCupomDesconto() throws Exception {
        int databaseSizeBeforeUpdate = cupomDescontoRepository.findAll().size();
        cupomDesconto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCupomDescontoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cupomDesconto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CupomDesconto in the database
        List<CupomDesconto> cupomDescontoList = cupomDescontoRepository.findAll();
        assertThat(cupomDescontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCupomDescontoWithPatch() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        int databaseSizeBeforeUpdate = cupomDescontoRepository.findAll().size();

        // Update the cupomDesconto using partial update
        CupomDesconto partialUpdatedCupomDesconto = new CupomDesconto();
        partialUpdatedCupomDesconto.setId(cupomDesconto.getId());

        partialUpdatedCupomDesconto.valorMinimo(UPDATED_VALOR_MINIMO).valido(UPDATED_VALIDO);

        restCupomDescontoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCupomDesconto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCupomDesconto))
            )
            .andExpect(status().isOk());

        // Validate the CupomDesconto in the database
        List<CupomDesconto> cupomDescontoList = cupomDescontoRepository.findAll();
        assertThat(cupomDescontoList).hasSize(databaseSizeBeforeUpdate);
        CupomDesconto testCupomDesconto = cupomDescontoList.get(cupomDescontoList.size() - 1);
        assertThat(testCupomDesconto.getValorDesconto()).isEqualByComparingTo(DEFAULT_VALOR_DESCONTO);
        assertThat(testCupomDesconto.getValorMinimo()).isEqualTo(UPDATED_VALOR_MINIMO);
        assertThat(testCupomDesconto.getValorMinimoRegra()).isEqualByComparingTo(DEFAULT_VALOR_MINIMO_REGRA);
        assertThat(testCupomDesconto.getDescricaoRegras()).isEqualTo(DEFAULT_DESCRICAO_REGRAS);
        assertThat(testCupomDesconto.getValido()).isEqualTo(UPDATED_VALIDO);
    }

    @Test
    @Transactional
    void fullUpdateCupomDescontoWithPatch() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        int databaseSizeBeforeUpdate = cupomDescontoRepository.findAll().size();

        // Update the cupomDesconto using partial update
        CupomDesconto partialUpdatedCupomDesconto = new CupomDesconto();
        partialUpdatedCupomDesconto.setId(cupomDesconto.getId());

        partialUpdatedCupomDesconto
            .valorDesconto(UPDATED_VALOR_DESCONTO)
            .valorMinimo(UPDATED_VALOR_MINIMO)
            .valorMinimoRegra(UPDATED_VALOR_MINIMO_REGRA)
            .descricaoRegras(UPDATED_DESCRICAO_REGRAS)
            .valido(UPDATED_VALIDO);

        restCupomDescontoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCupomDesconto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCupomDesconto))
            )
            .andExpect(status().isOk());

        // Validate the CupomDesconto in the database
        List<CupomDesconto> cupomDescontoList = cupomDescontoRepository.findAll();
        assertThat(cupomDescontoList).hasSize(databaseSizeBeforeUpdate);
        CupomDesconto testCupomDesconto = cupomDescontoList.get(cupomDescontoList.size() - 1);
        assertThat(testCupomDesconto.getValorDesconto()).isEqualByComparingTo(UPDATED_VALOR_DESCONTO);
        assertThat(testCupomDesconto.getValorMinimo()).isEqualTo(UPDATED_VALOR_MINIMO);
        assertThat(testCupomDesconto.getValorMinimoRegra()).isEqualByComparingTo(UPDATED_VALOR_MINIMO_REGRA);
        assertThat(testCupomDesconto.getDescricaoRegras()).isEqualTo(UPDATED_DESCRICAO_REGRAS);
        assertThat(testCupomDesconto.getValido()).isEqualTo(UPDATED_VALIDO);
    }

    @Test
    @Transactional
    void patchNonExistingCupomDesconto() throws Exception {
        int databaseSizeBeforeUpdate = cupomDescontoRepository.findAll().size();
        cupomDesconto.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCupomDescontoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cupomDesconto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cupomDesconto))
            )
            .andExpect(status().isBadRequest());

        // Validate the CupomDesconto in the database
        List<CupomDesconto> cupomDescontoList = cupomDescontoRepository.findAll();
        assertThat(cupomDescontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCupomDesconto() throws Exception {
        int databaseSizeBeforeUpdate = cupomDescontoRepository.findAll().size();
        cupomDesconto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCupomDescontoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cupomDesconto))
            )
            .andExpect(status().isBadRequest());

        // Validate the CupomDesconto in the database
        List<CupomDesconto> cupomDescontoList = cupomDescontoRepository.findAll();
        assertThat(cupomDescontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCupomDesconto() throws Exception {
        int databaseSizeBeforeUpdate = cupomDescontoRepository.findAll().size();
        cupomDesconto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCupomDescontoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cupomDesconto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CupomDesconto in the database
        List<CupomDesconto> cupomDescontoList = cupomDescontoRepository.findAll();
        assertThat(cupomDescontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCupomDesconto() throws Exception {
        // Initialize the database
        cupomDescontoRepository.saveAndFlush(cupomDesconto);

        int databaseSizeBeforeDelete = cupomDescontoRepository.findAll().size();

        // Delete the cupomDesconto
        restCupomDescontoMockMvc
            .perform(delete(ENTITY_API_URL_ID, cupomDesconto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CupomDesconto> cupomDescontoList = cupomDescontoRepository.findAll();
        assertThat(cupomDescontoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

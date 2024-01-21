package com.graodireto.mvp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.graodireto.mvp.IntegrationTest;
import com.graodireto.mvp.domain.Cardapio;
import com.graodireto.mvp.domain.Cidade;
import com.graodireto.mvp.domain.CupomDesconto;
import com.graodireto.mvp.domain.Estabelecimento;
import com.graodireto.mvp.domain.Imagens;
import com.graodireto.mvp.domain.enumeration.TipoEstabelicimento;
import com.graodireto.mvp.repository.EstabelecimentoRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
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
 * Integration tests for the {@link EstabelecimentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstabelecimentoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final TipoEstabelicimento DEFAULT_TIPO_ESTABELECIMENTO = TipoEstabelicimento.LANCHES;
    private static final TipoEstabelicimento UPDATED_TIPO_ESTABELECIMENTO = TipoEstabelicimento.SAUDAVEIS;

    private static final byte[] DEFAULT_CAPA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CAPA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CAPA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CAPA_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CRIADO_EM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CRIADO_EM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "AAAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/estabelecimentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstabelecimentoMockMvc;

    private Estabelecimento estabelecimento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estabelecimento createEntity(EntityManager em) {
        Estabelecimento estabelecimento = new Estabelecimento()
            .nome(DEFAULT_NOME)
            .telefone(DEFAULT_TELEFONE)
            .email(DEFAULT_EMAIL)
            .tipoEstabelecimento(DEFAULT_TIPO_ESTABELECIMENTO)
            .capa(DEFAULT_CAPA)
            .capaContentType(DEFAULT_CAPA_CONTENT_TYPE)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .criadoEm(DEFAULT_CRIADO_EM)
            .logradouro(DEFAULT_LOGRADOURO)
            .numero(DEFAULT_NUMERO)
            .complemento(DEFAULT_COMPLEMENTO)
            .bairro(DEFAULT_BAIRRO)
            .cep(DEFAULT_CEP);
        return estabelecimento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estabelecimento createUpdatedEntity(EntityManager em) {
        Estabelecimento estabelecimento = new Estabelecimento()
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE)
            .email(UPDATED_EMAIL)
            .tipoEstabelecimento(UPDATED_TIPO_ESTABELECIMENTO)
            .capa(UPDATED_CAPA)
            .capaContentType(UPDATED_CAPA_CONTENT_TYPE)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .criadoEm(UPDATED_CRIADO_EM)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP);
        return estabelecimento;
    }

    @BeforeEach
    public void initTest() {
        estabelecimento = createEntity(em);
    }

    @Test
    @Transactional
    void createEstabelecimento() throws Exception {
        int databaseSizeBeforeCreate = estabelecimentoRepository.findAll().size();
        // Create the Estabelecimento
        restEstabelecimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isCreated());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeCreate + 1);
        Estabelecimento testEstabelecimento = estabelecimentoList.get(estabelecimentoList.size() - 1);
        assertThat(testEstabelecimento.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEstabelecimento.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testEstabelecimento.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEstabelecimento.getTipoEstabelecimento()).isEqualTo(DEFAULT_TIPO_ESTABELECIMENTO);
        assertThat(testEstabelecimento.getCapa()).isEqualTo(DEFAULT_CAPA);
        assertThat(testEstabelecimento.getCapaContentType()).isEqualTo(DEFAULT_CAPA_CONTENT_TYPE);
        assertThat(testEstabelecimento.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testEstabelecimento.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testEstabelecimento.getCriadoEm()).isEqualTo(DEFAULT_CRIADO_EM);
        assertThat(testEstabelecimento.getLogradouro()).isEqualTo(DEFAULT_LOGRADOURO);
        assertThat(testEstabelecimento.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testEstabelecimento.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
        assertThat(testEstabelecimento.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testEstabelecimento.getCep()).isEqualTo(DEFAULT_CEP);
    }

    @Test
    @Transactional
    void createEstabelecimentoWithExistingId() throws Exception {
        // Create the Estabelecimento with an existing ID
        estabelecimento.setId(1L);

        int databaseSizeBeforeCreate = estabelecimentoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstabelecimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = estabelecimentoRepository.findAll().size();
        // set the field null
        estabelecimento.setNome(null);

        // Create the Estabelecimento, which fails.

        restEstabelecimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isBadRequest());

        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLogradouroIsRequired() throws Exception {
        int databaseSizeBeforeTest = estabelecimentoRepository.findAll().size();
        // set the field null
        estabelecimento.setLogradouro(null);

        // Create the Estabelecimento, which fails.

        restEstabelecimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isBadRequest());

        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBairroIsRequired() throws Exception {
        int databaseSizeBeforeTest = estabelecimentoRepository.findAll().size();
        // set the field null
        estabelecimento.setBairro(null);

        // Create the Estabelecimento, which fails.

        restEstabelecimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isBadRequest());

        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCepIsRequired() throws Exception {
        int databaseSizeBeforeTest = estabelecimentoRepository.findAll().size();
        // set the field null
        estabelecimento.setCep(null);

        // Create the Estabelecimento, which fails.

        restEstabelecimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isBadRequest());

        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEstabelecimentos() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList
        restEstabelecimentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estabelecimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].tipoEstabelecimento").value(hasItem(DEFAULT_TIPO_ESTABELECIMENTO.toString())))
            .andExpect(jsonPath("$.[*].capaContentType").value(hasItem(DEFAULT_CAPA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].capa").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_CAPA))))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].criadoEm").value(hasItem(DEFAULT_CRIADO_EM.toString())))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)));
    }

    @Test
    @Transactional
    void getEstabelecimento() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get the estabelecimento
        restEstabelecimentoMockMvc
            .perform(get(ENTITY_API_URL_ID, estabelecimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estabelecimento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.tipoEstabelecimento").value(DEFAULT_TIPO_ESTABELECIMENTO.toString()))
            .andExpect(jsonPath("$.capaContentType").value(DEFAULT_CAPA_CONTENT_TYPE))
            .andExpect(jsonPath("$.capa").value(Base64.getEncoder().encodeToString(DEFAULT_CAPA)))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64.getEncoder().encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.criadoEm").value(DEFAULT_CRIADO_EM.toString()))
            .andExpect(jsonPath("$.logradouro").value(DEFAULT_LOGRADOURO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP));
    }

    @Test
    @Transactional
    void getEstabelecimentosByIdFiltering() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        Long id = estabelecimento.getId();

        defaultEstabelecimentoShouldBeFound("id.equals=" + id);
        defaultEstabelecimentoShouldNotBeFound("id.notEquals=" + id);

        defaultEstabelecimentoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEstabelecimentoShouldNotBeFound("id.greaterThan=" + id);

        defaultEstabelecimentoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEstabelecimentoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where nome equals to DEFAULT_NOME
        defaultEstabelecimentoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the estabelecimentoList where nome equals to UPDATED_NOME
        defaultEstabelecimentoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultEstabelecimentoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the estabelecimentoList where nome equals to UPDATED_NOME
        defaultEstabelecimentoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where nome is not null
        defaultEstabelecimentoShouldBeFound("nome.specified=true");

        // Get all the estabelecimentoList where nome is null
        defaultEstabelecimentoShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByNomeContainsSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where nome contains DEFAULT_NOME
        defaultEstabelecimentoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the estabelecimentoList where nome contains UPDATED_NOME
        defaultEstabelecimentoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where nome does not contain DEFAULT_NOME
        defaultEstabelecimentoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the estabelecimentoList where nome does not contain UPDATED_NOME
        defaultEstabelecimentoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByTelefoneIsEqualToSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where telefone equals to DEFAULT_TELEFONE
        defaultEstabelecimentoShouldBeFound("telefone.equals=" + DEFAULT_TELEFONE);

        // Get all the estabelecimentoList where telefone equals to UPDATED_TELEFONE
        defaultEstabelecimentoShouldNotBeFound("telefone.equals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByTelefoneIsInShouldWork() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where telefone in DEFAULT_TELEFONE or UPDATED_TELEFONE
        defaultEstabelecimentoShouldBeFound("telefone.in=" + DEFAULT_TELEFONE + "," + UPDATED_TELEFONE);

        // Get all the estabelecimentoList where telefone equals to UPDATED_TELEFONE
        defaultEstabelecimentoShouldNotBeFound("telefone.in=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByTelefoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where telefone is not null
        defaultEstabelecimentoShouldBeFound("telefone.specified=true");

        // Get all the estabelecimentoList where telefone is null
        defaultEstabelecimentoShouldNotBeFound("telefone.specified=false");
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByTelefoneContainsSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where telefone contains DEFAULT_TELEFONE
        defaultEstabelecimentoShouldBeFound("telefone.contains=" + DEFAULT_TELEFONE);

        // Get all the estabelecimentoList where telefone contains UPDATED_TELEFONE
        defaultEstabelecimentoShouldNotBeFound("telefone.contains=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByTelefoneNotContainsSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where telefone does not contain DEFAULT_TELEFONE
        defaultEstabelecimentoShouldNotBeFound("telefone.doesNotContain=" + DEFAULT_TELEFONE);

        // Get all the estabelecimentoList where telefone does not contain UPDATED_TELEFONE
        defaultEstabelecimentoShouldBeFound("telefone.doesNotContain=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where email equals to DEFAULT_EMAIL
        defaultEstabelecimentoShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the estabelecimentoList where email equals to UPDATED_EMAIL
        defaultEstabelecimentoShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultEstabelecimentoShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the estabelecimentoList where email equals to UPDATED_EMAIL
        defaultEstabelecimentoShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where email is not null
        defaultEstabelecimentoShouldBeFound("email.specified=true");

        // Get all the estabelecimentoList where email is null
        defaultEstabelecimentoShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByEmailContainsSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where email contains DEFAULT_EMAIL
        defaultEstabelecimentoShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the estabelecimentoList where email contains UPDATED_EMAIL
        defaultEstabelecimentoShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where email does not contain DEFAULT_EMAIL
        defaultEstabelecimentoShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the estabelecimentoList where email does not contain UPDATED_EMAIL
        defaultEstabelecimentoShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByTipoEstabelecimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where tipoEstabelecimento equals to DEFAULT_TIPO_ESTABELECIMENTO
        defaultEstabelecimentoShouldBeFound("tipoEstabelecimento.equals=" + DEFAULT_TIPO_ESTABELECIMENTO);

        // Get all the estabelecimentoList where tipoEstabelecimento equals to UPDATED_TIPO_ESTABELECIMENTO
        defaultEstabelecimentoShouldNotBeFound("tipoEstabelecimento.equals=" + UPDATED_TIPO_ESTABELECIMENTO);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByTipoEstabelecimentoIsInShouldWork() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where tipoEstabelecimento in DEFAULT_TIPO_ESTABELECIMENTO or UPDATED_TIPO_ESTABELECIMENTO
        defaultEstabelecimentoShouldBeFound("tipoEstabelecimento.in=" + DEFAULT_TIPO_ESTABELECIMENTO + "," + UPDATED_TIPO_ESTABELECIMENTO);

        // Get all the estabelecimentoList where tipoEstabelecimento equals to UPDATED_TIPO_ESTABELECIMENTO
        defaultEstabelecimentoShouldNotBeFound("tipoEstabelecimento.in=" + UPDATED_TIPO_ESTABELECIMENTO);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByTipoEstabelecimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where tipoEstabelecimento is not null
        defaultEstabelecimentoShouldBeFound("tipoEstabelecimento.specified=true");

        // Get all the estabelecimentoList where tipoEstabelecimento is null
        defaultEstabelecimentoShouldNotBeFound("tipoEstabelecimento.specified=false");
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByCriadoEmIsEqualToSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where criadoEm equals to DEFAULT_CRIADO_EM
        defaultEstabelecimentoShouldBeFound("criadoEm.equals=" + DEFAULT_CRIADO_EM);

        // Get all the estabelecimentoList where criadoEm equals to UPDATED_CRIADO_EM
        defaultEstabelecimentoShouldNotBeFound("criadoEm.equals=" + UPDATED_CRIADO_EM);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByCriadoEmIsInShouldWork() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where criadoEm in DEFAULT_CRIADO_EM or UPDATED_CRIADO_EM
        defaultEstabelecimentoShouldBeFound("criadoEm.in=" + DEFAULT_CRIADO_EM + "," + UPDATED_CRIADO_EM);

        // Get all the estabelecimentoList where criadoEm equals to UPDATED_CRIADO_EM
        defaultEstabelecimentoShouldNotBeFound("criadoEm.in=" + UPDATED_CRIADO_EM);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByCriadoEmIsNullOrNotNull() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where criadoEm is not null
        defaultEstabelecimentoShouldBeFound("criadoEm.specified=true");

        // Get all the estabelecimentoList where criadoEm is null
        defaultEstabelecimentoShouldNotBeFound("criadoEm.specified=false");
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByLogradouroIsEqualToSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where logradouro equals to DEFAULT_LOGRADOURO
        defaultEstabelecimentoShouldBeFound("logradouro.equals=" + DEFAULT_LOGRADOURO);

        // Get all the estabelecimentoList where logradouro equals to UPDATED_LOGRADOURO
        defaultEstabelecimentoShouldNotBeFound("logradouro.equals=" + UPDATED_LOGRADOURO);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByLogradouroIsInShouldWork() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where logradouro in DEFAULT_LOGRADOURO or UPDATED_LOGRADOURO
        defaultEstabelecimentoShouldBeFound("logradouro.in=" + DEFAULT_LOGRADOURO + "," + UPDATED_LOGRADOURO);

        // Get all the estabelecimentoList where logradouro equals to UPDATED_LOGRADOURO
        defaultEstabelecimentoShouldNotBeFound("logradouro.in=" + UPDATED_LOGRADOURO);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByLogradouroIsNullOrNotNull() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where logradouro is not null
        defaultEstabelecimentoShouldBeFound("logradouro.specified=true");

        // Get all the estabelecimentoList where logradouro is null
        defaultEstabelecimentoShouldNotBeFound("logradouro.specified=false");
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByLogradouroContainsSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where logradouro contains DEFAULT_LOGRADOURO
        defaultEstabelecimentoShouldBeFound("logradouro.contains=" + DEFAULT_LOGRADOURO);

        // Get all the estabelecimentoList where logradouro contains UPDATED_LOGRADOURO
        defaultEstabelecimentoShouldNotBeFound("logradouro.contains=" + UPDATED_LOGRADOURO);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByLogradouroNotContainsSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where logradouro does not contain DEFAULT_LOGRADOURO
        defaultEstabelecimentoShouldNotBeFound("logradouro.doesNotContain=" + DEFAULT_LOGRADOURO);

        // Get all the estabelecimentoList where logradouro does not contain UPDATED_LOGRADOURO
        defaultEstabelecimentoShouldBeFound("logradouro.doesNotContain=" + UPDATED_LOGRADOURO);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where numero equals to DEFAULT_NUMERO
        defaultEstabelecimentoShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the estabelecimentoList where numero equals to UPDATED_NUMERO
        defaultEstabelecimentoShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultEstabelecimentoShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the estabelecimentoList where numero equals to UPDATED_NUMERO
        defaultEstabelecimentoShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where numero is not null
        defaultEstabelecimentoShouldBeFound("numero.specified=true");

        // Get all the estabelecimentoList where numero is null
        defaultEstabelecimentoShouldNotBeFound("numero.specified=false");
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByNumeroContainsSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where numero contains DEFAULT_NUMERO
        defaultEstabelecimentoShouldBeFound("numero.contains=" + DEFAULT_NUMERO);

        // Get all the estabelecimentoList where numero contains UPDATED_NUMERO
        defaultEstabelecimentoShouldNotBeFound("numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where numero does not contain DEFAULT_NUMERO
        defaultEstabelecimentoShouldNotBeFound("numero.doesNotContain=" + DEFAULT_NUMERO);

        // Get all the estabelecimentoList where numero does not contain UPDATED_NUMERO
        defaultEstabelecimentoShouldBeFound("numero.doesNotContain=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByComplementoIsEqualToSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where complemento equals to DEFAULT_COMPLEMENTO
        defaultEstabelecimentoShouldBeFound("complemento.equals=" + DEFAULT_COMPLEMENTO);

        // Get all the estabelecimentoList where complemento equals to UPDATED_COMPLEMENTO
        defaultEstabelecimentoShouldNotBeFound("complemento.equals=" + UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByComplementoIsInShouldWork() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where complemento in DEFAULT_COMPLEMENTO or UPDATED_COMPLEMENTO
        defaultEstabelecimentoShouldBeFound("complemento.in=" + DEFAULT_COMPLEMENTO + "," + UPDATED_COMPLEMENTO);

        // Get all the estabelecimentoList where complemento equals to UPDATED_COMPLEMENTO
        defaultEstabelecimentoShouldNotBeFound("complemento.in=" + UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByComplementoIsNullOrNotNull() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where complemento is not null
        defaultEstabelecimentoShouldBeFound("complemento.specified=true");

        // Get all the estabelecimentoList where complemento is null
        defaultEstabelecimentoShouldNotBeFound("complemento.specified=false");
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByComplementoContainsSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where complemento contains DEFAULT_COMPLEMENTO
        defaultEstabelecimentoShouldBeFound("complemento.contains=" + DEFAULT_COMPLEMENTO);

        // Get all the estabelecimentoList where complemento contains UPDATED_COMPLEMENTO
        defaultEstabelecimentoShouldNotBeFound("complemento.contains=" + UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByComplementoNotContainsSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where complemento does not contain DEFAULT_COMPLEMENTO
        defaultEstabelecimentoShouldNotBeFound("complemento.doesNotContain=" + DEFAULT_COMPLEMENTO);

        // Get all the estabelecimentoList where complemento does not contain UPDATED_COMPLEMENTO
        defaultEstabelecimentoShouldBeFound("complemento.doesNotContain=" + UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByBairroIsEqualToSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where bairro equals to DEFAULT_BAIRRO
        defaultEstabelecimentoShouldBeFound("bairro.equals=" + DEFAULT_BAIRRO);

        // Get all the estabelecimentoList where bairro equals to UPDATED_BAIRRO
        defaultEstabelecimentoShouldNotBeFound("bairro.equals=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByBairroIsInShouldWork() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where bairro in DEFAULT_BAIRRO or UPDATED_BAIRRO
        defaultEstabelecimentoShouldBeFound("bairro.in=" + DEFAULT_BAIRRO + "," + UPDATED_BAIRRO);

        // Get all the estabelecimentoList where bairro equals to UPDATED_BAIRRO
        defaultEstabelecimentoShouldNotBeFound("bairro.in=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByBairroIsNullOrNotNull() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where bairro is not null
        defaultEstabelecimentoShouldBeFound("bairro.specified=true");

        // Get all the estabelecimentoList where bairro is null
        defaultEstabelecimentoShouldNotBeFound("bairro.specified=false");
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByBairroContainsSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where bairro contains DEFAULT_BAIRRO
        defaultEstabelecimentoShouldBeFound("bairro.contains=" + DEFAULT_BAIRRO);

        // Get all the estabelecimentoList where bairro contains UPDATED_BAIRRO
        defaultEstabelecimentoShouldNotBeFound("bairro.contains=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByBairroNotContainsSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where bairro does not contain DEFAULT_BAIRRO
        defaultEstabelecimentoShouldNotBeFound("bairro.doesNotContain=" + DEFAULT_BAIRRO);

        // Get all the estabelecimentoList where bairro does not contain UPDATED_BAIRRO
        defaultEstabelecimentoShouldBeFound("bairro.doesNotContain=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByCepIsEqualToSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where cep equals to DEFAULT_CEP
        defaultEstabelecimentoShouldBeFound("cep.equals=" + DEFAULT_CEP);

        // Get all the estabelecimentoList where cep equals to UPDATED_CEP
        defaultEstabelecimentoShouldNotBeFound("cep.equals=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByCepIsInShouldWork() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where cep in DEFAULT_CEP or UPDATED_CEP
        defaultEstabelecimentoShouldBeFound("cep.in=" + DEFAULT_CEP + "," + UPDATED_CEP);

        // Get all the estabelecimentoList where cep equals to UPDATED_CEP
        defaultEstabelecimentoShouldNotBeFound("cep.in=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByCepIsNullOrNotNull() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where cep is not null
        defaultEstabelecimentoShouldBeFound("cep.specified=true");

        // Get all the estabelecimentoList where cep is null
        defaultEstabelecimentoShouldNotBeFound("cep.specified=false");
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByCepContainsSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where cep contains DEFAULT_CEP
        defaultEstabelecimentoShouldBeFound("cep.contains=" + DEFAULT_CEP);

        // Get all the estabelecimentoList where cep contains UPDATED_CEP
        defaultEstabelecimentoShouldNotBeFound("cep.contains=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByCepNotContainsSomething() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList where cep does not contain DEFAULT_CEP
        defaultEstabelecimentoShouldNotBeFound("cep.doesNotContain=" + DEFAULT_CEP);

        // Get all the estabelecimentoList where cep does not contain UPDATED_CEP
        defaultEstabelecimentoShouldBeFound("cep.doesNotContain=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByCardapioIsEqualToSomething() throws Exception {
        Cardapio cardapio;
        if (TestUtil.findAll(em, Cardapio.class).isEmpty()) {
            estabelecimentoRepository.saveAndFlush(estabelecimento);
            cardapio = CardapioResourceIT.createEntity(em);
        } else {
            cardapio = TestUtil.findAll(em, Cardapio.class).get(0);
        }
        em.persist(cardapio);
        em.flush();
        estabelecimento.addCardapio(cardapio);
        estabelecimentoRepository.saveAndFlush(estabelecimento);
        Long cardapioId = cardapio.getId();
        // Get all the estabelecimentoList where cardapio equals to cardapioId
        defaultEstabelecimentoShouldBeFound("cardapioId.equals=" + cardapioId);

        // Get all the estabelecimentoList where cardapio equals to (cardapioId + 1)
        defaultEstabelecimentoShouldNotBeFound("cardapioId.equals=" + (cardapioId + 1));
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByImagensIsEqualToSomething() throws Exception {
        Imagens imagens;
        if (TestUtil.findAll(em, Imagens.class).isEmpty()) {
            estabelecimentoRepository.saveAndFlush(estabelecimento);
            imagens = ImagensResourceIT.createEntity(em);
        } else {
            imagens = TestUtil.findAll(em, Imagens.class).get(0);
        }
        em.persist(imagens);
        em.flush();
        estabelecimento.addImagens(imagens);
        estabelecimentoRepository.saveAndFlush(estabelecimento);
        Long imagensId = imagens.getId();
        // Get all the estabelecimentoList where imagens equals to imagensId
        defaultEstabelecimentoShouldBeFound("imagensId.equals=" + imagensId);

        // Get all the estabelecimentoList where imagens equals to (imagensId + 1)
        defaultEstabelecimentoShouldNotBeFound("imagensId.equals=" + (imagensId + 1));
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByCupomDescontoIsEqualToSomething() throws Exception {
        CupomDesconto cupomDesconto;
        if (TestUtil.findAll(em, CupomDesconto.class).isEmpty()) {
            estabelecimentoRepository.saveAndFlush(estabelecimento);
            cupomDesconto = CupomDescontoResourceIT.createEntity(em);
        } else {
            cupomDesconto = TestUtil.findAll(em, CupomDesconto.class).get(0);
        }
        em.persist(cupomDesconto);
        em.flush();
        estabelecimento.addCupomDesconto(cupomDesconto);
        estabelecimentoRepository.saveAndFlush(estabelecimento);
        Long cupomDescontoId = cupomDesconto.getId();
        // Get all the estabelecimentoList where cupomDesconto equals to cupomDescontoId
        defaultEstabelecimentoShouldBeFound("cupomDescontoId.equals=" + cupomDescontoId);

        // Get all the estabelecimentoList where cupomDesconto equals to (cupomDescontoId + 1)
        defaultEstabelecimentoShouldNotBeFound("cupomDescontoId.equals=" + (cupomDescontoId + 1));
    }

    @Test
    @Transactional
    void getAllEstabelecimentosByCidadeIsEqualToSomething() throws Exception {
        Cidade cidade;
        if (TestUtil.findAll(em, Cidade.class).isEmpty()) {
            estabelecimentoRepository.saveAndFlush(estabelecimento);
            cidade = CidadeResourceIT.createEntity(em);
        } else {
            cidade = TestUtil.findAll(em, Cidade.class).get(0);
        }
        em.persist(cidade);
        em.flush();
        estabelecimento.setCidade(cidade);
        estabelecimentoRepository.saveAndFlush(estabelecimento);
        Long cidadeId = cidade.getId();
        // Get all the estabelecimentoList where cidade equals to cidadeId
        defaultEstabelecimentoShouldBeFound("cidadeId.equals=" + cidadeId);

        // Get all the estabelecimentoList where cidade equals to (cidadeId + 1)
        defaultEstabelecimentoShouldNotBeFound("cidadeId.equals=" + (cidadeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEstabelecimentoShouldBeFound(String filter) throws Exception {
        restEstabelecimentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estabelecimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].tipoEstabelecimento").value(hasItem(DEFAULT_TIPO_ESTABELECIMENTO.toString())))
            .andExpect(jsonPath("$.[*].capaContentType").value(hasItem(DEFAULT_CAPA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].capa").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_CAPA))))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].criadoEm").value(hasItem(DEFAULT_CRIADO_EM.toString())))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)));

        // Check, that the count call also returns 1
        restEstabelecimentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEstabelecimentoShouldNotBeFound(String filter) throws Exception {
        restEstabelecimentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEstabelecimentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEstabelecimento() throws Exception {
        // Get the estabelecimento
        restEstabelecimentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEstabelecimento() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();

        // Update the estabelecimento
        Estabelecimento updatedEstabelecimento = estabelecimentoRepository.findById(estabelecimento.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEstabelecimento are not directly saved in db
        em.detach(updatedEstabelecimento);
        updatedEstabelecimento
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE)
            .email(UPDATED_EMAIL)
            .tipoEstabelecimento(UPDATED_TIPO_ESTABELECIMENTO)
            .capa(UPDATED_CAPA)
            .capaContentType(UPDATED_CAPA_CONTENT_TYPE)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .criadoEm(UPDATED_CRIADO_EM)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP);

        restEstabelecimentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEstabelecimento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEstabelecimento))
            )
            .andExpect(status().isOk());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
        Estabelecimento testEstabelecimento = estabelecimentoList.get(estabelecimentoList.size() - 1);
        assertThat(testEstabelecimento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEstabelecimento.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testEstabelecimento.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEstabelecimento.getTipoEstabelecimento()).isEqualTo(UPDATED_TIPO_ESTABELECIMENTO);
        assertThat(testEstabelecimento.getCapa()).isEqualTo(UPDATED_CAPA);
        assertThat(testEstabelecimento.getCapaContentType()).isEqualTo(UPDATED_CAPA_CONTENT_TYPE);
        assertThat(testEstabelecimento.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testEstabelecimento.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testEstabelecimento.getCriadoEm()).isEqualTo(UPDATED_CRIADO_EM);
        assertThat(testEstabelecimento.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testEstabelecimento.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEstabelecimento.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testEstabelecimento.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testEstabelecimento.getCep()).isEqualTo(UPDATED_CEP);
    }

    @Test
    @Transactional
    void putNonExistingEstabelecimento() throws Exception {
        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();
        estabelecimento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstabelecimentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estabelecimento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstabelecimento() throws Exception {
        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();
        estabelecimento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstabelecimentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstabelecimento() throws Exception {
        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();
        estabelecimento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstabelecimentoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstabelecimentoWithPatch() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();

        // Update the estabelecimento using partial update
        Estabelecimento partialUpdatedEstabelecimento = new Estabelecimento();
        partialUpdatedEstabelecimento.setId(estabelecimento.getId());

        partialUpdatedEstabelecimento
            .nome(UPDATED_NOME)
            .tipoEstabelecimento(UPDATED_TIPO_ESTABELECIMENTO)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .criadoEm(UPDATED_CRIADO_EM)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .cep(UPDATED_CEP);

        restEstabelecimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstabelecimento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstabelecimento))
            )
            .andExpect(status().isOk());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
        Estabelecimento testEstabelecimento = estabelecimentoList.get(estabelecimentoList.size() - 1);
        assertThat(testEstabelecimento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEstabelecimento.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testEstabelecimento.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEstabelecimento.getTipoEstabelecimento()).isEqualTo(UPDATED_TIPO_ESTABELECIMENTO);
        assertThat(testEstabelecimento.getCapa()).isEqualTo(DEFAULT_CAPA);
        assertThat(testEstabelecimento.getCapaContentType()).isEqualTo(DEFAULT_CAPA_CONTENT_TYPE);
        assertThat(testEstabelecimento.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testEstabelecimento.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testEstabelecimento.getCriadoEm()).isEqualTo(UPDATED_CRIADO_EM);
        assertThat(testEstabelecimento.getLogradouro()).isEqualTo(DEFAULT_LOGRADOURO);
        assertThat(testEstabelecimento.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEstabelecimento.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testEstabelecimento.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testEstabelecimento.getCep()).isEqualTo(UPDATED_CEP);
    }

    @Test
    @Transactional
    void fullUpdateEstabelecimentoWithPatch() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();

        // Update the estabelecimento using partial update
        Estabelecimento partialUpdatedEstabelecimento = new Estabelecimento();
        partialUpdatedEstabelecimento.setId(estabelecimento.getId());

        partialUpdatedEstabelecimento
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE)
            .email(UPDATED_EMAIL)
            .tipoEstabelecimento(UPDATED_TIPO_ESTABELECIMENTO)
            .capa(UPDATED_CAPA)
            .capaContentType(UPDATED_CAPA_CONTENT_TYPE)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .criadoEm(UPDATED_CRIADO_EM)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP);

        restEstabelecimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstabelecimento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstabelecimento))
            )
            .andExpect(status().isOk());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
        Estabelecimento testEstabelecimento = estabelecimentoList.get(estabelecimentoList.size() - 1);
        assertThat(testEstabelecimento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEstabelecimento.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testEstabelecimento.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEstabelecimento.getTipoEstabelecimento()).isEqualTo(UPDATED_TIPO_ESTABELECIMENTO);
        assertThat(testEstabelecimento.getCapa()).isEqualTo(UPDATED_CAPA);
        assertThat(testEstabelecimento.getCapaContentType()).isEqualTo(UPDATED_CAPA_CONTENT_TYPE);
        assertThat(testEstabelecimento.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testEstabelecimento.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testEstabelecimento.getCriadoEm()).isEqualTo(UPDATED_CRIADO_EM);
        assertThat(testEstabelecimento.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testEstabelecimento.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEstabelecimento.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testEstabelecimento.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testEstabelecimento.getCep()).isEqualTo(UPDATED_CEP);
    }

    @Test
    @Transactional
    void patchNonExistingEstabelecimento() throws Exception {
        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();
        estabelecimento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstabelecimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estabelecimento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstabelecimento() throws Exception {
        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();
        estabelecimento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstabelecimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstabelecimento() throws Exception {
        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();
        estabelecimento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstabelecimentoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstabelecimento() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        int databaseSizeBeforeDelete = estabelecimentoRepository.findAll().size();

        // Delete the estabelecimento
        restEstabelecimentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, estabelecimento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

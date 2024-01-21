package com.graodireto.mvp.web.rest;

import static com.graodireto.mvp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.graodireto.mvp.IntegrationTest;
import com.graodireto.mvp.domain.Cardapio;
import com.graodireto.mvp.domain.CategoriaProduto;
import com.graodireto.mvp.domain.Produto;
import com.graodireto.mvp.repository.ProdutoRepository;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link ProdutoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProdutoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRECO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECO = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRECO = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_DESCONTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_DESCONTO = new BigDecimal(2);
    private static final BigDecimal SMALLER_DESCONTO = new BigDecimal(1 - 1);

    private static final byte[] DEFAULT_IMAGEM_PRODUTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEM_PRODUTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEM_PRODUTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEM_PRODUTO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/produtos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProdutoMockMvc;

    private Produto produto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produto createEntity(EntityManager em) {
        Produto produto = new Produto()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .preco(DEFAULT_PRECO)
            .desconto(DEFAULT_DESCONTO)
            .imagemProduto(DEFAULT_IMAGEM_PRODUTO)
            .imagemProdutoContentType(DEFAULT_IMAGEM_PRODUTO_CONTENT_TYPE);
        return produto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produto createUpdatedEntity(EntityManager em) {
        Produto produto = new Produto()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .preco(UPDATED_PRECO)
            .desconto(UPDATED_DESCONTO)
            .imagemProduto(UPDATED_IMAGEM_PRODUTO)
            .imagemProdutoContentType(UPDATED_IMAGEM_PRODUTO_CONTENT_TYPE);
        return produto;
    }

    @BeforeEach
    public void initTest() {
        produto = createEntity(em);
    }

    @Test
    @Transactional
    void createProduto() throws Exception {
        int databaseSizeBeforeCreate = produtoRepository.findAll().size();
        // Create the Produto
        restProdutoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isCreated());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeCreate + 1);
        Produto testProduto = produtoList.get(produtoList.size() - 1);
        assertThat(testProduto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProduto.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testProduto.getPreco()).isEqualByComparingTo(DEFAULT_PRECO);
        assertThat(testProduto.getDesconto()).isEqualByComparingTo(DEFAULT_DESCONTO);
        assertThat(testProduto.getImagemProduto()).isEqualTo(DEFAULT_IMAGEM_PRODUTO);
        assertThat(testProduto.getImagemProdutoContentType()).isEqualTo(DEFAULT_IMAGEM_PRODUTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createProdutoWithExistingId() throws Exception {
        // Create the Produto with an existing ID
        produto.setId(1L);

        int databaseSizeBeforeCreate = produtoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdutoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isBadRequest());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoRepository.findAll().size();
        // set the field null
        produto.setNome(null);

        // Create the Produto, which fails.

        restProdutoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isBadRequest());

        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoRepository.findAll().size();
        // set the field null
        produto.setPreco(null);

        // Create the Produto, which fails.

        restProdutoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isBadRequest());

        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProdutos() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList
        restProdutoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].preco").value(hasItem(sameNumber(DEFAULT_PRECO))))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(sameNumber(DEFAULT_DESCONTO))))
            .andExpect(jsonPath("$.[*].imagemProdutoContentType").value(hasItem(DEFAULT_IMAGEM_PRODUTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagemProduto").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEM_PRODUTO))));
    }

    @Test
    @Transactional
    void getProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get the produto
        restProdutoMockMvc
            .perform(get(ENTITY_API_URL_ID, produto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(produto.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.preco").value(sameNumber(DEFAULT_PRECO)))
            .andExpect(jsonPath("$.desconto").value(sameNumber(DEFAULT_DESCONTO)))
            .andExpect(jsonPath("$.imagemProdutoContentType").value(DEFAULT_IMAGEM_PRODUTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagemProduto").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGEM_PRODUTO)));
    }

    @Test
    @Transactional
    void getProdutosByIdFiltering() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        Long id = produto.getId();

        defaultProdutoShouldBeFound("id.equals=" + id);
        defaultProdutoShouldNotBeFound("id.notEquals=" + id);

        defaultProdutoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProdutoShouldNotBeFound("id.greaterThan=" + id);

        defaultProdutoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProdutoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProdutosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where nome equals to DEFAULT_NOME
        defaultProdutoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the produtoList where nome equals to UPDATED_NOME
        defaultProdutoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllProdutosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultProdutoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the produtoList where nome equals to UPDATED_NOME
        defaultProdutoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllProdutosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where nome is not null
        defaultProdutoShouldBeFound("nome.specified=true");

        // Get all the produtoList where nome is null
        defaultProdutoShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllProdutosByNomeContainsSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where nome contains DEFAULT_NOME
        defaultProdutoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the produtoList where nome contains UPDATED_NOME
        defaultProdutoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllProdutosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where nome does not contain DEFAULT_NOME
        defaultProdutoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the produtoList where nome does not contain UPDATED_NOME
        defaultProdutoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllProdutosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where descricao equals to DEFAULT_DESCRICAO
        defaultProdutoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the produtoList where descricao equals to UPDATED_DESCRICAO
        defaultProdutoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllProdutosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultProdutoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the produtoList where descricao equals to UPDATED_DESCRICAO
        defaultProdutoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllProdutosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where descricao is not null
        defaultProdutoShouldBeFound("descricao.specified=true");

        // Get all the produtoList where descricao is null
        defaultProdutoShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllProdutosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where descricao contains DEFAULT_DESCRICAO
        defaultProdutoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the produtoList where descricao contains UPDATED_DESCRICAO
        defaultProdutoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllProdutosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where descricao does not contain DEFAULT_DESCRICAO
        defaultProdutoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the produtoList where descricao does not contain UPDATED_DESCRICAO
        defaultProdutoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllProdutosByPrecoIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where preco equals to DEFAULT_PRECO
        defaultProdutoShouldBeFound("preco.equals=" + DEFAULT_PRECO);

        // Get all the produtoList where preco equals to UPDATED_PRECO
        defaultProdutoShouldNotBeFound("preco.equals=" + UPDATED_PRECO);
    }

    @Test
    @Transactional
    void getAllProdutosByPrecoIsInShouldWork() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where preco in DEFAULT_PRECO or UPDATED_PRECO
        defaultProdutoShouldBeFound("preco.in=" + DEFAULT_PRECO + "," + UPDATED_PRECO);

        // Get all the produtoList where preco equals to UPDATED_PRECO
        defaultProdutoShouldNotBeFound("preco.in=" + UPDATED_PRECO);
    }

    @Test
    @Transactional
    void getAllProdutosByPrecoIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where preco is not null
        defaultProdutoShouldBeFound("preco.specified=true");

        // Get all the produtoList where preco is null
        defaultProdutoShouldNotBeFound("preco.specified=false");
    }

    @Test
    @Transactional
    void getAllProdutosByPrecoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where preco is greater than or equal to DEFAULT_PRECO
        defaultProdutoShouldBeFound("preco.greaterThanOrEqual=" + DEFAULT_PRECO);

        // Get all the produtoList where preco is greater than or equal to UPDATED_PRECO
        defaultProdutoShouldNotBeFound("preco.greaterThanOrEqual=" + UPDATED_PRECO);
    }

    @Test
    @Transactional
    void getAllProdutosByPrecoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where preco is less than or equal to DEFAULT_PRECO
        defaultProdutoShouldBeFound("preco.lessThanOrEqual=" + DEFAULT_PRECO);

        // Get all the produtoList where preco is less than or equal to SMALLER_PRECO
        defaultProdutoShouldNotBeFound("preco.lessThanOrEqual=" + SMALLER_PRECO);
    }

    @Test
    @Transactional
    void getAllProdutosByPrecoIsLessThanSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where preco is less than DEFAULT_PRECO
        defaultProdutoShouldNotBeFound("preco.lessThan=" + DEFAULT_PRECO);

        // Get all the produtoList where preco is less than UPDATED_PRECO
        defaultProdutoShouldBeFound("preco.lessThan=" + UPDATED_PRECO);
    }

    @Test
    @Transactional
    void getAllProdutosByPrecoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where preco is greater than DEFAULT_PRECO
        defaultProdutoShouldNotBeFound("preco.greaterThan=" + DEFAULT_PRECO);

        // Get all the produtoList where preco is greater than SMALLER_PRECO
        defaultProdutoShouldBeFound("preco.greaterThan=" + SMALLER_PRECO);
    }

    @Test
    @Transactional
    void getAllProdutosByDescontoIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where desconto equals to DEFAULT_DESCONTO
        defaultProdutoShouldBeFound("desconto.equals=" + DEFAULT_DESCONTO);

        // Get all the produtoList where desconto equals to UPDATED_DESCONTO
        defaultProdutoShouldNotBeFound("desconto.equals=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    void getAllProdutosByDescontoIsInShouldWork() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where desconto in DEFAULT_DESCONTO or UPDATED_DESCONTO
        defaultProdutoShouldBeFound("desconto.in=" + DEFAULT_DESCONTO + "," + UPDATED_DESCONTO);

        // Get all the produtoList where desconto equals to UPDATED_DESCONTO
        defaultProdutoShouldNotBeFound("desconto.in=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    void getAllProdutosByDescontoIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where desconto is not null
        defaultProdutoShouldBeFound("desconto.specified=true");

        // Get all the produtoList where desconto is null
        defaultProdutoShouldNotBeFound("desconto.specified=false");
    }

    @Test
    @Transactional
    void getAllProdutosByDescontoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where desconto is greater than or equal to DEFAULT_DESCONTO
        defaultProdutoShouldBeFound("desconto.greaterThanOrEqual=" + DEFAULT_DESCONTO);

        // Get all the produtoList where desconto is greater than or equal to UPDATED_DESCONTO
        defaultProdutoShouldNotBeFound("desconto.greaterThanOrEqual=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    void getAllProdutosByDescontoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where desconto is less than or equal to DEFAULT_DESCONTO
        defaultProdutoShouldBeFound("desconto.lessThanOrEqual=" + DEFAULT_DESCONTO);

        // Get all the produtoList where desconto is less than or equal to SMALLER_DESCONTO
        defaultProdutoShouldNotBeFound("desconto.lessThanOrEqual=" + SMALLER_DESCONTO);
    }

    @Test
    @Transactional
    void getAllProdutosByDescontoIsLessThanSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where desconto is less than DEFAULT_DESCONTO
        defaultProdutoShouldNotBeFound("desconto.lessThan=" + DEFAULT_DESCONTO);

        // Get all the produtoList where desconto is less than UPDATED_DESCONTO
        defaultProdutoShouldBeFound("desconto.lessThan=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    void getAllProdutosByDescontoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where desconto is greater than DEFAULT_DESCONTO
        defaultProdutoShouldNotBeFound("desconto.greaterThan=" + DEFAULT_DESCONTO);

        // Get all the produtoList where desconto is greater than SMALLER_DESCONTO
        defaultProdutoShouldBeFound("desconto.greaterThan=" + SMALLER_DESCONTO);
    }

    @Test
    @Transactional
    void getAllProdutosByCategoriaProdutoIsEqualToSomething() throws Exception {
        CategoriaProduto categoriaProduto;
        if (TestUtil.findAll(em, CategoriaProduto.class).isEmpty()) {
            produtoRepository.saveAndFlush(produto);
            categoriaProduto = CategoriaProdutoResourceIT.createEntity(em);
        } else {
            categoriaProduto = TestUtil.findAll(em, CategoriaProduto.class).get(0);
        }
        em.persist(categoriaProduto);
        em.flush();
        produto.setCategoriaProduto(categoriaProduto);
        produtoRepository.saveAndFlush(produto);
        Long categoriaProdutoId = categoriaProduto.getId();
        // Get all the produtoList where categoriaProduto equals to categoriaProdutoId
        defaultProdutoShouldBeFound("categoriaProdutoId.equals=" + categoriaProdutoId);

        // Get all the produtoList where categoriaProduto equals to (categoriaProdutoId + 1)
        defaultProdutoShouldNotBeFound("categoriaProdutoId.equals=" + (categoriaProdutoId + 1));
    }

    @Test
    @Transactional
    void getAllProdutosByCardapioIsEqualToSomething() throws Exception {
        Cardapio cardapio;
        if (TestUtil.findAll(em, Cardapio.class).isEmpty()) {
            produtoRepository.saveAndFlush(produto);
            cardapio = CardapioResourceIT.createEntity(em);
        } else {
            cardapio = TestUtil.findAll(em, Cardapio.class).get(0);
        }
        em.persist(cardapio);
        em.flush();
        produto.setCardapio(cardapio);
        produtoRepository.saveAndFlush(produto);
        Long cardapioId = cardapio.getId();
        // Get all the produtoList where cardapio equals to cardapioId
        defaultProdutoShouldBeFound("cardapioId.equals=" + cardapioId);

        // Get all the produtoList where cardapio equals to (cardapioId + 1)
        defaultProdutoShouldNotBeFound("cardapioId.equals=" + (cardapioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProdutoShouldBeFound(String filter) throws Exception {
        restProdutoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].preco").value(hasItem(sameNumber(DEFAULT_PRECO))))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(sameNumber(DEFAULT_DESCONTO))))
            .andExpect(jsonPath("$.[*].imagemProdutoContentType").value(hasItem(DEFAULT_IMAGEM_PRODUTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagemProduto").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEM_PRODUTO))));

        // Check, that the count call also returns 1
        restProdutoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProdutoShouldNotBeFound(String filter) throws Exception {
        restProdutoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProdutoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProduto() throws Exception {
        // Get the produto
        restProdutoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // Update the produto
        Produto updatedProduto = produtoRepository.findById(produto.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProduto are not directly saved in db
        em.detach(updatedProduto);
        updatedProduto
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .preco(UPDATED_PRECO)
            .desconto(UPDATED_DESCONTO)
            .imagemProduto(UPDATED_IMAGEM_PRODUTO)
            .imagemProdutoContentType(UPDATED_IMAGEM_PRODUTO_CONTENT_TYPE);

        restProdutoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProduto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProduto))
            )
            .andExpect(status().isOk());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
        Produto testProduto = produtoList.get(produtoList.size() - 1);
        assertThat(testProduto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProduto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testProduto.getPreco()).isEqualByComparingTo(UPDATED_PRECO);
        assertThat(testProduto.getDesconto()).isEqualByComparingTo(UPDATED_DESCONTO);
        assertThat(testProduto.getImagemProduto()).isEqualTo(UPDATED_IMAGEM_PRODUTO);
        assertThat(testProduto.getImagemProdutoContentType()).isEqualTo(UPDATED_IMAGEM_PRODUTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingProduto() throws Exception {
        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();
        produto.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdutoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, produto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProduto() throws Exception {
        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();
        produto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdutoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProduto() throws Exception {
        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();
        produto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdutoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProdutoWithPatch() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // Update the produto using partial update
        Produto partialUpdatedProduto = new Produto();
        partialUpdatedProduto.setId(produto.getId());

        partialUpdatedProduto
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .preco(UPDATED_PRECO)
            .desconto(UPDATED_DESCONTO)
            .imagemProduto(UPDATED_IMAGEM_PRODUTO)
            .imagemProdutoContentType(UPDATED_IMAGEM_PRODUTO_CONTENT_TYPE);

        restProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduto))
            )
            .andExpect(status().isOk());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
        Produto testProduto = produtoList.get(produtoList.size() - 1);
        assertThat(testProduto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProduto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testProduto.getPreco()).isEqualByComparingTo(UPDATED_PRECO);
        assertThat(testProduto.getDesconto()).isEqualByComparingTo(UPDATED_DESCONTO);
        assertThat(testProduto.getImagemProduto()).isEqualTo(UPDATED_IMAGEM_PRODUTO);
        assertThat(testProduto.getImagemProdutoContentType()).isEqualTo(UPDATED_IMAGEM_PRODUTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateProdutoWithPatch() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // Update the produto using partial update
        Produto partialUpdatedProduto = new Produto();
        partialUpdatedProduto.setId(produto.getId());

        partialUpdatedProduto
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .preco(UPDATED_PRECO)
            .desconto(UPDATED_DESCONTO)
            .imagemProduto(UPDATED_IMAGEM_PRODUTO)
            .imagemProdutoContentType(UPDATED_IMAGEM_PRODUTO_CONTENT_TYPE);

        restProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduto))
            )
            .andExpect(status().isOk());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
        Produto testProduto = produtoList.get(produtoList.size() - 1);
        assertThat(testProduto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProduto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testProduto.getPreco()).isEqualByComparingTo(UPDATED_PRECO);
        assertThat(testProduto.getDesconto()).isEqualByComparingTo(UPDATED_DESCONTO);
        assertThat(testProduto.getImagemProduto()).isEqualTo(UPDATED_IMAGEM_PRODUTO);
        assertThat(testProduto.getImagemProdutoContentType()).isEqualTo(UPDATED_IMAGEM_PRODUTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingProduto() throws Exception {
        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();
        produto.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, produto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(produto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProduto() throws Exception {
        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();
        produto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(produto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProduto() throws Exception {
        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();
        produto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdutoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        int databaseSizeBeforeDelete = produtoRepository.findAll().size();

        // Delete the produto
        restProdutoMockMvc
            .perform(delete(ENTITY_API_URL_ID, produto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

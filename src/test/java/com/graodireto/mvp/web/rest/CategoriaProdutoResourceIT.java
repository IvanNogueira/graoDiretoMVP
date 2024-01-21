package com.graodireto.mvp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.graodireto.mvp.IntegrationTest;
import com.graodireto.mvp.domain.CategoriaProduto;
import com.graodireto.mvp.repository.CategoriaProdutoRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link CategoriaProdutoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategoriaProdutoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/categoria-produtos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriaProdutoMockMvc;

    private CategoriaProduto categoriaProduto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaProduto createEntity(EntityManager em) {
        CategoriaProduto categoriaProduto = new CategoriaProduto().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO);
        return categoriaProduto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaProduto createUpdatedEntity(EntityManager em) {
        CategoriaProduto categoriaProduto = new CategoriaProduto().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        return categoriaProduto;
    }

    @BeforeEach
    public void initTest() {
        categoriaProduto = createEntity(em);
    }

    @Test
    @Transactional
    void createCategoriaProduto() throws Exception {
        int databaseSizeBeforeCreate = categoriaProdutoRepository.findAll().size();
        // Create the CategoriaProduto
        restCategoriaProdutoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoriaProduto))
            )
            .andExpect(status().isCreated());

        // Validate the CategoriaProduto in the database
        List<CategoriaProduto> categoriaProdutoList = categoriaProdutoRepository.findAll();
        assertThat(categoriaProdutoList).hasSize(databaseSizeBeforeCreate + 1);
        CategoriaProduto testCategoriaProduto = categoriaProdutoList.get(categoriaProdutoList.size() - 1);
        assertThat(testCategoriaProduto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCategoriaProduto.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createCategoriaProdutoWithExistingId() throws Exception {
        // Create the CategoriaProduto with an existing ID
        categoriaProduto.setId(1L);

        int databaseSizeBeforeCreate = categoriaProdutoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaProdutoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoriaProduto))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaProduto in the database
        List<CategoriaProduto> categoriaProdutoList = categoriaProdutoRepository.findAll();
        assertThat(categoriaProdutoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriaProdutoRepository.findAll().size();
        // set the field null
        categoriaProduto.setNome(null);

        // Create the CategoriaProduto, which fails.

        restCategoriaProdutoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoriaProduto))
            )
            .andExpect(status().isBadRequest());

        List<CategoriaProduto> categoriaProdutoList = categoriaProdutoRepository.findAll();
        assertThat(categoriaProdutoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCategoriaProdutos() throws Exception {
        // Initialize the database
        categoriaProdutoRepository.saveAndFlush(categoriaProduto);

        // Get all the categoriaProdutoList
        restCategoriaProdutoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaProduto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getCategoriaProduto() throws Exception {
        // Initialize the database
        categoriaProdutoRepository.saveAndFlush(categoriaProduto);

        // Get the categoriaProduto
        restCategoriaProdutoMockMvc
            .perform(get(ENTITY_API_URL_ID, categoriaProduto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoriaProduto.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getCategoriaProdutosByIdFiltering() throws Exception {
        // Initialize the database
        categoriaProdutoRepository.saveAndFlush(categoriaProduto);

        Long id = categoriaProduto.getId();

        defaultCategoriaProdutoShouldBeFound("id.equals=" + id);
        defaultCategoriaProdutoShouldNotBeFound("id.notEquals=" + id);

        defaultCategoriaProdutoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategoriaProdutoShouldNotBeFound("id.greaterThan=" + id);

        defaultCategoriaProdutoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategoriaProdutoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCategoriaProdutosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaProdutoRepository.saveAndFlush(categoriaProduto);

        // Get all the categoriaProdutoList where nome equals to DEFAULT_NOME
        defaultCategoriaProdutoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the categoriaProdutoList where nome equals to UPDATED_NOME
        defaultCategoriaProdutoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCategoriaProdutosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaProdutoRepository.saveAndFlush(categoriaProduto);

        // Get all the categoriaProdutoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultCategoriaProdutoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the categoriaProdutoList where nome equals to UPDATED_NOME
        defaultCategoriaProdutoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCategoriaProdutosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaProdutoRepository.saveAndFlush(categoriaProduto);

        // Get all the categoriaProdutoList where nome is not null
        defaultCategoriaProdutoShouldBeFound("nome.specified=true");

        // Get all the categoriaProdutoList where nome is null
        defaultCategoriaProdutoShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriaProdutosByNomeContainsSomething() throws Exception {
        // Initialize the database
        categoriaProdutoRepository.saveAndFlush(categoriaProduto);

        // Get all the categoriaProdutoList where nome contains DEFAULT_NOME
        defaultCategoriaProdutoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the categoriaProdutoList where nome contains UPDATED_NOME
        defaultCategoriaProdutoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCategoriaProdutosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        categoriaProdutoRepository.saveAndFlush(categoriaProduto);

        // Get all the categoriaProdutoList where nome does not contain DEFAULT_NOME
        defaultCategoriaProdutoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the categoriaProdutoList where nome does not contain UPDATED_NOME
        defaultCategoriaProdutoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCategoriaProdutosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaProdutoRepository.saveAndFlush(categoriaProduto);

        // Get all the categoriaProdutoList where descricao equals to DEFAULT_DESCRICAO
        defaultCategoriaProdutoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the categoriaProdutoList where descricao equals to UPDATED_DESCRICAO
        defaultCategoriaProdutoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllCategoriaProdutosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaProdutoRepository.saveAndFlush(categoriaProduto);

        // Get all the categoriaProdutoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultCategoriaProdutoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the categoriaProdutoList where descricao equals to UPDATED_DESCRICAO
        defaultCategoriaProdutoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllCategoriaProdutosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaProdutoRepository.saveAndFlush(categoriaProduto);

        // Get all the categoriaProdutoList where descricao is not null
        defaultCategoriaProdutoShouldBeFound("descricao.specified=true");

        // Get all the categoriaProdutoList where descricao is null
        defaultCategoriaProdutoShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriaProdutosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        categoriaProdutoRepository.saveAndFlush(categoriaProduto);

        // Get all the categoriaProdutoList where descricao contains DEFAULT_DESCRICAO
        defaultCategoriaProdutoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the categoriaProdutoList where descricao contains UPDATED_DESCRICAO
        defaultCategoriaProdutoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllCategoriaProdutosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        categoriaProdutoRepository.saveAndFlush(categoriaProduto);

        // Get all the categoriaProdutoList where descricao does not contain DEFAULT_DESCRICAO
        defaultCategoriaProdutoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the categoriaProdutoList where descricao does not contain UPDATED_DESCRICAO
        defaultCategoriaProdutoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoriaProdutoShouldBeFound(String filter) throws Exception {
        restCategoriaProdutoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaProduto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restCategoriaProdutoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoriaProdutoShouldNotBeFound(String filter) throws Exception {
        restCategoriaProdutoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoriaProdutoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCategoriaProduto() throws Exception {
        // Get the categoriaProduto
        restCategoriaProdutoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCategoriaProduto() throws Exception {
        // Initialize the database
        categoriaProdutoRepository.saveAndFlush(categoriaProduto);

        int databaseSizeBeforeUpdate = categoriaProdutoRepository.findAll().size();

        // Update the categoriaProduto
        CategoriaProduto updatedCategoriaProduto = categoriaProdutoRepository.findById(categoriaProduto.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCategoriaProduto are not directly saved in db
        em.detach(updatedCategoriaProduto);
        updatedCategoriaProduto.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restCategoriaProdutoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCategoriaProduto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCategoriaProduto))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaProduto in the database
        List<CategoriaProduto> categoriaProdutoList = categoriaProdutoRepository.findAll();
        assertThat(categoriaProdutoList).hasSize(databaseSizeBeforeUpdate);
        CategoriaProduto testCategoriaProduto = categoriaProdutoList.get(categoriaProdutoList.size() - 1);
        assertThat(testCategoriaProduto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCategoriaProduto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingCategoriaProduto() throws Exception {
        int databaseSizeBeforeUpdate = categoriaProdutoRepository.findAll().size();
        categoriaProduto.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaProdutoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriaProduto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaProduto))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaProduto in the database
        List<CategoriaProduto> categoriaProdutoList = categoriaProdutoRepository.findAll();
        assertThat(categoriaProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategoriaProduto() throws Exception {
        int databaseSizeBeforeUpdate = categoriaProdutoRepository.findAll().size();
        categoriaProduto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaProdutoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaProduto))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaProduto in the database
        List<CategoriaProduto> categoriaProdutoList = categoriaProdutoRepository.findAll();
        assertThat(categoriaProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategoriaProduto() throws Exception {
        int databaseSizeBeforeUpdate = categoriaProdutoRepository.findAll().size();
        categoriaProduto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaProdutoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoriaProduto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoriaProduto in the database
        List<CategoriaProduto> categoriaProdutoList = categoriaProdutoRepository.findAll();
        assertThat(categoriaProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategoriaProdutoWithPatch() throws Exception {
        // Initialize the database
        categoriaProdutoRepository.saveAndFlush(categoriaProduto);

        int databaseSizeBeforeUpdate = categoriaProdutoRepository.findAll().size();

        // Update the categoriaProduto using partial update
        CategoriaProduto partialUpdatedCategoriaProduto = new CategoriaProduto();
        partialUpdatedCategoriaProduto.setId(categoriaProduto.getId());

        partialUpdatedCategoriaProduto.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restCategoriaProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoriaProduto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategoriaProduto))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaProduto in the database
        List<CategoriaProduto> categoriaProdutoList = categoriaProdutoRepository.findAll();
        assertThat(categoriaProdutoList).hasSize(databaseSizeBeforeUpdate);
        CategoriaProduto testCategoriaProduto = categoriaProdutoList.get(categoriaProdutoList.size() - 1);
        assertThat(testCategoriaProduto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCategoriaProduto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateCategoriaProdutoWithPatch() throws Exception {
        // Initialize the database
        categoriaProdutoRepository.saveAndFlush(categoriaProduto);

        int databaseSizeBeforeUpdate = categoriaProdutoRepository.findAll().size();

        // Update the categoriaProduto using partial update
        CategoriaProduto partialUpdatedCategoriaProduto = new CategoriaProduto();
        partialUpdatedCategoriaProduto.setId(categoriaProduto.getId());

        partialUpdatedCategoriaProduto.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restCategoriaProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoriaProduto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategoriaProduto))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaProduto in the database
        List<CategoriaProduto> categoriaProdutoList = categoriaProdutoRepository.findAll();
        assertThat(categoriaProdutoList).hasSize(databaseSizeBeforeUpdate);
        CategoriaProduto testCategoriaProduto = categoriaProdutoList.get(categoriaProdutoList.size() - 1);
        assertThat(testCategoriaProduto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCategoriaProduto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingCategoriaProduto() throws Exception {
        int databaseSizeBeforeUpdate = categoriaProdutoRepository.findAll().size();
        categoriaProduto.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categoriaProduto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriaProduto))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaProduto in the database
        List<CategoriaProduto> categoriaProdutoList = categoriaProdutoRepository.findAll();
        assertThat(categoriaProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategoriaProduto() throws Exception {
        int databaseSizeBeforeUpdate = categoriaProdutoRepository.findAll().size();
        categoriaProduto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriaProduto))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaProduto in the database
        List<CategoriaProduto> categoriaProdutoList = categoriaProdutoRepository.findAll();
        assertThat(categoriaProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategoriaProduto() throws Exception {
        int databaseSizeBeforeUpdate = categoriaProdutoRepository.findAll().size();
        categoriaProduto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriaProduto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoriaProduto in the database
        List<CategoriaProduto> categoriaProdutoList = categoriaProdutoRepository.findAll();
        assertThat(categoriaProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategoriaProduto() throws Exception {
        // Initialize the database
        categoriaProdutoRepository.saveAndFlush(categoriaProduto);

        int databaseSizeBeforeDelete = categoriaProdutoRepository.findAll().size();

        // Delete the categoriaProduto
        restCategoriaProdutoMockMvc
            .perform(delete(ENTITY_API_URL_ID, categoriaProduto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoriaProduto> categoriaProdutoList = categoriaProdutoRepository.findAll();
        assertThat(categoriaProdutoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

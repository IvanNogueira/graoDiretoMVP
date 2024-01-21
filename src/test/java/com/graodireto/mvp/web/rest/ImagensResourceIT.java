package com.graodireto.mvp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.graodireto.mvp.IntegrationTest;
import com.graodireto.mvp.domain.Estabelecimento;
import com.graodireto.mvp.domain.Imagens;
import com.graodireto.mvp.domain.Produto;
import com.graodireto.mvp.domain.enumeration.LocalImagem;
import com.graodireto.mvp.repository.ImagensRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link ImagensResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ImagensResourceIT {

    private static final byte[] DEFAULT_IMAGEM_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEM_CONTENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEM_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEM_CONTENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_IMAGEM_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEM_CONTENT_TYPE = "BBBBBBBBBB";

    private static final LocalImagem DEFAULT_LOCAL_IMAGEM = LocalImagem.PRODUTO;
    private static final LocalImagem UPDATED_LOCAL_IMAGEM = LocalImagem.LOGO;

    private static final String ENTITY_API_URL = "/api/imagens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ImagensRepository imagensRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImagensMockMvc;

    private Imagens imagens;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Imagens createEntity(EntityManager em) {
        Imagens imagens = new Imagens()
            .imagemContent(DEFAULT_IMAGEM_CONTENT)
            .imagemContentContentType(DEFAULT_IMAGEM_CONTENT_CONTENT_TYPE)
            .imagemContentType(DEFAULT_IMAGEM_CONTENT_TYPE)
            .localImagem(DEFAULT_LOCAL_IMAGEM);
        return imagens;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Imagens createUpdatedEntity(EntityManager em) {
        Imagens imagens = new Imagens()
            .imagemContent(UPDATED_IMAGEM_CONTENT)
            .imagemContentContentType(UPDATED_IMAGEM_CONTENT_CONTENT_TYPE)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .localImagem(UPDATED_LOCAL_IMAGEM);
        return imagens;
    }

    @BeforeEach
    public void initTest() {
        imagens = createEntity(em);
    }

    @Test
    @Transactional
    void createImagens() throws Exception {
        int databaseSizeBeforeCreate = imagensRepository.findAll().size();
        // Create the Imagens
        restImagensMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imagens)))
            .andExpect(status().isCreated());

        // Validate the Imagens in the database
        List<Imagens> imagensList = imagensRepository.findAll();
        assertThat(imagensList).hasSize(databaseSizeBeforeCreate + 1);
        Imagens testImagens = imagensList.get(imagensList.size() - 1);
        assertThat(testImagens.getImagemContent()).isEqualTo(DEFAULT_IMAGEM_CONTENT);
        assertThat(testImagens.getImagemContentContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_CONTENT_TYPE);
        assertThat(testImagens.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
        assertThat(testImagens.getLocalImagem()).isEqualTo(DEFAULT_LOCAL_IMAGEM);
    }

    @Test
    @Transactional
    void createImagensWithExistingId() throws Exception {
        // Create the Imagens with an existing ID
        imagens.setId(1L);

        int databaseSizeBeforeCreate = imagensRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restImagensMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imagens)))
            .andExpect(status().isBadRequest());

        // Validate the Imagens in the database
        List<Imagens> imagensList = imagensRepository.findAll();
        assertThat(imagensList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllImagens() throws Exception {
        // Initialize the database
        imagensRepository.saveAndFlush(imagens);

        // Get all the imagensList
        restImagensMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imagens.getId().intValue())))
            .andExpect(jsonPath("$.[*].imagemContentContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagemContent").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEM_CONTENT))))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].localImagem").value(hasItem(DEFAULT_LOCAL_IMAGEM.toString())));
    }

    @Test
    @Transactional
    void getImagens() throws Exception {
        // Initialize the database
        imagensRepository.saveAndFlush(imagens);

        // Get the imagens
        restImagensMockMvc
            .perform(get(ENTITY_API_URL_ID, imagens.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(imagens.getId().intValue()))
            .andExpect(jsonPath("$.imagemContentContentType").value(DEFAULT_IMAGEM_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagemContent").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGEM_CONTENT)))
            .andExpect(jsonPath("$.imagemContentType").value(DEFAULT_IMAGEM_CONTENT_TYPE))
            .andExpect(jsonPath("$.localImagem").value(DEFAULT_LOCAL_IMAGEM.toString()));
    }

    @Test
    @Transactional
    void getImagensByIdFiltering() throws Exception {
        // Initialize the database
        imagensRepository.saveAndFlush(imagens);

        Long id = imagens.getId();

        defaultImagensShouldBeFound("id.equals=" + id);
        defaultImagensShouldNotBeFound("id.notEquals=" + id);

        defaultImagensShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultImagensShouldNotBeFound("id.greaterThan=" + id);

        defaultImagensShouldBeFound("id.lessThanOrEqual=" + id);
        defaultImagensShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllImagensByImagemContentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        imagensRepository.saveAndFlush(imagens);

        // Get all the imagensList where imagemContentType equals to DEFAULT_IMAGEM_CONTENT_TYPE
        defaultImagensShouldBeFound("imagemContentType.equals=" + DEFAULT_IMAGEM_CONTENT_TYPE);

        // Get all the imagensList where imagemContentType equals to UPDATED_IMAGEM_CONTENT_TYPE
        defaultImagensShouldNotBeFound("imagemContentType.equals=" + UPDATED_IMAGEM_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void getAllImagensByImagemContentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        imagensRepository.saveAndFlush(imagens);

        // Get all the imagensList where imagemContentType in DEFAULT_IMAGEM_CONTENT_TYPE or UPDATED_IMAGEM_CONTENT_TYPE
        defaultImagensShouldBeFound("imagemContentType.in=" + DEFAULT_IMAGEM_CONTENT_TYPE + "," + UPDATED_IMAGEM_CONTENT_TYPE);

        // Get all the imagensList where imagemContentType equals to UPDATED_IMAGEM_CONTENT_TYPE
        defaultImagensShouldNotBeFound("imagemContentType.in=" + UPDATED_IMAGEM_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void getAllImagensByImagemContentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        imagensRepository.saveAndFlush(imagens);

        // Get all the imagensList where imagemContentType is not null
        defaultImagensShouldBeFound("imagemContentType.specified=true");

        // Get all the imagensList where imagemContentType is null
        defaultImagensShouldNotBeFound("imagemContentType.specified=false");
    }

    @Test
    @Transactional
    void getAllImagensByImagemContentTypeContainsSomething() throws Exception {
        // Initialize the database
        imagensRepository.saveAndFlush(imagens);

        // Get all the imagensList where imagemContentType contains DEFAULT_IMAGEM_CONTENT_TYPE
        defaultImagensShouldBeFound("imagemContentType.contains=" + DEFAULT_IMAGEM_CONTENT_TYPE);

        // Get all the imagensList where imagemContentType contains UPDATED_IMAGEM_CONTENT_TYPE
        defaultImagensShouldNotBeFound("imagemContentType.contains=" + UPDATED_IMAGEM_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void getAllImagensByImagemContentTypeNotContainsSomething() throws Exception {
        // Initialize the database
        imagensRepository.saveAndFlush(imagens);

        // Get all the imagensList where imagemContentType does not contain DEFAULT_IMAGEM_CONTENT_TYPE
        defaultImagensShouldNotBeFound("imagemContentType.doesNotContain=" + DEFAULT_IMAGEM_CONTENT_TYPE);

        // Get all the imagensList where imagemContentType does not contain UPDATED_IMAGEM_CONTENT_TYPE
        defaultImagensShouldBeFound("imagemContentType.doesNotContain=" + UPDATED_IMAGEM_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void getAllImagensByLocalImagemIsEqualToSomething() throws Exception {
        // Initialize the database
        imagensRepository.saveAndFlush(imagens);

        // Get all the imagensList where localImagem equals to DEFAULT_LOCAL_IMAGEM
        defaultImagensShouldBeFound("localImagem.equals=" + DEFAULT_LOCAL_IMAGEM);

        // Get all the imagensList where localImagem equals to UPDATED_LOCAL_IMAGEM
        defaultImagensShouldNotBeFound("localImagem.equals=" + UPDATED_LOCAL_IMAGEM);
    }

    @Test
    @Transactional
    void getAllImagensByLocalImagemIsInShouldWork() throws Exception {
        // Initialize the database
        imagensRepository.saveAndFlush(imagens);

        // Get all the imagensList where localImagem in DEFAULT_LOCAL_IMAGEM or UPDATED_LOCAL_IMAGEM
        defaultImagensShouldBeFound("localImagem.in=" + DEFAULT_LOCAL_IMAGEM + "," + UPDATED_LOCAL_IMAGEM);

        // Get all the imagensList where localImagem equals to UPDATED_LOCAL_IMAGEM
        defaultImagensShouldNotBeFound("localImagem.in=" + UPDATED_LOCAL_IMAGEM);
    }

    @Test
    @Transactional
    void getAllImagensByLocalImagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        imagensRepository.saveAndFlush(imagens);

        // Get all the imagensList where localImagem is not null
        defaultImagensShouldBeFound("localImagem.specified=true");

        // Get all the imagensList where localImagem is null
        defaultImagensShouldNotBeFound("localImagem.specified=false");
    }

    @Test
    @Transactional
    void getAllImagensByEstabelecimentoIsEqualToSomething() throws Exception {
        Estabelecimento estabelecimento;
        if (TestUtil.findAll(em, Estabelecimento.class).isEmpty()) {
            imagensRepository.saveAndFlush(imagens);
            estabelecimento = EstabelecimentoResourceIT.createEntity(em);
        } else {
            estabelecimento = TestUtil.findAll(em, Estabelecimento.class).get(0);
        }
        em.persist(estabelecimento);
        em.flush();
        imagens.setEstabelecimento(estabelecimento);
        imagensRepository.saveAndFlush(imagens);
        Long estabelecimentoId = estabelecimento.getId();
        // Get all the imagensList where estabelecimento equals to estabelecimentoId
        defaultImagensShouldBeFound("estabelecimentoId.equals=" + estabelecimentoId);

        // Get all the imagensList where estabelecimento equals to (estabelecimentoId + 1)
        defaultImagensShouldNotBeFound("estabelecimentoId.equals=" + (estabelecimentoId + 1));
    }

    @Test
    @Transactional
    void getAllImagensByProdutoIsEqualToSomething() throws Exception {
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            imagensRepository.saveAndFlush(imagens);
            produto = ProdutoResourceIT.createEntity(em);
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        em.persist(produto);
        em.flush();
        imagens.setProduto(produto);
        imagensRepository.saveAndFlush(imagens);
        Long produtoId = produto.getId();
        // Get all the imagensList where produto equals to produtoId
        defaultImagensShouldBeFound("produtoId.equals=" + produtoId);

        // Get all the imagensList where produto equals to (produtoId + 1)
        defaultImagensShouldNotBeFound("produtoId.equals=" + (produtoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultImagensShouldBeFound(String filter) throws Exception {
        restImagensMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imagens.getId().intValue())))
            .andExpect(jsonPath("$.[*].imagemContentContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagemContent").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEM_CONTENT))))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].localImagem").value(hasItem(DEFAULT_LOCAL_IMAGEM.toString())));

        // Check, that the count call also returns 1
        restImagensMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultImagensShouldNotBeFound(String filter) throws Exception {
        restImagensMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restImagensMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingImagens() throws Exception {
        // Get the imagens
        restImagensMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingImagens() throws Exception {
        // Initialize the database
        imagensRepository.saveAndFlush(imagens);

        int databaseSizeBeforeUpdate = imagensRepository.findAll().size();

        // Update the imagens
        Imagens updatedImagens = imagensRepository.findById(imagens.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedImagens are not directly saved in db
        em.detach(updatedImagens);
        updatedImagens
            .imagemContent(UPDATED_IMAGEM_CONTENT)
            .imagemContentContentType(UPDATED_IMAGEM_CONTENT_CONTENT_TYPE)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .localImagem(UPDATED_LOCAL_IMAGEM);

        restImagensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedImagens.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedImagens))
            )
            .andExpect(status().isOk());

        // Validate the Imagens in the database
        List<Imagens> imagensList = imagensRepository.findAll();
        assertThat(imagensList).hasSize(databaseSizeBeforeUpdate);
        Imagens testImagens = imagensList.get(imagensList.size() - 1);
        assertThat(testImagens.getImagemContent()).isEqualTo(UPDATED_IMAGEM_CONTENT);
        assertThat(testImagens.getImagemContentContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_CONTENT_TYPE);
        assertThat(testImagens.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testImagens.getLocalImagem()).isEqualTo(UPDATED_LOCAL_IMAGEM);
    }

    @Test
    @Transactional
    void putNonExistingImagens() throws Exception {
        int databaseSizeBeforeUpdate = imagensRepository.findAll().size();
        imagens.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImagensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, imagens.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imagens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Imagens in the database
        List<Imagens> imagensList = imagensRepository.findAll();
        assertThat(imagensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchImagens() throws Exception {
        int databaseSizeBeforeUpdate = imagensRepository.findAll().size();
        imagens.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImagensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imagens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Imagens in the database
        List<Imagens> imagensList = imagensRepository.findAll();
        assertThat(imagensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamImagens() throws Exception {
        int databaseSizeBeforeUpdate = imagensRepository.findAll().size();
        imagens.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImagensMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imagens)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Imagens in the database
        List<Imagens> imagensList = imagensRepository.findAll();
        assertThat(imagensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateImagensWithPatch() throws Exception {
        // Initialize the database
        imagensRepository.saveAndFlush(imagens);

        int databaseSizeBeforeUpdate = imagensRepository.findAll().size();

        // Update the imagens using partial update
        Imagens partialUpdatedImagens = new Imagens();
        partialUpdatedImagens.setId(imagens.getId());

        partialUpdatedImagens.imagemContent(UPDATED_IMAGEM_CONTENT).imagemContentContentType(UPDATED_IMAGEM_CONTENT_CONTENT_TYPE);

        restImagensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImagens.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImagens))
            )
            .andExpect(status().isOk());

        // Validate the Imagens in the database
        List<Imagens> imagensList = imagensRepository.findAll();
        assertThat(imagensList).hasSize(databaseSizeBeforeUpdate);
        Imagens testImagens = imagensList.get(imagensList.size() - 1);
        assertThat(testImagens.getImagemContent()).isEqualTo(UPDATED_IMAGEM_CONTENT);
        assertThat(testImagens.getImagemContentContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_CONTENT_TYPE);
        assertThat(testImagens.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
        assertThat(testImagens.getLocalImagem()).isEqualTo(DEFAULT_LOCAL_IMAGEM);
    }

    @Test
    @Transactional
    void fullUpdateImagensWithPatch() throws Exception {
        // Initialize the database
        imagensRepository.saveAndFlush(imagens);

        int databaseSizeBeforeUpdate = imagensRepository.findAll().size();

        // Update the imagens using partial update
        Imagens partialUpdatedImagens = new Imagens();
        partialUpdatedImagens.setId(imagens.getId());

        partialUpdatedImagens
            .imagemContent(UPDATED_IMAGEM_CONTENT)
            .imagemContentContentType(UPDATED_IMAGEM_CONTENT_CONTENT_TYPE)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .localImagem(UPDATED_LOCAL_IMAGEM);

        restImagensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImagens.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImagens))
            )
            .andExpect(status().isOk());

        // Validate the Imagens in the database
        List<Imagens> imagensList = imagensRepository.findAll();
        assertThat(imagensList).hasSize(databaseSizeBeforeUpdate);
        Imagens testImagens = imagensList.get(imagensList.size() - 1);
        assertThat(testImagens.getImagemContent()).isEqualTo(UPDATED_IMAGEM_CONTENT);
        assertThat(testImagens.getImagemContentContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_CONTENT_TYPE);
        assertThat(testImagens.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testImagens.getLocalImagem()).isEqualTo(UPDATED_LOCAL_IMAGEM);
    }

    @Test
    @Transactional
    void patchNonExistingImagens() throws Exception {
        int databaseSizeBeforeUpdate = imagensRepository.findAll().size();
        imagens.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImagensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, imagens.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(imagens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Imagens in the database
        List<Imagens> imagensList = imagensRepository.findAll();
        assertThat(imagensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchImagens() throws Exception {
        int databaseSizeBeforeUpdate = imagensRepository.findAll().size();
        imagens.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImagensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(imagens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Imagens in the database
        List<Imagens> imagensList = imagensRepository.findAll();
        assertThat(imagensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamImagens() throws Exception {
        int databaseSizeBeforeUpdate = imagensRepository.findAll().size();
        imagens.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImagensMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(imagens)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Imagens in the database
        List<Imagens> imagensList = imagensRepository.findAll();
        assertThat(imagensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteImagens() throws Exception {
        // Initialize the database
        imagensRepository.saveAndFlush(imagens);

        int databaseSizeBeforeDelete = imagensRepository.findAll().size();

        // Delete the imagens
        restImagensMockMvc
            .perform(delete(ENTITY_API_URL_ID, imagens.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Imagens> imagensList = imagensRepository.findAll();
        assertThat(imagensList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

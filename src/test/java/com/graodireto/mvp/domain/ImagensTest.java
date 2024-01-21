package com.graodireto.mvp.domain;

import static com.graodireto.mvp.domain.EstabelecimentoTestSamples.*;
import static com.graodireto.mvp.domain.ImagensTestSamples.*;
import static com.graodireto.mvp.domain.ProdutoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.graodireto.mvp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ImagensTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Imagens.class);
        Imagens imagens1 = getImagensSample1();
        Imagens imagens2 = new Imagens();
        assertThat(imagens1).isNotEqualTo(imagens2);

        imagens2.setId(imagens1.getId());
        assertThat(imagens1).isEqualTo(imagens2);

        imagens2 = getImagensSample2();
        assertThat(imagens1).isNotEqualTo(imagens2);
    }

    @Test
    void estabelecimentoTest() throws Exception {
        Imagens imagens = getImagensRandomSampleGenerator();
        Estabelecimento estabelecimentoBack = getEstabelecimentoRandomSampleGenerator();

        imagens.setEstabelecimento(estabelecimentoBack);
        assertThat(imagens.getEstabelecimento()).isEqualTo(estabelecimentoBack);

        imagens.estabelecimento(null);
        assertThat(imagens.getEstabelecimento()).isNull();
    }

    @Test
    void produtoTest() throws Exception {
        Imagens imagens = getImagensRandomSampleGenerator();
        Produto produtoBack = getProdutoRandomSampleGenerator();

        imagens.setProduto(produtoBack);
        assertThat(imagens.getProduto()).isEqualTo(produtoBack);

        imagens.produto(null);
        assertThat(imagens.getProduto()).isNull();
    }
}

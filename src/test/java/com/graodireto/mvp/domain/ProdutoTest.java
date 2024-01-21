package com.graodireto.mvp.domain;

import static com.graodireto.mvp.domain.CardapioTestSamples.*;
import static com.graodireto.mvp.domain.CategoriaProdutoTestSamples.*;
import static com.graodireto.mvp.domain.ProdutoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.graodireto.mvp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProdutoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Produto.class);
        Produto produto1 = getProdutoSample1();
        Produto produto2 = new Produto();
        assertThat(produto1).isNotEqualTo(produto2);

        produto2.setId(produto1.getId());
        assertThat(produto1).isEqualTo(produto2);

        produto2 = getProdutoSample2();
        assertThat(produto1).isNotEqualTo(produto2);
    }

    @Test
    void categoriaProdutoTest() throws Exception {
        Produto produto = getProdutoRandomSampleGenerator();
        CategoriaProduto categoriaProdutoBack = getCategoriaProdutoRandomSampleGenerator();

        produto.setCategoriaProduto(categoriaProdutoBack);
        assertThat(produto.getCategoriaProduto()).isEqualTo(categoriaProdutoBack);

        produto.categoriaProduto(null);
        assertThat(produto.getCategoriaProduto()).isNull();
    }

    @Test
    void cardapioTest() throws Exception {
        Produto produto = getProdutoRandomSampleGenerator();
        Cardapio cardapioBack = getCardapioRandomSampleGenerator();

        produto.setCardapio(cardapioBack);
        assertThat(produto.getCardapio()).isEqualTo(cardapioBack);

        produto.cardapio(null);
        assertThat(produto.getCardapio()).isNull();
    }
}

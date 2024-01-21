package com.graodireto.mvp.domain;

import static com.graodireto.mvp.domain.CardapioTestSamples.*;
import static com.graodireto.mvp.domain.CategoriaProdutoTestSamples.*;
import static com.graodireto.mvp.domain.ImagensTestSamples.*;
import static com.graodireto.mvp.domain.ProdutoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.graodireto.mvp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
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
    void imagensTest() throws Exception {
        Produto produto = getProdutoRandomSampleGenerator();
        Imagens imagensBack = getImagensRandomSampleGenerator();

        produto.addImagens(imagensBack);
        assertThat(produto.getImagens()).containsOnly(imagensBack);
        assertThat(imagensBack.getProduto()).isEqualTo(produto);

        produto.removeImagens(imagensBack);
        assertThat(produto.getImagens()).doesNotContain(imagensBack);
        assertThat(imagensBack.getProduto()).isNull();

        produto.imagens(new HashSet<>(Set.of(imagensBack)));
        assertThat(produto.getImagens()).containsOnly(imagensBack);
        assertThat(imagensBack.getProduto()).isEqualTo(produto);

        produto.setImagens(new HashSet<>());
        assertThat(produto.getImagens()).doesNotContain(imagensBack);
        assertThat(imagensBack.getProduto()).isNull();
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

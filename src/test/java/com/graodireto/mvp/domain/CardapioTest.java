package com.graodireto.mvp.domain;

import static com.graodireto.mvp.domain.CardapioTestSamples.*;
import static com.graodireto.mvp.domain.EstabelecimentoTestSamples.*;
import static com.graodireto.mvp.domain.ProdutoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.graodireto.mvp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CardapioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cardapio.class);
        Cardapio cardapio1 = getCardapioSample1();
        Cardapio cardapio2 = new Cardapio();
        assertThat(cardapio1).isNotEqualTo(cardapio2);

        cardapio2.setId(cardapio1.getId());
        assertThat(cardapio1).isEqualTo(cardapio2);

        cardapio2 = getCardapioSample2();
        assertThat(cardapio1).isNotEqualTo(cardapio2);
    }

    @Test
    void produtoTest() throws Exception {
        Cardapio cardapio = getCardapioRandomSampleGenerator();
        Produto produtoBack = getProdutoRandomSampleGenerator();

        cardapio.addProduto(produtoBack);
        assertThat(cardapio.getProdutos()).containsOnly(produtoBack);
        assertThat(produtoBack.getCardapio()).isEqualTo(cardapio);

        cardapio.removeProduto(produtoBack);
        assertThat(cardapio.getProdutos()).doesNotContain(produtoBack);
        assertThat(produtoBack.getCardapio()).isNull();

        cardapio.produtos(new HashSet<>(Set.of(produtoBack)));
        assertThat(cardapio.getProdutos()).containsOnly(produtoBack);
        assertThat(produtoBack.getCardapio()).isEqualTo(cardapio);

        cardapio.setProdutos(new HashSet<>());
        assertThat(cardapio.getProdutos()).doesNotContain(produtoBack);
        assertThat(produtoBack.getCardapio()).isNull();
    }

    @Test
    void estabelecimentoTest() throws Exception {
        Cardapio cardapio = getCardapioRandomSampleGenerator();
        Estabelecimento estabelecimentoBack = getEstabelecimentoRandomSampleGenerator();

        cardapio.setEstabelecimento(estabelecimentoBack);
        assertThat(cardapio.getEstabelecimento()).isEqualTo(estabelecimentoBack);

        cardapio.estabelecimento(null);
        assertThat(cardapio.getEstabelecimento()).isNull();
    }
}

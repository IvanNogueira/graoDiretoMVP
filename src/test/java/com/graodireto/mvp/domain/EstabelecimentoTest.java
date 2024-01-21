package com.graodireto.mvp.domain;

import static com.graodireto.mvp.domain.CardapioTestSamples.*;
import static com.graodireto.mvp.domain.CidadeTestSamples.*;
import static com.graodireto.mvp.domain.CupomDescontoTestSamples.*;
import static com.graodireto.mvp.domain.EstabelecimentoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.graodireto.mvp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EstabelecimentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estabelecimento.class);
        Estabelecimento estabelecimento1 = getEstabelecimentoSample1();
        Estabelecimento estabelecimento2 = new Estabelecimento();
        assertThat(estabelecimento1).isNotEqualTo(estabelecimento2);

        estabelecimento2.setId(estabelecimento1.getId());
        assertThat(estabelecimento1).isEqualTo(estabelecimento2);

        estabelecimento2 = getEstabelecimentoSample2();
        assertThat(estabelecimento1).isNotEqualTo(estabelecimento2);
    }

    @Test
    void cardapioTest() throws Exception {
        Estabelecimento estabelecimento = getEstabelecimentoRandomSampleGenerator();
        Cardapio cardapioBack = getCardapioRandomSampleGenerator();

        estabelecimento.addCardapio(cardapioBack);
        assertThat(estabelecimento.getCardapios()).containsOnly(cardapioBack);
        assertThat(cardapioBack.getEstabelecimento()).isEqualTo(estabelecimento);

        estabelecimento.removeCardapio(cardapioBack);
        assertThat(estabelecimento.getCardapios()).doesNotContain(cardapioBack);
        assertThat(cardapioBack.getEstabelecimento()).isNull();

        estabelecimento.cardapios(new HashSet<>(Set.of(cardapioBack)));
        assertThat(estabelecimento.getCardapios()).containsOnly(cardapioBack);
        assertThat(cardapioBack.getEstabelecimento()).isEqualTo(estabelecimento);

        estabelecimento.setCardapios(new HashSet<>());
        assertThat(estabelecimento.getCardapios()).doesNotContain(cardapioBack);
        assertThat(cardapioBack.getEstabelecimento()).isNull();
    }

    @Test
    void cupomDescontoTest() throws Exception {
        Estabelecimento estabelecimento = getEstabelecimentoRandomSampleGenerator();
        CupomDesconto cupomDescontoBack = getCupomDescontoRandomSampleGenerator();

        estabelecimento.addCupomDesconto(cupomDescontoBack);
        assertThat(estabelecimento.getCupomDescontos()).containsOnly(cupomDescontoBack);
        assertThat(cupomDescontoBack.getEstabelecimento()).isEqualTo(estabelecimento);

        estabelecimento.removeCupomDesconto(cupomDescontoBack);
        assertThat(estabelecimento.getCupomDescontos()).doesNotContain(cupomDescontoBack);
        assertThat(cupomDescontoBack.getEstabelecimento()).isNull();

        estabelecimento.cupomDescontos(new HashSet<>(Set.of(cupomDescontoBack)));
        assertThat(estabelecimento.getCupomDescontos()).containsOnly(cupomDescontoBack);
        assertThat(cupomDescontoBack.getEstabelecimento()).isEqualTo(estabelecimento);

        estabelecimento.setCupomDescontos(new HashSet<>());
        assertThat(estabelecimento.getCupomDescontos()).doesNotContain(cupomDescontoBack);
        assertThat(cupomDescontoBack.getEstabelecimento()).isNull();
    }

    @Test
    void cidadeTest() throws Exception {
        Estabelecimento estabelecimento = getEstabelecimentoRandomSampleGenerator();
        Cidade cidadeBack = getCidadeRandomSampleGenerator();

        estabelecimento.setCidade(cidadeBack);
        assertThat(estabelecimento.getCidade()).isEqualTo(cidadeBack);

        estabelecimento.cidade(null);
        assertThat(estabelecimento.getCidade()).isNull();
    }
}

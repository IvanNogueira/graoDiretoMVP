package com.graodireto.mvp.domain;

import static com.graodireto.mvp.domain.CupomDescontoTestSamples.*;
import static com.graodireto.mvp.domain.EstabelecimentoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.graodireto.mvp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CupomDescontoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CupomDesconto.class);
        CupomDesconto cupomDesconto1 = getCupomDescontoSample1();
        CupomDesconto cupomDesconto2 = new CupomDesconto();
        assertThat(cupomDesconto1).isNotEqualTo(cupomDesconto2);

        cupomDesconto2.setId(cupomDesconto1.getId());
        assertThat(cupomDesconto1).isEqualTo(cupomDesconto2);

        cupomDesconto2 = getCupomDescontoSample2();
        assertThat(cupomDesconto1).isNotEqualTo(cupomDesconto2);
    }

    @Test
    void estabelecimentoTest() throws Exception {
        CupomDesconto cupomDesconto = getCupomDescontoRandomSampleGenerator();
        Estabelecimento estabelecimentoBack = getEstabelecimentoRandomSampleGenerator();

        cupomDesconto.setEstabelecimento(estabelecimentoBack);
        assertThat(cupomDesconto.getEstabelecimento()).isEqualTo(estabelecimentoBack);

        cupomDesconto.estabelecimento(null);
        assertThat(cupomDesconto.getEstabelecimento()).isNull();
    }
}

package com.graodireto.mvp.domain;

import static com.graodireto.mvp.domain.CidadeTestSamples.*;
import static com.graodireto.mvp.domain.EstabelecimentoTestSamples.*;
import static com.graodireto.mvp.domain.EstadoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.graodireto.mvp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CidadeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cidade.class);
        Cidade cidade1 = getCidadeSample1();
        Cidade cidade2 = new Cidade();
        assertThat(cidade1).isNotEqualTo(cidade2);

        cidade2.setId(cidade1.getId());
        assertThat(cidade1).isEqualTo(cidade2);

        cidade2 = getCidadeSample2();
        assertThat(cidade1).isNotEqualTo(cidade2);
    }

    @Test
    void estabelecimentoTest() throws Exception {
        Cidade cidade = getCidadeRandomSampleGenerator();
        Estabelecimento estabelecimentoBack = getEstabelecimentoRandomSampleGenerator();

        cidade.addEstabelecimento(estabelecimentoBack);
        assertThat(cidade.getEstabelecimentos()).containsOnly(estabelecimentoBack);
        assertThat(estabelecimentoBack.getCidade()).isEqualTo(cidade);

        cidade.removeEstabelecimento(estabelecimentoBack);
        assertThat(cidade.getEstabelecimentos()).doesNotContain(estabelecimentoBack);
        assertThat(estabelecimentoBack.getCidade()).isNull();

        cidade.estabelecimentos(new HashSet<>(Set.of(estabelecimentoBack)));
        assertThat(cidade.getEstabelecimentos()).containsOnly(estabelecimentoBack);
        assertThat(estabelecimentoBack.getCidade()).isEqualTo(cidade);

        cidade.setEstabelecimentos(new HashSet<>());
        assertThat(cidade.getEstabelecimentos()).doesNotContain(estabelecimentoBack);
        assertThat(estabelecimentoBack.getCidade()).isNull();
    }

    @Test
    void estadoTest() throws Exception {
        Cidade cidade = getCidadeRandomSampleGenerator();
        Estado estadoBack = getEstadoRandomSampleGenerator();

        cidade.setEstado(estadoBack);
        assertThat(cidade.getEstado()).isEqualTo(estadoBack);

        cidade.estado(null);
        assertThat(cidade.getEstado()).isNull();
    }
}

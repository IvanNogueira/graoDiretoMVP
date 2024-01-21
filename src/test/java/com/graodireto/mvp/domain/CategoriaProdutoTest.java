package com.graodireto.mvp.domain;

import static com.graodireto.mvp.domain.CategoriaProdutoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.graodireto.mvp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategoriaProdutoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaProduto.class);
        CategoriaProduto categoriaProduto1 = getCategoriaProdutoSample1();
        CategoriaProduto categoriaProduto2 = new CategoriaProduto();
        assertThat(categoriaProduto1).isNotEqualTo(categoriaProduto2);

        categoriaProduto2.setId(categoriaProduto1.getId());
        assertThat(categoriaProduto1).isEqualTo(categoriaProduto2);

        categoriaProduto2 = getCategoriaProdutoSample2();
        assertThat(categoriaProduto1).isNotEqualTo(categoriaProduto2);
    }
}

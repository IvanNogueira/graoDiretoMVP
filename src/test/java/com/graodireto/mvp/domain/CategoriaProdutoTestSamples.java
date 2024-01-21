package com.graodireto.mvp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CategoriaProdutoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CategoriaProduto getCategoriaProdutoSample1() {
        return new CategoriaProduto().id(1L).nome("nome1").descricao("descricao1");
    }

    public static CategoriaProduto getCategoriaProdutoSample2() {
        return new CategoriaProduto().id(2L).nome("nome2").descricao("descricao2");
    }

    public static CategoriaProduto getCategoriaProdutoRandomSampleGenerator() {
        return new CategoriaProduto()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString());
    }
}

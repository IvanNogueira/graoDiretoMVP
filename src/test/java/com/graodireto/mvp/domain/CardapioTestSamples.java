package com.graodireto.mvp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CardapioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Cardapio getCardapioSample1() {
        return new Cardapio().id(1L).nome("nome1");
    }

    public static Cardapio getCardapioSample2() {
        return new Cardapio().id(2L).nome("nome2");
    }

    public static Cardapio getCardapioRandomSampleGenerator() {
        return new Cardapio().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString());
    }
}

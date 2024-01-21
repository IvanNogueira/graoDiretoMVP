package com.graodireto.mvp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EstadoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Estado getEstadoSample1() {
        return new Estado().id(1L).nome("nome1").uf("uf1");
    }

    public static Estado getEstadoSample2() {
        return new Estado().id(2L).nome("nome2").uf("uf2");
    }

    public static Estado getEstadoRandomSampleGenerator() {
        return new Estado().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString()).uf(UUID.randomUUID().toString());
    }
}

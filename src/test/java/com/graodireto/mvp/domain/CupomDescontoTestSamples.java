package com.graodireto.mvp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CupomDescontoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CupomDesconto getCupomDescontoSample1() {
        return new CupomDesconto().id(1L).nome("nome1").descricaoRegras("descricaoRegras1");
    }

    public static CupomDesconto getCupomDescontoSample2() {
        return new CupomDesconto().id(2L).nome("nome2").descricaoRegras("descricaoRegras2");
    }

    public static CupomDesconto getCupomDescontoRandomSampleGenerator() {
        return new CupomDesconto()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .descricaoRegras(UUID.randomUUID().toString());
    }
}

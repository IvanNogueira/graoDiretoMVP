package com.graodireto.mvp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CupomDescontoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CupomDesconto getCupomDescontoSample1() {
        return new CupomDesconto().id(1L).descricaoRegras("descricaoRegras1");
    }

    public static CupomDesconto getCupomDescontoSample2() {
        return new CupomDesconto().id(2L).descricaoRegras("descricaoRegras2");
    }

    public static CupomDesconto getCupomDescontoRandomSampleGenerator() {
        return new CupomDesconto().id(longCount.incrementAndGet()).descricaoRegras(UUID.randomUUID().toString());
    }
}

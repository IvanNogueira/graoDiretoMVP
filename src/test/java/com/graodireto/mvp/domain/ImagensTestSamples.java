package com.graodireto.mvp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ImagensTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Imagens getImagensSample1() {
        return new Imagens().id(1L).imagemContentType("imagemContentType1");
    }

    public static Imagens getImagensSample2() {
        return new Imagens().id(2L).imagemContentType("imagemContentType2");
    }

    public static Imagens getImagensRandomSampleGenerator() {
        return new Imagens().id(longCount.incrementAndGet()).imagemContentType(UUID.randomUUID().toString());
    }
}

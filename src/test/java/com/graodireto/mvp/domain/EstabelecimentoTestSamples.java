package com.graodireto.mvp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EstabelecimentoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Estabelecimento getEstabelecimentoSample1() {
        return new Estabelecimento()
            .id(1L)
            .nome("nome1")
            .telefone("telefone1")
            .email("email1")
            .logradouro("logradouro1")
            .numero("numero1")
            .complemento("complemento1")
            .bairro("bairro1")
            .cep("cep1");
    }

    public static Estabelecimento getEstabelecimentoSample2() {
        return new Estabelecimento()
            .id(2L)
            .nome("nome2")
            .telefone("telefone2")
            .email("email2")
            .logradouro("logradouro2")
            .numero("numero2")
            .complemento("complemento2")
            .bairro("bairro2")
            .cep("cep2");
    }

    public static Estabelecimento getEstabelecimentoRandomSampleGenerator() {
        return new Estabelecimento()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .telefone(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .logradouro(UUID.randomUUID().toString())
            .numero(UUID.randomUUID().toString())
            .complemento(UUID.randomUUID().toString())
            .bairro(UUID.randomUUID().toString())
            .cep(UUID.randomUUID().toString());
    }
}

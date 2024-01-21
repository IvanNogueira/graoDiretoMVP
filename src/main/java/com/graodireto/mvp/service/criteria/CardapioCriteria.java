package com.graodireto.mvp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.graodireto.mvp.domain.Cardapio} entity. This class is used
 * in {@link com.graodireto.mvp.web.rest.CardapioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cardapios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CardapioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private LocalDateFilter dataCriacao;

    private BooleanFilter ativo;

    private LongFilter produtoId;

    private LongFilter estabelecimentoId;

    private Boolean distinct;

    public CardapioCriteria() {}

    public CardapioCriteria(CardapioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.dataCriacao = other.dataCriacao == null ? null : other.dataCriacao.copy();
        this.ativo = other.ativo == null ? null : other.ativo.copy();
        this.produtoId = other.produtoId == null ? null : other.produtoId.copy();
        this.estabelecimentoId = other.estabelecimentoId == null ? null : other.estabelecimentoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CardapioCriteria copy() {
        return new CardapioCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNome() {
        return nome;
    }

    public StringFilter nome() {
        if (nome == null) {
            nome = new StringFilter();
        }
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public LocalDateFilter getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateFilter dataCriacao() {
        if (dataCriacao == null) {
            dataCriacao = new LocalDateFilter();
        }
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateFilter dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public BooleanFilter getAtivo() {
        return ativo;
    }

    public BooleanFilter ativo() {
        if (ativo == null) {
            ativo = new BooleanFilter();
        }
        return ativo;
    }

    public void setAtivo(BooleanFilter ativo) {
        this.ativo = ativo;
    }

    public LongFilter getProdutoId() {
        return produtoId;
    }

    public LongFilter produtoId() {
        if (produtoId == null) {
            produtoId = new LongFilter();
        }
        return produtoId;
    }

    public void setProdutoId(LongFilter produtoId) {
        this.produtoId = produtoId;
    }

    public LongFilter getEstabelecimentoId() {
        return estabelecimentoId;
    }

    public LongFilter estabelecimentoId() {
        if (estabelecimentoId == null) {
            estabelecimentoId = new LongFilter();
        }
        return estabelecimentoId;
    }

    public void setEstabelecimentoId(LongFilter estabelecimentoId) {
        this.estabelecimentoId = estabelecimentoId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CardapioCriteria that = (CardapioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(dataCriacao, that.dataCriacao) &&
            Objects.equals(ativo, that.ativo) &&
            Objects.equals(produtoId, that.produtoId) &&
            Objects.equals(estabelecimentoId, that.estabelecimentoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, dataCriacao, ativo, produtoId, estabelecimentoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CardapioCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (dataCriacao != null ? "dataCriacao=" + dataCriacao + ", " : "") +
            (ativo != null ? "ativo=" + ativo + ", " : "") +
            (produtoId != null ? "produtoId=" + produtoId + ", " : "") +
            (estabelecimentoId != null ? "estabelecimentoId=" + estabelecimentoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

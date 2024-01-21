package com.graodireto.mvp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.graodireto.mvp.domain.CupomDesconto} entity. This class is used
 * in {@link com.graodireto.mvp.web.rest.CupomDescontoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cupom-descontos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CupomDescontoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private BigDecimalFilter valorDesconto;

    private BooleanFilter valorMinimo;

    private BigDecimalFilter valorMinimoRegra;

    private StringFilter descricaoRegras;

    private BooleanFilter valido;

    private LongFilter estabelecimentoId;

    private Boolean distinct;

    public CupomDescontoCriteria() {}

    public CupomDescontoCriteria(CupomDescontoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.valorDesconto = other.valorDesconto == null ? null : other.valorDesconto.copy();
        this.valorMinimo = other.valorMinimo == null ? null : other.valorMinimo.copy();
        this.valorMinimoRegra = other.valorMinimoRegra == null ? null : other.valorMinimoRegra.copy();
        this.descricaoRegras = other.descricaoRegras == null ? null : other.descricaoRegras.copy();
        this.valido = other.valido == null ? null : other.valido.copy();
        this.estabelecimentoId = other.estabelecimentoId == null ? null : other.estabelecimentoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CupomDescontoCriteria copy() {
        return new CupomDescontoCriteria(this);
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

    public BigDecimalFilter getValorDesconto() {
        return valorDesconto;
    }

    public BigDecimalFilter valorDesconto() {
        if (valorDesconto == null) {
            valorDesconto = new BigDecimalFilter();
        }
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimalFilter valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public BooleanFilter getValorMinimo() {
        return valorMinimo;
    }

    public BooleanFilter valorMinimo() {
        if (valorMinimo == null) {
            valorMinimo = new BooleanFilter();
        }
        return valorMinimo;
    }

    public void setValorMinimo(BooleanFilter valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public BigDecimalFilter getValorMinimoRegra() {
        return valorMinimoRegra;
    }

    public BigDecimalFilter valorMinimoRegra() {
        if (valorMinimoRegra == null) {
            valorMinimoRegra = new BigDecimalFilter();
        }
        return valorMinimoRegra;
    }

    public void setValorMinimoRegra(BigDecimalFilter valorMinimoRegra) {
        this.valorMinimoRegra = valorMinimoRegra;
    }

    public StringFilter getDescricaoRegras() {
        return descricaoRegras;
    }

    public StringFilter descricaoRegras() {
        if (descricaoRegras == null) {
            descricaoRegras = new StringFilter();
        }
        return descricaoRegras;
    }

    public void setDescricaoRegras(StringFilter descricaoRegras) {
        this.descricaoRegras = descricaoRegras;
    }

    public BooleanFilter getValido() {
        return valido;
    }

    public BooleanFilter valido() {
        if (valido == null) {
            valido = new BooleanFilter();
        }
        return valido;
    }

    public void setValido(BooleanFilter valido) {
        this.valido = valido;
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
        final CupomDescontoCriteria that = (CupomDescontoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(valorDesconto, that.valorDesconto) &&
            Objects.equals(valorMinimo, that.valorMinimo) &&
            Objects.equals(valorMinimoRegra, that.valorMinimoRegra) &&
            Objects.equals(descricaoRegras, that.descricaoRegras) &&
            Objects.equals(valido, that.valido) &&
            Objects.equals(estabelecimentoId, that.estabelecimentoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, valorDesconto, valorMinimo, valorMinimoRegra, descricaoRegras, valido, estabelecimentoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CupomDescontoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (valorDesconto != null ? "valorDesconto=" + valorDesconto + ", " : "") +
            (valorMinimo != null ? "valorMinimo=" + valorMinimo + ", " : "") +
            (valorMinimoRegra != null ? "valorMinimoRegra=" + valorMinimoRegra + ", " : "") +
            (descricaoRegras != null ? "descricaoRegras=" + descricaoRegras + ", " : "") +
            (valido != null ? "valido=" + valido + ", " : "") +
            (estabelecimentoId != null ? "estabelecimentoId=" + estabelecimentoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

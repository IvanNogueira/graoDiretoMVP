package com.graodireto.mvp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.graodireto.mvp.domain.Produto} entity. This class is used
 * in {@link com.graodireto.mvp.web.rest.ProdutoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /produtos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProdutoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter descricao;

    private BigDecimalFilter preco;

    private BigDecimalFilter desconto;

    private LongFilter imagensId;

    private LongFilter categoriaProdutoId;

    private LongFilter cardapioId;

    private Boolean distinct;

    public ProdutoCriteria() {}

    public ProdutoCriteria(ProdutoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.preco = other.preco == null ? null : other.preco.copy();
        this.desconto = other.desconto == null ? null : other.desconto.copy();
        this.imagensId = other.imagensId == null ? null : other.imagensId.copy();
        this.categoriaProdutoId = other.categoriaProdutoId == null ? null : other.categoriaProdutoId.copy();
        this.cardapioId = other.cardapioId == null ? null : other.cardapioId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProdutoCriteria copy() {
        return new ProdutoCriteria(this);
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

    public StringFilter getDescricao() {
        return descricao;
    }

    public StringFilter descricao() {
        if (descricao == null) {
            descricao = new StringFilter();
        }
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public BigDecimalFilter getPreco() {
        return preco;
    }

    public BigDecimalFilter preco() {
        if (preco == null) {
            preco = new BigDecimalFilter();
        }
        return preco;
    }

    public void setPreco(BigDecimalFilter preco) {
        this.preco = preco;
    }

    public BigDecimalFilter getDesconto() {
        return desconto;
    }

    public BigDecimalFilter desconto() {
        if (desconto == null) {
            desconto = new BigDecimalFilter();
        }
        return desconto;
    }

    public void setDesconto(BigDecimalFilter desconto) {
        this.desconto = desconto;
    }

    public LongFilter getImagensId() {
        return imagensId;
    }

    public LongFilter imagensId() {
        if (imagensId == null) {
            imagensId = new LongFilter();
        }
        return imagensId;
    }

    public void setImagensId(LongFilter imagensId) {
        this.imagensId = imagensId;
    }

    public LongFilter getCategoriaProdutoId() {
        return categoriaProdutoId;
    }

    public LongFilter categoriaProdutoId() {
        if (categoriaProdutoId == null) {
            categoriaProdutoId = new LongFilter();
        }
        return categoriaProdutoId;
    }

    public void setCategoriaProdutoId(LongFilter categoriaProdutoId) {
        this.categoriaProdutoId = categoriaProdutoId;
    }

    public LongFilter getCardapioId() {
        return cardapioId;
    }

    public LongFilter cardapioId() {
        if (cardapioId == null) {
            cardapioId = new LongFilter();
        }
        return cardapioId;
    }

    public void setCardapioId(LongFilter cardapioId) {
        this.cardapioId = cardapioId;
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
        final ProdutoCriteria that = (ProdutoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(preco, that.preco) &&
            Objects.equals(desconto, that.desconto) &&
            Objects.equals(imagensId, that.imagensId) &&
            Objects.equals(categoriaProdutoId, that.categoriaProdutoId) &&
            Objects.equals(cardapioId, that.cardapioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descricao, preco, desconto, imagensId, categoriaProdutoId, cardapioId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProdutoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (preco != null ? "preco=" + preco + ", " : "") +
            (desconto != null ? "desconto=" + desconto + ", " : "") +
            (imagensId != null ? "imagensId=" + imagensId + ", " : "") +
            (categoriaProdutoId != null ? "categoriaProdutoId=" + categoriaProdutoId + ", " : "") +
            (cardapioId != null ? "cardapioId=" + cardapioId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

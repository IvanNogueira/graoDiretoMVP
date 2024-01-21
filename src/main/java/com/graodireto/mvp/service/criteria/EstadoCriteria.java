package com.graodireto.mvp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.graodireto.mvp.domain.Estado} entity. This class is used
 * in {@link com.graodireto.mvp.web.rest.EstadoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /estados?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstadoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter uf;

    private LongFilter cidadeId;

    private Boolean distinct;

    public EstadoCriteria() {}

    public EstadoCriteria(EstadoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.uf = other.uf == null ? null : other.uf.copy();
        this.cidadeId = other.cidadeId == null ? null : other.cidadeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EstadoCriteria copy() {
        return new EstadoCriteria(this);
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

    public StringFilter getUf() {
        return uf;
    }

    public StringFilter uf() {
        if (uf == null) {
            uf = new StringFilter();
        }
        return uf;
    }

    public void setUf(StringFilter uf) {
        this.uf = uf;
    }

    public LongFilter getCidadeId() {
        return cidadeId;
    }

    public LongFilter cidadeId() {
        if (cidadeId == null) {
            cidadeId = new LongFilter();
        }
        return cidadeId;
    }

    public void setCidadeId(LongFilter cidadeId) {
        this.cidadeId = cidadeId;
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
        final EstadoCriteria that = (EstadoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(uf, that.uf) &&
            Objects.equals(cidadeId, that.cidadeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, uf, cidadeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstadoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (uf != null ? "uf=" + uf + ", " : "") +
            (cidadeId != null ? "cidadeId=" + cidadeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

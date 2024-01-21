package com.graodireto.mvp.service.criteria;

import com.graodireto.mvp.domain.enumeration.LocalImagem;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.graodireto.mvp.domain.Imagens} entity. This class is used
 * in {@link com.graodireto.mvp.web.rest.ImagensResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /imagens?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImagensCriteria implements Serializable, Criteria {

    /**
     * Class for filtering LocalImagem
     */
    public static class LocalImagemFilter extends Filter<LocalImagem> {

        public LocalImagemFilter() {}

        public LocalImagemFilter(LocalImagemFilter filter) {
            super(filter);
        }

        @Override
        public LocalImagemFilter copy() {
            return new LocalImagemFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter imagemContentType;

    private LocalImagemFilter localImagem;

    private LongFilter estabelecimentoId;

    private LongFilter produtoId;

    private Boolean distinct;

    public ImagensCriteria() {}

    public ImagensCriteria(ImagensCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.imagemContentType = other.imagemContentType == null ? null : other.imagemContentType.copy();
        this.localImagem = other.localImagem == null ? null : other.localImagem.copy();
        this.estabelecimentoId = other.estabelecimentoId == null ? null : other.estabelecimentoId.copy();
        this.produtoId = other.produtoId == null ? null : other.produtoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ImagensCriteria copy() {
        return new ImagensCriteria(this);
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

    public StringFilter getImagemContentType() {
        return imagemContentType;
    }

    public StringFilter imagemContentType() {
        if (imagemContentType == null) {
            imagemContentType = new StringFilter();
        }
        return imagemContentType;
    }

    public void setImagemContentType(StringFilter imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    public LocalImagemFilter getLocalImagem() {
        return localImagem;
    }

    public LocalImagemFilter localImagem() {
        if (localImagem == null) {
            localImagem = new LocalImagemFilter();
        }
        return localImagem;
    }

    public void setLocalImagem(LocalImagemFilter localImagem) {
        this.localImagem = localImagem;
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
        final ImagensCriteria that = (ImagensCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(imagemContentType, that.imagemContentType) &&
            Objects.equals(localImagem, that.localImagem) &&
            Objects.equals(estabelecimentoId, that.estabelecimentoId) &&
            Objects.equals(produtoId, that.produtoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imagemContentType, localImagem, estabelecimentoId, produtoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImagensCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (imagemContentType != null ? "imagemContentType=" + imagemContentType + ", " : "") +
            (localImagem != null ? "localImagem=" + localImagem + ", " : "") +
            (estabelecimentoId != null ? "estabelecimentoId=" + estabelecimentoId + ", " : "") +
            (produtoId != null ? "produtoId=" + produtoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

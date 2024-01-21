package com.graodireto.mvp.service.criteria;

import com.graodireto.mvp.domain.enumeration.TipoEstabelicimento;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.graodireto.mvp.domain.Estabelecimento} entity. This class is used
 * in {@link com.graodireto.mvp.web.rest.EstabelecimentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /estabelecimentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstabelecimentoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TipoEstabelicimento
     */
    public static class TipoEstabelicimentoFilter extends Filter<TipoEstabelicimento> {

        public TipoEstabelicimentoFilter() {}

        public TipoEstabelicimentoFilter(TipoEstabelicimentoFilter filter) {
            super(filter);
        }

        @Override
        public TipoEstabelicimentoFilter copy() {
            return new TipoEstabelicimentoFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter telefone;

    private StringFilter email;

    private TipoEstabelicimentoFilter tipoEstabelecimento;

    private InstantFilter criadoEm;

    private StringFilter logradouro;

    private StringFilter numero;

    private StringFilter complemento;

    private StringFilter bairro;

    private StringFilter cep;

    private LongFilter cardapioId;

    private LongFilter cupomDescontoId;

    private LongFilter cidadeId;

    private LongFilter userId;

    private Boolean distinct;

    public EstabelecimentoCriteria() {}

    public EstabelecimentoCriteria(EstabelecimentoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.telefone = other.telefone == null ? null : other.telefone.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.tipoEstabelecimento = other.tipoEstabelecimento == null ? null : other.tipoEstabelecimento.copy();
        this.criadoEm = other.criadoEm == null ? null : other.criadoEm.copy();
        this.logradouro = other.logradouro == null ? null : other.logradouro.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.complemento = other.complemento == null ? null : other.complemento.copy();
        this.bairro = other.bairro == null ? null : other.bairro.copy();
        this.cep = other.cep == null ? null : other.cep.copy();
        this.cardapioId = other.cardapioId == null ? null : other.cardapioId.copy();
        this.cupomDescontoId = other.cupomDescontoId == null ? null : other.cupomDescontoId.copy();
        this.cidadeId = other.cidadeId == null ? null : other.cidadeId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();

        this.distinct = other.distinct;
    }

    @Override
    public EstabelecimentoCriteria copy() {
        return new EstabelecimentoCriteria(this);
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

    public StringFilter getTelefone() {
        return telefone;
    }

    public StringFilter telefone() {
        if (telefone == null) {
            telefone = new StringFilter();
        }
        return telefone;
    }

    public void setTelefone(StringFilter telefone) {
        this.telefone = telefone;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public TipoEstabelicimentoFilter getTipoEstabelecimento() {
        return tipoEstabelecimento;
    }

    public TipoEstabelicimentoFilter tipoEstabelecimento() {
        if (tipoEstabelecimento == null) {
            tipoEstabelecimento = new TipoEstabelicimentoFilter();
        }
        return tipoEstabelecimento;
    }

    public void setTipoEstabelecimento(TipoEstabelicimentoFilter tipoEstabelecimento) {
        this.tipoEstabelecimento = tipoEstabelecimento;
    }

    public InstantFilter getCriadoEm() {
        return criadoEm;
    }

    public InstantFilter criadoEm() {
        if (criadoEm == null) {
            criadoEm = new InstantFilter();
        }
        return criadoEm;
    }

    public void setCriadoEm(InstantFilter criadoEm) {
        this.criadoEm = criadoEm;
    }

    public StringFilter getLogradouro() {
        return logradouro;
    }

    public StringFilter logradouro() {
        if (logradouro == null) {
            logradouro = new StringFilter();
        }
        return logradouro;
    }

    public void setLogradouro(StringFilter logradouro) {
        this.logradouro = logradouro;
    }

    public StringFilter getNumero() {
        return numero;
    }

    public StringFilter numero() {
        if (numero == null) {
            numero = new StringFilter();
        }
        return numero;
    }

    public void setNumero(StringFilter numero) {
        this.numero = numero;
    }

    public StringFilter getComplemento() {
        return complemento;
    }

    public StringFilter complemento() {
        if (complemento == null) {
            complemento = new StringFilter();
        }
        return complemento;
    }

    public void setComplemento(StringFilter complemento) {
        this.complemento = complemento;
    }

    public StringFilter getBairro() {
        return bairro;
    }

    public StringFilter bairro() {
        if (bairro == null) {
            bairro = new StringFilter();
        }
        return bairro;
    }

    public void setBairro(StringFilter bairro) {
        this.bairro = bairro;
    }

    public StringFilter getCep() {
        return cep;
    }

    public StringFilter cep() {
        if (cep == null) {
            cep = new StringFilter();
        }
        return cep;
    }

    public void setCep(StringFilter cep) {
        this.cep = cep;
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

    public LongFilter getCupomDescontoId() {
        return cupomDescontoId;
    }

    public LongFilter cupomDescontoId() {
        if (cupomDescontoId == null) {
            cupomDescontoId = new LongFilter();
        }
        return cupomDescontoId;
    }

    public void setCupomDescontoId(LongFilter cupomDescontoId) {
        this.cupomDescontoId = cupomDescontoId;
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

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
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
        final EstabelecimentoCriteria that = (EstabelecimentoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(telefone, that.telefone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(tipoEstabelecimento, that.tipoEstabelecimento) &&
            Objects.equals(criadoEm, that.criadoEm) &&
            Objects.equals(logradouro, that.logradouro) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(complemento, that.complemento) &&
            Objects.equals(bairro, that.bairro) &&
            Objects.equals(cep, that.cep) &&
            Objects.equals(cardapioId, that.cardapioId) &&
            Objects.equals(cupomDescontoId, that.cupomDescontoId) &&
            Objects.equals(cidadeId, that.cidadeId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nome,
            telefone,
            email,
            tipoEstabelecimento,
            criadoEm,
            logradouro,
            numero,
            complemento,
            bairro,
            cep,
            cardapioId,
            cupomDescontoId,
            cidadeId,
            userId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstabelecimentoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (telefone != null ? "telefone=" + telefone + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (tipoEstabelecimento != null ? "tipoEstabelecimento=" + tipoEstabelecimento + ", " : "") +
            (criadoEm != null ? "criadoEm=" + criadoEm + ", " : "") +
            (logradouro != null ? "logradouro=" + logradouro + ", " : "") +
            (numero != null ? "numero=" + numero + ", " : "") +
            (complemento != null ? "complemento=" + complemento + ", " : "") +
            (bairro != null ? "bairro=" + bairro + ", " : "") +
            (cep != null ? "cep=" + cep + ", " : "") +
            (cardapioId != null ? "cardapioId=" + cardapioId + ", " : "") +
            (cupomDescontoId != null ? "cupomDescontoId=" + cupomDescontoId + ", " : "") +
            (cidadeId != null ? "cidadeId=" + cidadeId + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

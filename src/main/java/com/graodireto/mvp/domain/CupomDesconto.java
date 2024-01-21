package com.graodireto.mvp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A CupomDesconto.
 */
@Entity
@Table(name = "cupom_desconto")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CupomDesconto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "valor_desconto", precision = 21, scale = 2)
    private BigDecimal valorDesconto;

    @Column(name = "valor_minimo")
    private Boolean valorMinimo;

    @Column(name = "valor_minimo_regra", precision = 21, scale = 2)
    private BigDecimal valorMinimoRegra;

    @Column(name = "descricao_regras")
    private String descricaoRegras;

    @Column(name = "valido")
    private Boolean valido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "cardapios", "imagens", "cupomDescontos", "cidade" }, allowSetters = true)
    private Estabelecimento estabelecimento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CupomDesconto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorDesconto() {
        return this.valorDesconto;
    }

    public CupomDesconto valorDesconto(BigDecimal valorDesconto) {
        this.setValorDesconto(valorDesconto);
        return this;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public Boolean getValorMinimo() {
        return this.valorMinimo;
    }

    public CupomDesconto valorMinimo(Boolean valorMinimo) {
        this.setValorMinimo(valorMinimo);
        return this;
    }

    public void setValorMinimo(Boolean valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public BigDecimal getValorMinimoRegra() {
        return this.valorMinimoRegra;
    }

    public CupomDesconto valorMinimoRegra(BigDecimal valorMinimoRegra) {
        this.setValorMinimoRegra(valorMinimoRegra);
        return this;
    }

    public void setValorMinimoRegra(BigDecimal valorMinimoRegra) {
        this.valorMinimoRegra = valorMinimoRegra;
    }

    public String getDescricaoRegras() {
        return this.descricaoRegras;
    }

    public CupomDesconto descricaoRegras(String descricaoRegras) {
        this.setDescricaoRegras(descricaoRegras);
        return this;
    }

    public void setDescricaoRegras(String descricaoRegras) {
        this.descricaoRegras = descricaoRegras;
    }

    public Boolean getValido() {
        return this.valido;
    }

    public CupomDesconto valido(Boolean valido) {
        this.setValido(valido);
        return this;
    }

    public void setValido(Boolean valido) {
        this.valido = valido;
    }

    public Estabelecimento getEstabelecimento() {
        return this.estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public CupomDesconto estabelecimento(Estabelecimento estabelecimento) {
        this.setEstabelecimento(estabelecimento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CupomDesconto)) {
            return false;
        }
        return getId() != null && getId().equals(((CupomDesconto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CupomDesconto{" +
            "id=" + getId() +
            ", valorDesconto=" + getValorDesconto() +
            ", valorMinimo='" + getValorMinimo() + "'" +
            ", valorMinimoRegra=" + getValorMinimoRegra() +
            ", descricaoRegras='" + getDescricaoRegras() + "'" +
            ", valido='" + getValido() + "'" +
            "}";
    }
}

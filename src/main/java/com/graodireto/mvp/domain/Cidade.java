package com.graodireto.mvp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cidade.
 */
@Entity
@Table(name = "cidade")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cidade")
    @JsonIgnoreProperties(value = { "cardapios", "imagens", "cupomDescontos", "cidade" }, allowSetters = true)
    private Set<Estabelecimento> estabelecimentos = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "cidades" }, allowSetters = true)
    private Estado estado;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cidade id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Cidade nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Estabelecimento> getEstabelecimentos() {
        return this.estabelecimentos;
    }

    public void setEstabelecimentos(Set<Estabelecimento> estabelecimentos) {
        if (this.estabelecimentos != null) {
            this.estabelecimentos.forEach(i -> i.setCidade(null));
        }
        if (estabelecimentos != null) {
            estabelecimentos.forEach(i -> i.setCidade(this));
        }
        this.estabelecimentos = estabelecimentos;
    }

    public Cidade estabelecimentos(Set<Estabelecimento> estabelecimentos) {
        this.setEstabelecimentos(estabelecimentos);
        return this;
    }

    public Cidade addEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimentos.add(estabelecimento);
        estabelecimento.setCidade(this);
        return this;
    }

    public Cidade removeEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimentos.remove(estabelecimento);
        estabelecimento.setCidade(null);
        return this;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Cidade estado(Estado estado) {
        this.setEstado(estado);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cidade)) {
            return false;
        }
        return getId() != null && getId().equals(((Cidade) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cidade{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}

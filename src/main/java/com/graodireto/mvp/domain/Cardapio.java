package com.graodireto.mvp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cardapio.
 */
@Entity
@Table(name = "cardapio")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cardapio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "ativo")
    private Boolean ativo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cardapio")
    @JsonIgnoreProperties(value = { "imagens", "categoriaProduto", "cardapio" }, allowSetters = true)
    private Set<Produto> produtos = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "cardapios", "imagens", "cupomDescontos", "cidade" }, allowSetters = true)
    private Estabelecimento estabelecimento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cardapio id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Cardapio nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataCriacao() {
        return this.dataCriacao;
    }

    public Cardapio dataCriacao(LocalDate dataCriacao) {
        this.setDataCriacao(dataCriacao);
        return this;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public Cardapio ativo(Boolean ativo) {
        this.setAtivo(ativo);
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Set<Produto> getProdutos() {
        return this.produtos;
    }

    public void setProdutos(Set<Produto> produtos) {
        if (this.produtos != null) {
            this.produtos.forEach(i -> i.setCardapio(null));
        }
        if (produtos != null) {
            produtos.forEach(i -> i.setCardapio(this));
        }
        this.produtos = produtos;
    }

    public Cardapio produtos(Set<Produto> produtos) {
        this.setProdutos(produtos);
        return this;
    }

    public Cardapio addProduto(Produto produto) {
        this.produtos.add(produto);
        produto.setCardapio(this);
        return this;
    }

    public Cardapio removeProduto(Produto produto) {
        this.produtos.remove(produto);
        produto.setCardapio(null);
        return this;
    }

    public Estabelecimento getEstabelecimento() {
        return this.estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public Cardapio estabelecimento(Estabelecimento estabelecimento) {
        this.setEstabelecimento(estabelecimento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cardapio)) {
            return false;
        }
        return getId() != null && getId().equals(((Cardapio) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cardapio{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dataCriacao='" + getDataCriacao() + "'" +
            ", ativo='" + getAtivo() + "'" +
            "}";
    }
}

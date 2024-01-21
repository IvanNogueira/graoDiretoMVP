package com.graodireto.mvp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Produto.
 */
@Entity
@Table(name = "produto")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Column(name = "preco", precision = 21, scale = 2, nullable = false)
    private BigDecimal preco;

    @Column(name = "desconto", precision = 21, scale = 2)
    private BigDecimal desconto;

    @Lob
    @Column(name = "imagem_produto")
    private byte[] imagemProduto;

    @Column(name = "imagem_produto_content_type")
    private String imagemProdutoContentType;

    @ManyToOne(fetch = FetchType.EAGER)
    private CategoriaProduto categoriaProduto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "produtos", "estabelecimento" }, allowSetters = true)
    private Cardapio cardapio;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Produto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Produto nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Produto descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return this.preco;
    }

    public Produto preco(BigDecimal preco) {
        this.setPreco(preco);
        return this;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public BigDecimal getDesconto() {
        return this.desconto;
    }

    public Produto desconto(BigDecimal desconto) {
        this.setDesconto(desconto);
        return this;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public byte[] getImagemProduto() {
        return this.imagemProduto;
    }

    public Produto imagemProduto(byte[] imagemProduto) {
        this.setImagemProduto(imagemProduto);
        return this;
    }

    public void setImagemProduto(byte[] imagemProduto) {
        this.imagemProduto = imagemProduto;
    }

    public String getImagemProdutoContentType() {
        return this.imagemProdutoContentType;
    }

    public Produto imagemProdutoContentType(String imagemProdutoContentType) {
        this.imagemProdutoContentType = imagemProdutoContentType;
        return this;
    }

    public void setImagemProdutoContentType(String imagemProdutoContentType) {
        this.imagemProdutoContentType = imagemProdutoContentType;
    }

    public CategoriaProduto getCategoriaProduto() {
        return this.categoriaProduto;
    }

    public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    public Produto categoriaProduto(CategoriaProduto categoriaProduto) {
        this.setCategoriaProduto(categoriaProduto);
        return this;
    }

    public Cardapio getCardapio() {
        return this.cardapio;
    }

    public void setCardapio(Cardapio cardapio) {
        this.cardapio = cardapio;
    }

    public Produto cardapio(Cardapio cardapio) {
        this.setCardapio(cardapio);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produto)) {
            return false;
        }
        return getId() != null && getId().equals(((Produto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", preco=" + getPreco() +
            ", desconto=" + getDesconto() +
            ", imagemProduto='" + getImagemProduto() + "'" +
            ", imagemProdutoContentType='" + getImagemProdutoContentType() + "'" +
            "}";
    }
}

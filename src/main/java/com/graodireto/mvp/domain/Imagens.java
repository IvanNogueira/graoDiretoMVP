package com.graodireto.mvp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.graodireto.mvp.domain.enumeration.LocalImagem;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Imagens.
 */
@Entity
@Table(name = "imagens")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Imagens implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "imagem_content")
    private byte[] imagemContent;

    @Column(name = "imagem_content_content_type")
    private String imagemContentContentType;

    @Column(name = "imagem_content_type")
    private String imagemContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "local_imagem")
    private LocalImagem localImagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "cardapios", "imagens", "cupomDescontos", "cidade" }, allowSetters = true)
    private Estabelecimento estabelecimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "imagens", "categoriaProduto", "cardapio" }, allowSetters = true)
    private Produto produto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Imagens id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImagemContent() {
        return this.imagemContent;
    }

    public Imagens imagemContent(byte[] imagemContent) {
        this.setImagemContent(imagemContent);
        return this;
    }

    public void setImagemContent(byte[] imagemContent) {
        this.imagemContent = imagemContent;
    }

    public String getImagemContentContentType() {
        return this.imagemContentContentType;
    }

    public Imagens imagemContentContentType(String imagemContentContentType) {
        this.imagemContentContentType = imagemContentContentType;
        return this;
    }

    public void setImagemContentContentType(String imagemContentContentType) {
        this.imagemContentContentType = imagemContentContentType;
    }

    public String getImagemContentType() {
        return this.imagemContentType;
    }

    public Imagens imagemContentType(String imagemContentType) {
        this.setImagemContentType(imagemContentType);
        return this;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    public LocalImagem getLocalImagem() {
        return this.localImagem;
    }

    public Imagens localImagem(LocalImagem localImagem) {
        this.setLocalImagem(localImagem);
        return this;
    }

    public void setLocalImagem(LocalImagem localImagem) {
        this.localImagem = localImagem;
    }

    public Estabelecimento getEstabelecimento() {
        return this.estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public Imagens estabelecimento(Estabelecimento estabelecimento) {
        this.setEstabelecimento(estabelecimento);
        return this;
    }

    public Produto getProduto() {
        return this.produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Imagens produto(Produto produto) {
        this.setProduto(produto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Imagens)) {
            return false;
        }
        return getId() != null && getId().equals(((Imagens) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Imagens{" +
            "id=" + getId() +
            ", imagemContent='" + getImagemContent() + "'" +
            ", imagemContentContentType='" + getImagemContentContentType() + "'" +
            ", imagemContentType='" + getImagemContentType() + "'" +
            ", localImagem='" + getLocalImagem() + "'" +
            "}";
    }
}

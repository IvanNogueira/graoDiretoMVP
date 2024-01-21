package com.graodireto.mvp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CategoriaProduto.
 */
@Entity
@Table(name = "categoria_produto")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoriaProduto implements Serializable {

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

    @OneToMany(mappedBy = "categoriaProduto", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnoreProperties(value = { "estabelecimento", "categoriaProduto", "cardapio" }, allowSetters = true)
    private Set<Produto> produtos = new HashSet<>();

    @PreRemove
    private void preRemove() {
        // Desvincula os produtos da categoria antes de excluí-la
        for (Produto produto : produtos) {
            produto.setCategoriaProduto(null);
        }
    }

    public Long getId() {
        return this.id;
    }

    public CategoriaProduto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public CategoriaProduto nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public CategoriaProduto descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriaProduto)) {
            return false;
        }
        return getId() != null && getId().equals(((CategoriaProduto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriaProduto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}

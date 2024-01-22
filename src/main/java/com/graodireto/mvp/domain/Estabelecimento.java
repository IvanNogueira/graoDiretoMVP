package com.graodireto.mvp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.graodireto.mvp.domain.enumeration.TipoEstabelicimento;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Estabelecimento.
 */
@Entity
@Table(name = "estabelecimento")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Estabelecimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_estabelecimento")
    private TipoEstabelicimento tipoEstabelecimento;

    @Lob
    @Column(name = "capa")
    private byte[] capa;

    @Column(name = "capa_content_type")
    private String capaContentType;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Column(name = "criado_em")
    private Instant criadoEm;

    @NotNull
    @Column(name = "logradouro", nullable = false)
    private String logradouro;

    @Column(name = "numero")
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @NotNull
    @Column(name = "bairro", nullable = false)
    private String bairro;

    @NotNull
    @Column(name = "cep", nullable = false)
    private String cep;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "estabelecimento")
    @JsonIgnoreProperties(value = {}, allowSetters = true)
    private Set<Cardapio> cardapios = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "estabelecimento")
    @JsonIgnoreProperties(value = { "estabelecimento" }, allowSetters = true)
    private Set<CupomDesconto> cupomDescontos = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "estabelecimentos", "estado" }, allowSetters = true)
    private Cidade cidade;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Estabelecimento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Estabelecimento nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Estabelecimento telefone(String telefone) {
        this.setTelefone(telefone);
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return this.email;
    }

    public Estabelecimento email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoEstabelicimento getTipoEstabelecimento() {
        return this.tipoEstabelecimento;
    }

    public Estabelecimento tipoEstabelecimento(TipoEstabelicimento tipoEstabelecimento) {
        this.setTipoEstabelecimento(tipoEstabelecimento);
        return this;
    }

    public void setTipoEstabelecimento(TipoEstabelicimento tipoEstabelecimento) {
        this.tipoEstabelecimento = tipoEstabelecimento;
    }

    public byte[] getCapa() {
        return this.capa;
    }

    public Estabelecimento capa(byte[] capa) {
        this.setCapa(capa);
        return this;
    }

    public void setCapa(byte[] capa) {
        this.capa = capa;
    }

    public String getCapaContentType() {
        return this.capaContentType;
    }

    public Estabelecimento capaContentType(String capaContentType) {
        this.capaContentType = capaContentType;
        return this;
    }

    public void setCapaContentType(String capaContentType) {
        this.capaContentType = capaContentType;
    }

    public byte[] getLogo() {
        return this.logo;
    }

    public Estabelecimento logo(byte[] logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return this.logoContentType;
    }

    public Estabelecimento logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public Instant getCriadoEm() {
        return this.criadoEm;
    }

    public Estabelecimento criadoEm(Instant criadoEm) {
        this.setCriadoEm(criadoEm);
        return this;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public Estabelecimento logradouro(String logradouro) {
        this.setLogradouro(logradouro);
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return this.numero;
    }

    public Estabelecimento numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return this.complemento;
    }

    public Estabelecimento complemento(String complemento) {
        this.setComplemento(complemento);
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return this.bairro;
    }

    public Estabelecimento bairro(String bairro) {
        this.setBairro(bairro);
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return this.cep;
    }

    public Estabelecimento cep(String cep) {
        this.setCep(cep);
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Set<Cardapio> getCardapios() {
        return this.cardapios;
    }

    public void setCardapios(Set<Cardapio> cardapios) {
        if (this.cardapios != null) {
            this.cardapios.forEach(i -> i.setEstabelecimento(null));
        }
        if (cardapios != null) {
            cardapios.forEach(i -> i.setEstabelecimento(this));
        }
        this.cardapios = cardapios;
    }

    public Estabelecimento cardapios(Set<Cardapio> cardapios) {
        this.setCardapios(cardapios);
        return this;
    }

    public Estabelecimento addCardapio(Cardapio cardapio) {
        this.cardapios.add(cardapio);
        cardapio.setEstabelecimento(this);
        return this;
    }

    public Estabelecimento removeCardapio(Cardapio cardapio) {
        this.cardapios.remove(cardapio);
        cardapio.setEstabelecimento(null);
        return this;
    }

    public Set<CupomDesconto> getCupomDescontos() {
        return this.cupomDescontos;
    }

    public void setCupomDescontos(Set<CupomDesconto> cupomDescontos) {
        if (this.cupomDescontos != null) {
            this.cupomDescontos.forEach(i -> i.setEstabelecimento(null));
        }
        if (cupomDescontos != null) {
            cupomDescontos.forEach(i -> i.setEstabelecimento(this));
        }
        this.cupomDescontos = cupomDescontos;
    }

    public Estabelecimento cupomDescontos(Set<CupomDesconto> cupomDescontos) {
        this.setCupomDescontos(cupomDescontos);
        return this;
    }

    public Estabelecimento addCupomDesconto(CupomDesconto cupomDesconto) {
        this.cupomDescontos.add(cupomDesconto);
        cupomDesconto.setEstabelecimento(this);
        return this;
    }

    public Estabelecimento removeCupomDesconto(CupomDesconto cupomDesconto) {
        this.cupomDescontos.remove(cupomDesconto);
        cupomDesconto.setEstabelecimento(null);
        return this;
    }

    public Cidade getCidade() {
        return this.cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Estabelecimento cidade(Cidade cidade) {
        this.setCidade(cidade);
        return this;
    }

    public User getUser() {
        return user;
    }

    public Estabelecimento user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Estabelecimento)) {
            return false;
        }
        return getId() != null && getId().equals(((Estabelecimento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Estabelecimento{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", email='" + getEmail() + "'" +
            ", tipoEstabelecimento='" + getTipoEstabelecimento() + "'" +
            ", capa='" + getCapa() + "'" +
            ", capaContentType='" + getCapaContentType() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", criadoEm='" + getCriadoEm() + "'" +
            ", logradouro='" + getLogradouro() + "'" +
            ", numero='" + getNumero() + "'" +
            ", complemento='" + getComplemento() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", cep='" + getCep() + "'" +
            "}";
    }
}

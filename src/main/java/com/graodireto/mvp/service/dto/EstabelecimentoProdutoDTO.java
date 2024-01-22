package com.graodireto.mvp.service.dto;

import com.graodireto.mvp.domain.Estabelecimento;
import com.graodireto.mvp.domain.Produto;
import java.util.Arrays;
import java.util.List;

public class EstabelecimentoProdutoDTO {

    private List<Estabelecimento> estabelecimento;
    private List<Produto> produto;

    public List<Estabelecimento> getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(List<Estabelecimento> estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public List<Produto> getProduto() {
        return produto;
    }

    public void setProduto(List<Produto> produto) {
        this.produto = produto;
    }
}

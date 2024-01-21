package com.graodireto.mvp.service.impl;

import com.graodireto.mvp.domain.Produto;
import com.graodireto.mvp.repository.ProdutoRepository;
import com.graodireto.mvp.service.ProdutoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.graodireto.mvp.domain.Produto}.
 */
@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService {

    private final Logger log = LoggerFactory.getLogger(ProdutoServiceImpl.class);

    private final ProdutoRepository produtoRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Produto save(Produto produto) {
        log.debug("Request to save Produto : {}", produto);
        return produtoRepository.save(produto);
    }

    @Override
    public Produto update(Produto produto) {
        log.debug("Request to update Produto : {}", produto);
        return produtoRepository.save(produto);
    }

    @Override
    public Optional<Produto> partialUpdate(Produto produto) {
        log.debug("Request to partially update Produto : {}", produto);

        return produtoRepository
            .findById(produto.getId())
            .map(existingProduto -> {
                if (produto.getNome() != null) {
                    existingProduto.setNome(produto.getNome());
                }
                if (produto.getDescricao() != null) {
                    existingProduto.setDescricao(produto.getDescricao());
                }
                if (produto.getPreco() != null) {
                    existingProduto.setPreco(produto.getPreco());
                }
                if (produto.getDesconto() != null) {
                    existingProduto.setDesconto(produto.getDesconto());
                }

                return existingProduto;
            })
            .map(produtoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Produto> findAll(Pageable pageable) {
        log.debug("Request to get all Produtos");
        return produtoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Produto> findOne(Long id) {
        log.debug("Request to get Produto : {}", id);
        return produtoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Produto : {}", id);
        produtoRepository.deleteById(id);
    }
}

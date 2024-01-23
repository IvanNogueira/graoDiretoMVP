package com.graodireto.mvp.service.impl;

import com.graodireto.mvp.domain.CategoriaProduto;
import com.graodireto.mvp.repository.CategoriaProdutoRepository;
import com.graodireto.mvp.service.CategoriaProdutoService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.graodireto.mvp.domain.CategoriaProduto}.
 */
@Service
@Transactional
public class CategoriaProdutoServiceImpl implements CategoriaProdutoService {

    private final Logger log = LoggerFactory.getLogger(CategoriaProdutoServiceImpl.class);

    private final CategoriaProdutoRepository categoriaProdutoRepository;

    public CategoriaProdutoServiceImpl(CategoriaProdutoRepository categoriaProdutoRepository) {
        this.categoriaProdutoRepository = categoriaProdutoRepository;
    }

    @Override
    public CategoriaProduto save(CategoriaProduto categoriaProduto) {
        log.debug("Request to save CategoriaProduto : {}", categoriaProduto);
        return categoriaProdutoRepository.save(categoriaProduto);
    }

    @Override
    public CategoriaProduto update(CategoriaProduto categoriaProduto) {
        log.debug("Request to update CategoriaProduto : {}", categoriaProduto);
        return categoriaProdutoRepository.save(categoriaProduto);
    }

    @Override
    public Optional<CategoriaProduto> partialUpdate(CategoriaProduto categoriaProduto) {
        log.debug("Request to partially update CategoriaProduto : {}", categoriaProduto);

        return categoriaProdutoRepository
            .findById(categoriaProduto.getId())
            .map(existingCategoriaProduto -> {
                if (categoriaProduto.getNome() != null) {
                    existingCategoriaProduto.setNome(categoriaProduto.getNome());
                }
                if (categoriaProduto.getDescricao() != null) {
                    existingCategoriaProduto.setDescricao(categoriaProduto.getDescricao());
                }

                return existingCategoriaProduto;
            })
            .map(categoriaProdutoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaProduto> findAll(Pageable pageable) {
        log.debug("Request to get all CategoriaProdutos");
        return categoriaProdutoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaProduto> findCategoriasByUserId(Long userId) {
        log.debug("Request to get all CategoriaProdutos");
        return categoriaProdutoRepository.findCategoriasByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoriaProduto> findOne(Long id) {
        log.debug("Request to get CategoriaProduto : {}", id);
        return categoriaProdutoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoriaProduto : {}", id);
        categoriaProdutoRepository.deleteById(id);
    }
}

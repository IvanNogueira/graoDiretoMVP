package com.graodireto.mvp.service.impl;

import com.graodireto.mvp.domain.Cidade;
import com.graodireto.mvp.repository.CidadeRepository;
import com.graodireto.mvp.service.CidadeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.graodireto.mvp.domain.Cidade}.
 */
@Service
@Transactional
public class CidadeServiceImpl implements CidadeService {

    private final Logger log = LoggerFactory.getLogger(CidadeServiceImpl.class);

    private final CidadeRepository cidadeRepository;

    public CidadeServiceImpl(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    @Override
    public Cidade save(Cidade cidade) {
        log.debug("Request to save Cidade : {}", cidade);
        return cidadeRepository.save(cidade);
    }

    @Override
    public Cidade update(Cidade cidade) {
        log.debug("Request to update Cidade : {}", cidade);
        return cidadeRepository.save(cidade);
    }

    @Override
    public Optional<Cidade> partialUpdate(Cidade cidade) {
        log.debug("Request to partially update Cidade : {}", cidade);

        return cidadeRepository
            .findById(cidade.getId())
            .map(existingCidade -> {
                if (cidade.getNome() != null) {
                    existingCidade.setNome(cidade.getNome());
                }

                return existingCidade;
            })
            .map(cidadeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cidade> findAll(Pageable pageable) {
        log.debug("Request to get all Cidades");
        return cidadeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cidade> findOne(Long id) {
        log.debug("Request to get Cidade : {}", id);
        return cidadeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cidade : {}", id);
        cidadeRepository.deleteById(id);
    }
}

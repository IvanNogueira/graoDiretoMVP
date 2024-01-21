package com.graodireto.mvp.service.impl;

import com.graodireto.mvp.domain.CupomDesconto;
import com.graodireto.mvp.repository.CupomDescontoRepository;
import com.graodireto.mvp.service.CupomDescontoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.graodireto.mvp.domain.CupomDesconto}.
 */
@Service
@Transactional
public class CupomDescontoServiceImpl implements CupomDescontoService {

    private final Logger log = LoggerFactory.getLogger(CupomDescontoServiceImpl.class);

    private final CupomDescontoRepository cupomDescontoRepository;

    public CupomDescontoServiceImpl(CupomDescontoRepository cupomDescontoRepository) {
        this.cupomDescontoRepository = cupomDescontoRepository;
    }

    @Override
    public CupomDesconto save(CupomDesconto cupomDesconto) {
        log.debug("Request to save CupomDesconto : {}", cupomDesconto);
        return cupomDescontoRepository.save(cupomDesconto);
    }

    @Override
    public CupomDesconto update(CupomDesconto cupomDesconto) {
        log.debug("Request to update CupomDesconto : {}", cupomDesconto);
        return cupomDescontoRepository.save(cupomDesconto);
    }

    @Override
    public Optional<CupomDesconto> partialUpdate(CupomDesconto cupomDesconto) {
        log.debug("Request to partially update CupomDesconto : {}", cupomDesconto);

        return cupomDescontoRepository
            .findById(cupomDesconto.getId())
            .map(existingCupomDesconto -> {
                if (cupomDesconto.getNome() != null) {
                    existingCupomDesconto.setNome(cupomDesconto.getNome());
                }
                if (cupomDesconto.getValorDesconto() != null) {
                    existingCupomDesconto.setValorDesconto(cupomDesconto.getValorDesconto());
                }
                if (cupomDesconto.getValorMinimo() != null) {
                    existingCupomDesconto.setValorMinimo(cupomDesconto.getValorMinimo());
                }
                if (cupomDesconto.getValorMinimoRegra() != null) {
                    existingCupomDesconto.setValorMinimoRegra(cupomDesconto.getValorMinimoRegra());
                }
                if (cupomDesconto.getDescricaoRegras() != null) {
                    existingCupomDesconto.setDescricaoRegras(cupomDesconto.getDescricaoRegras());
                }
                if (cupomDesconto.getValido() != null) {
                    existingCupomDesconto.setValido(cupomDesconto.getValido());
                }

                return existingCupomDesconto;
            })
            .map(cupomDescontoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CupomDesconto> findAll(Pageable pageable) {
        log.debug("Request to get all CupomDescontos");
        return cupomDescontoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CupomDesconto> findOne(Long id) {
        log.debug("Request to get CupomDesconto : {}", id);
        return cupomDescontoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CupomDesconto : {}", id);
        cupomDescontoRepository.deleteById(id);
    }
}

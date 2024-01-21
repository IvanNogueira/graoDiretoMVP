package com.graodireto.mvp.service.impl;

import com.graodireto.mvp.domain.Estabelecimento;
import com.graodireto.mvp.repository.EstabelecimentoRepository;
import com.graodireto.mvp.service.EstabelecimentoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.graodireto.mvp.domain.Estabelecimento}.
 */
@Service
@Transactional
public class EstabelecimentoServiceImpl implements EstabelecimentoService {

    private final Logger log = LoggerFactory.getLogger(EstabelecimentoServiceImpl.class);

    private final EstabelecimentoRepository estabelecimentoRepository;

    public EstabelecimentoServiceImpl(EstabelecimentoRepository estabelecimentoRepository) {
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    @Override
    public Estabelecimento save(Estabelecimento estabelecimento) {
        log.debug("Request to save Estabelecimento : {}", estabelecimento);
        return estabelecimentoRepository.save(estabelecimento);
    }

    @Override
    public Estabelecimento update(Estabelecimento estabelecimento) {
        log.debug("Request to update Estabelecimento : {}", estabelecimento);
        return estabelecimentoRepository.save(estabelecimento);
    }

    @Override
    public Optional<Estabelecimento> partialUpdate(Estabelecimento estabelecimento) {
        log.debug("Request to partially update Estabelecimento : {}", estabelecimento);

        return estabelecimentoRepository
            .findById(estabelecimento.getId())
            .map(existingEstabelecimento -> {
                if (estabelecimento.getNome() != null) {
                    existingEstabelecimento.setNome(estabelecimento.getNome());
                }
                if (estabelecimento.getTelefone() != null) {
                    existingEstabelecimento.setTelefone(estabelecimento.getTelefone());
                }
                if (estabelecimento.getEmail() != null) {
                    existingEstabelecimento.setEmail(estabelecimento.getEmail());
                }
                if (estabelecimento.getTipoEstabelecimento() != null) {
                    existingEstabelecimento.setTipoEstabelecimento(estabelecimento.getTipoEstabelecimento());
                }
                if (estabelecimento.getCapa() != null) {
                    existingEstabelecimento.setCapa(estabelecimento.getCapa());
                }
                if (estabelecimento.getCapaContentType() != null) {
                    existingEstabelecimento.setCapaContentType(estabelecimento.getCapaContentType());
                }
                if (estabelecimento.getLogo() != null) {
                    existingEstabelecimento.setLogo(estabelecimento.getLogo());
                }
                if (estabelecimento.getLogoContentType() != null) {
                    existingEstabelecimento.setLogoContentType(estabelecimento.getLogoContentType());
                }
                if (estabelecimento.getCriadoEm() != null) {
                    existingEstabelecimento.setCriadoEm(estabelecimento.getCriadoEm());
                }
                if (estabelecimento.getLogradouro() != null) {
                    existingEstabelecimento.setLogradouro(estabelecimento.getLogradouro());
                }
                if (estabelecimento.getNumero() != null) {
                    existingEstabelecimento.setNumero(estabelecimento.getNumero());
                }
                if (estabelecimento.getComplemento() != null) {
                    existingEstabelecimento.setComplemento(estabelecimento.getComplemento());
                }
                if (estabelecimento.getBairro() != null) {
                    existingEstabelecimento.setBairro(estabelecimento.getBairro());
                }
                if (estabelecimento.getCep() != null) {
                    existingEstabelecimento.setCep(estabelecimento.getCep());
                }

                return existingEstabelecimento;
            })
            .map(estabelecimentoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Estabelecimento> findAll(Pageable pageable) {
        log.debug("Request to get all Estabelecimentos");
        return estabelecimentoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Estabelecimento> findOne(Long id) {
        log.debug("Request to get Estabelecimento : {}", id);
        return estabelecimentoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Estabelecimento : {}", id);
        estabelecimentoRepository.deleteById(id);
    }
}

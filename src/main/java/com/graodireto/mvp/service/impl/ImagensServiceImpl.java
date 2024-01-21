package com.graodireto.mvp.service.impl;

import com.graodireto.mvp.domain.Imagens;
import com.graodireto.mvp.repository.ImagensRepository;
import com.graodireto.mvp.service.ImagensService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.graodireto.mvp.domain.Imagens}.
 */
@Service
@Transactional
public class ImagensServiceImpl implements ImagensService {

    private final Logger log = LoggerFactory.getLogger(ImagensServiceImpl.class);

    private final ImagensRepository imagensRepository;

    public ImagensServiceImpl(ImagensRepository imagensRepository) {
        this.imagensRepository = imagensRepository;
    }

    @Override
    public Imagens save(Imagens imagens) {
        log.debug("Request to save Imagens : {}", imagens);
        return imagensRepository.save(imagens);
    }

    @Override
    public Imagens update(Imagens imagens) {
        log.debug("Request to update Imagens : {}", imagens);
        return imagensRepository.save(imagens);
    }

    @Override
    public Optional<Imagens> partialUpdate(Imagens imagens) {
        log.debug("Request to partially update Imagens : {}", imagens);

        return imagensRepository
            .findById(imagens.getId())
            .map(existingImagens -> {
                if (imagens.getImagemContent() != null) {
                    existingImagens.setImagemContent(imagens.getImagemContent());
                }
                if (imagens.getImagemContentContentType() != null) {
                    existingImagens.setImagemContentContentType(imagens.getImagemContentContentType());
                }
                if (imagens.getImagemContentType() != null) {
                    existingImagens.setImagemContentType(imagens.getImagemContentType());
                }
                if (imagens.getLocalImagem() != null) {
                    existingImagens.setLocalImagem(imagens.getLocalImagem());
                }

                return existingImagens;
            })
            .map(imagensRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Imagens> findAll(Pageable pageable) {
        log.debug("Request to get all Imagens");
        return imagensRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Imagens> findOne(Long id) {
        log.debug("Request to get Imagens : {}", id);
        return imagensRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Imagens : {}", id);
        imagensRepository.deleteById(id);
    }
}

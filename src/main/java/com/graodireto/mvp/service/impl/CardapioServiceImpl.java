package com.graodireto.mvp.service.impl;

import com.graodireto.mvp.domain.Cardapio;
import com.graodireto.mvp.domain.User;
import com.graodireto.mvp.repository.CardapioRepository;
import com.graodireto.mvp.service.CardapioService;
import com.graodireto.mvp.service.UserService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.graodireto.mvp.domain.Cardapio}.
 */
@Service
@Transactional
public class CardapioServiceImpl implements CardapioService {

    private final Logger log = LoggerFactory.getLogger(CardapioServiceImpl.class);

    private final CardapioRepository cardapioRepository;
    private final UserService userService;

    public CardapioServiceImpl(CardapioRepository cardapioRepository, UserService userService) {
        this.cardapioRepository = cardapioRepository;
        this.userService = userService;
    }

    @Override
    public Cardapio save(Cardapio cardapio) {
        log.debug("Request to save Cardapio : {}", cardapio);
        cardapio.setDataCriacao(LocalDate.now());
        return cardapioRepository.save(cardapio);
    }

    @Override
    public Cardapio update(Cardapio cardapio) {
        log.debug("Request to update Cardapio : {}", cardapio);
        return cardapioRepository.save(cardapio);
    }

    @Override
    public Optional<Cardapio> partialUpdate(Cardapio cardapio) {
        log.debug("Request to partially update Cardapio : {}", cardapio);

        return cardapioRepository
            .findById(cardapio.getId())
            .map(existingCardapio -> {
                if (cardapio.getNome() != null) {
                    existingCardapio.setNome(cardapio.getNome());
                }
                if (cardapio.getDataCriacao() != null) {
                    existingCardapio.setDataCriacao(cardapio.getDataCriacao());
                }
                if (cardapio.getAtivo() != null) {
                    existingCardapio.setAtivo(cardapio.getAtivo());
                }

                return existingCardapio;
            })
            .map(cardapioRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cardapio> findAll(Pageable pageable) {
        log.debug("Request to get all Cardapios");
        return cardapioRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cardapio> findAllUserEstabelicimento(Long userId) {
        log.debug("Request to get all Cardapios");
        List<Cardapio> cardapios = cardapioRepository.findCardapiosByUserId(userId);
        return cardapios;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cardapio> findOne(Long id) {
        log.debug("Request to get Cardapio : {}", id);
        return cardapioRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cardapio : {}", id);
        cardapioRepository.deleteById(id);
    }
}

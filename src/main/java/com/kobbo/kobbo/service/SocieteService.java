package com.kobbo.kobbo.service;

import com.kobbo.kobbo.dto.societe.SocieteDto;
import com.kobbo.kobbo.entity.Societe;
import com.kobbo.kobbo.exception.DuplicateEntryException;
import com.kobbo.kobbo.mapper.SocieteMapper;
import com.kobbo.kobbo.repository.SocieteRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SocieteService {
    private final SocieteRepository societeRepository;
    private final SocieteMapper societeMapper;

    @Transactional
    public SocieteDto creerSociete(Societe societe) {
        //Contrôler le doublon sur l'email
        Societe societeExiste = societeRepository.findByEmail(societe.getEmail()).orElse(null);

        if (societeExiste != null) {
            throw new DuplicateEntryException("Société", societeExiste.getEmail());
        }

        Societe societeSaved = societeRepository.save(societe);

        return societeMapper.toDto(societeSaved);
    }
    
    @Transactional
    public Page<SocieteDto> listeSociete(Pageable pageable) {
        return societeRepository.findAll(pageable).map(societeMapper::toDto);
    }
}

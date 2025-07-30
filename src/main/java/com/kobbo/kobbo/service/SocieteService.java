package com.kobbo.kobbo.service;

import com.kobbo.kobbo.dto.societe.request.RegisterSocieteRequest;
import com.kobbo.kobbo.dto.societe.response.SocieteDto;
import com.kobbo.kobbo.entity.Societe;
import com.kobbo.kobbo.exception.DuplicateEntryException;
import com.kobbo.kobbo.exception.EntityNotFoundException;
import com.kobbo.kobbo.mapper.SocieteMapper;
import com.kobbo.kobbo.repository.SocieteRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class SocieteService {
    private final SocieteRepository societeRepository;
    private final SocieteMapper societeMapper;

    @Transactional
    public SocieteDto createSociete(RegisterSocieteRequest request) {
        Societe societe = societeMapper.toEntity(request);

        //Contrôler le doublon sur l'email
        if (getSocieteByEmail(societe.getEmail()) != null) {
            throw new DuplicateEntryException("Société", societe.getEmail());
        }

        return societeMapper.toDto(societeRepository.save(societe));
    }

    @Transactional
    public Page<SocieteDto> listSociete(Pageable pageable) {
        return societeRepository.findAll(pageable).map(societeMapper::toDto);
    }

    @Transactional
    public Societe getSocieteById(UUID id) {
        Societe societe = societeRepository.findById(id).orElse(null);

        if (societe == null) {
            throw new EntityNotFoundException("Société", id.toString());
        }

        return societe;
    }

    @Transactional
    public Societe getSocieteByEmail(String email) {
        //Contrôler le doublon sur l'email
        return societeRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public Societe updateSociete(UUID id, RegisterSocieteRequest request) {
        // Controller l'existence de la société
        Societe societe = getSocieteById(id);
        if (societe == null) {
            throw new EntityNotFoundException("Société", id.toString());
        }

        //Contrôler le doublon sur l'email
        Societe societeDuplicated = getSocieteByEmail(request.getEmail());

        if (societeDuplicated != null && !societeDuplicated.getId().equals(id)) {
            throw new DuplicateEntryException("Société", societeDuplicated.getEmail());
        }

        societeMapper.update(request, societe);

        return societeRepository.save(societe);
    }

    @Transactional
    public void deleteSociete(UUID id) {
        // Rechercher la société
        Societe societe = getSocieteById(id);
        if (societe == null) {
            throw new EntityNotFoundException("Société", id.toString());
        }

        societeRepository.delete(societe);
    }
}

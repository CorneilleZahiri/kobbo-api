package com.kobbo.kobbo.service;

import com.kobbo.kobbo.dto.societe.response.SocieteDto;
import com.kobbo.kobbo.dto.societe.response.SocieteTiersDto;
import com.kobbo.kobbo.dto.tiers.request.RegisterTiersRequest;
import com.kobbo.kobbo.dto.tiers.response.TiersDto;
import com.kobbo.kobbo.dto.tiers.response.TiersResponse;
import com.kobbo.kobbo.entity.Societe;
import com.kobbo.kobbo.entity.Tiers;
import com.kobbo.kobbo.exception.EntityNotFoundException;
import com.kobbo.kobbo.mapper.SocieteMapper;
import com.kobbo.kobbo.mapper.TiersMapper;
import com.kobbo.kobbo.repository.NatureRepository;
import com.kobbo.kobbo.repository.TiersRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TiersService {
    private final NatureRepository natureRepository;
    private final TiersMapper tiersMapper;
    private final SocieteMapper societeMapper;
    private final SocieteService societeService;
    private final TiersRepository tiersRepository;

    @Transactional
    public TiersDto createTiers(RegisterTiersRequest request, UUID societeId) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        Tiers tiers = tiersMapper.toEntity(request);
        tiers.setSociete(societe);

        return tiersMapper.toDto(tiersRepository.save(tiers));
    }

    @Transactional
    public SocieteTiersDto getAllTiersBySocieteId(UUID SocieteId, Pageable pageable) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(SocieteId);

        SocieteDto societeDto = societeMapper.toDto(societe);

        Page<TiersResponse> tiersResponsePage = tiersRepository
                .findBySocieteId(SocieteId, pageable)
                .map(tiersMapper::toTiersResponse);

        return new SocieteTiersDto(societeDto, tiersResponsePage);
    }

    @Transactional
    public TiersDto getTiersByIdAndSocieteId(UUID tiersId, UUID societeId) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        Tiers tiers = tiersRepository.findByIdAndSocieteId(tiersId, societeId).orElse(null);
        if (tiers == null) {
            throw new EntityNotFoundException("Ce tiers",
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        return tiersMapper.toDto(tiers);
    }

    @Transactional
    public TiersDto modifyTiersByIdAndSocieteId(UUID tiersId,
                                                UUID societeId,
                                                RegisterTiersRequest request) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        //Vérifier que le responsable existe
        Tiers tiers = tiersRepository.findByIdAndSocieteId(tiersId, societeId).orElse(null);
        if (tiers == null) {
            throw new EntityNotFoundException("Ce tiers",
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        tiersMapper.update(request, tiers);

        return tiersMapper.toDto(tiersRepository.save(tiers));
    }

    @Transactional
    public void deleteTiers(UUID tiersId, UUID societeId) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        //Vérifier que le tiers existe
        Tiers tiers = tiersRepository.findByIdAndSocieteId(tiersId, societeId).orElse(null);
        if (tiers == null) {
            throw new EntityNotFoundException("Ce tiers",
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        tiersRepository.deleteById(tiers.getId());
    }
}

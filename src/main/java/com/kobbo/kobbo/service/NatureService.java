package com.kobbo.kobbo.service;

import com.kobbo.kobbo.dto.nature.request.RegisterNatureRequest;
import com.kobbo.kobbo.dto.nature.response.NatureDto;
import com.kobbo.kobbo.dto.nature.response.NatureResponse;
import com.kobbo.kobbo.dto.societe.response.SocieteDto;
import com.kobbo.kobbo.dto.societe.response.SocieteNatureDto;
import com.kobbo.kobbo.entity.Nature;
import com.kobbo.kobbo.entity.Societe;
import com.kobbo.kobbo.exception.DuplicateEntryException;
import com.kobbo.kobbo.exception.EntityNotFoundException;
import com.kobbo.kobbo.mapper.NatureMapper;
import com.kobbo.kobbo.mapper.SocieteMapper;
import com.kobbo.kobbo.repository.NatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class NatureService {
    private final NatureRepository natureRepository;
    private final NatureMapper natureMapper;
    private final SocieteMapper societeMapper;
    private final SocieteService societeService;

    public NatureDto createNature(RegisterNatureRequest request, UUID societeId) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        //Contrôler le doublon sur l'intitulé
        if (getNatureByIntituleAndSocieteId(request.getIntitule(), societe.getId()) != null) {
            throw new DuplicateEntryException("La nature " + request.getIntitule(),
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        Nature nature = natureMapper.toEntity(request);
        nature.setSociete(societe);

        return natureMapper.toDto(natureRepository.save(nature));
    }

    public Nature getNatureByIntituleAndSocieteId(String intitule, UUID id) {
        return natureRepository.findByIntituleAndSocieteId(intitule, id).orElse(null);
    }

    public SocieteNatureDto getAllNatureBySocieteId(UUID id, Pageable pageable) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(id);

        SocieteDto societeDto = societeMapper.toDto(societe);

        Page<NatureResponse> natureResponsePage = natureRepository
                .findBySocieteId(id, pageable)
                .map(natureMapper::toNatureResponse);

        return new SocieteNatureDto(societeDto, natureResponsePage);
    }


    public NatureDto getNatureByIdAndSocieteId(Long natureId, UUID societeId) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        Nature nature = natureRepository.findByIdAndSocieteId(natureId, societeId).orElse(null);
        if (nature == null) {
            throw new EntityNotFoundException("Cette nature", societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        return natureMapper.toDto(nature);
    }

}

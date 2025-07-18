package com.kobbo.kobbo.service;

import com.kobbo.kobbo.dto.responsable.request.RegisterResponsableRequest;
import com.kobbo.kobbo.dto.responsable.response.ResponsableDto;
import com.kobbo.kobbo.dto.responsable.response.ResponsableResponse;
import com.kobbo.kobbo.dto.societe.response.SocieteDto;
import com.kobbo.kobbo.dto.societe.response.SocieteResponsableDto;
import com.kobbo.kobbo.entity.Responsable;
import com.kobbo.kobbo.entity.Societe;
import com.kobbo.kobbo.exception.EntityNotFoundException;
import com.kobbo.kobbo.mapper.ResponsableMapper;
import com.kobbo.kobbo.mapper.SocieteMapper;
import com.kobbo.kobbo.repository.NatureRepository;
import com.kobbo.kobbo.repository.ResponsableRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ResponsableService {
    private final NatureRepository natureRepository;
    private final ResponsableMapper responsableMapper;
    private final SocieteMapper societeMapper;
    private final SocieteService societeService;
    private final ResponsableRepository responsableRepository;

    public ResponsableDto createResponsable(RegisterResponsableRequest request, UUID societeId) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        Responsable responsable = responsableMapper.toEntity(request);
        responsable.setSociete(societe);

        return responsableMapper.toDto(responsableRepository.save(responsable));
    }

    public SocieteResponsableDto getAllResponsableBySocieteId(UUID SocieteId, Pageable pageable) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(SocieteId);

        SocieteDto societeDto = societeMapper.toDto(societe);

        Page<ResponsableResponse> responsableResponsePage = responsableRepository
                .findBySocieteId(SocieteId, pageable)
                .map(responsableMapper::toResponsableResponse);

        return new SocieteResponsableDto(societeDto, responsableResponsePage);
    }

    public ResponsableDto getResponsableByIdAndSocieteId(UUID responsableId, UUID societeId) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        Responsable responsable = responsableRepository.findByIdAndSocieteId(responsableId, societeId).orElse(null);
        if (responsable == null) {
            throw new EntityNotFoundException("Ce responsable",
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        return responsableMapper.toDto(responsable);
    }

    public ResponsableDto modifyResponsableByIdAndSocieteId(UUID responsableId,
                                                            UUID societeId,
                                                            RegisterResponsableRequest request) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        //Vérifier que le responsable existe
        Responsable responsable = responsableRepository.findByIdAndSocieteId(responsableId, societeId).orElse(null);
        if (responsable == null) {
            throw new EntityNotFoundException("Ce responsable",
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        responsableMapper.update(request, responsable);

        return responsableMapper.toDto(responsableRepository.save(responsable));
    }

    public void deleteResponsable(UUID responsableId, UUID societeId) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        //Vérifier que le responsable existe
        Responsable responsable = responsableRepository.findByIdAndSocieteId(responsableId, societeId).orElse(null);
        if (responsable == null) {
            throw new EntityNotFoundException("Ce responsable",
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        responsableRepository.deleteById(responsable.getId());
    }
}

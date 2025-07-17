package com.kobbo.kobbo.service;

import com.kobbo.kobbo.dto.nature.NatureDto;
import com.kobbo.kobbo.dto.nature.RegisterNatureRequest;
import com.kobbo.kobbo.entity.Nature;
import com.kobbo.kobbo.entity.Societe;
import com.kobbo.kobbo.exception.DuplicateEntryException;
import com.kobbo.kobbo.mapper.NatureMapper;
import com.kobbo.kobbo.repository.NatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class NatureService {
    private final NatureRepository natureRepository;
    private final NatureMapper natureMapper;
    private final SocieteService societeService;

    public NatureDto createNature(RegisterNatureRequest request, UUID societeID) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeID);

        //Contrôler le doublon sur l'intitulé
        if (getNatureByIntitule(request.getIntitule(), societe) != null) {
            throw new DuplicateEntryException("La nature " + request.getIntitule(), societe.getRaisonSociale());
        }

        Nature nature = natureMapper.toEntity(request);
        nature.setSociete(societe);

        return natureMapper.toDto(natureRepository.save(nature));
    }

    public Nature getNatureByIntitule(String intitule, Societe societe) {
        return natureRepository.findByIntituleAndSociete(intitule, societe).orElse(null);
    }
}

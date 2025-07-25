package com.kobbo.kobbo.service;

import com.kobbo.kobbo.dto.operation.request.RegisterOperationRequest;
import com.kobbo.kobbo.dto.operation.response.OperationDto;
import com.kobbo.kobbo.entity.*;
import com.kobbo.kobbo.enumeration.Statut;
import com.kobbo.kobbo.enumeration.TypeOperation;
import com.kobbo.kobbo.exception.EntityNotFoundException;
import com.kobbo.kobbo.mapper.OperationMapper;
import com.kobbo.kobbo.repository.NatureRepository;
import com.kobbo.kobbo.repository.OperationRepository;
import com.kobbo.kobbo.repository.TiersRepository;
import com.kobbo.kobbo.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OperationService {
    private final OperationRepository operationRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final TiersRepository tiersRepository;
    private final NatureRepository natureRepository;
    private final OperationMapper operationMapper;

    public OperationDto createOperation(RegisterOperationRequest request) {
        //Vérifier le statut
        if (!Statut.listStatut().contains(request.getStatut().toUpperCase())
                || request.getStatut().equalsIgnoreCase(Statut.SUPPRIMER.toString())) {
            throw new EntityNotFoundException("Statut ", request.getStatut());
        }

        //Vérifier le typeOperation
        if (!TypeOperation.listTypeOperation().contains(request.getTypeOperation().toUpperCase())) {
            throw new EntityNotFoundException("Type opération ", request.getTypeOperation());
        }

        //idSociete
        Societe societe = null;
        Nature nature = null;
        Tiers tiers = null;

        //Rechercher l'utilisateur
        Utilisateur utilisateur = utilisateurRepository.findById(request.getUtilisateurId()).orElse(null);
        if (utilisateur != null) {
            societe = utilisateur.getRole().getSociete();

            if (societe != null) {
                //Rechercher le tiers
                tiers = tiersRepository.findByIdAndSocieteId(request.getTiersId(), societe.getId()).orElse(null);
                if (tiers == null) {
                    throw new EntityNotFoundException("Ce tiers",
                            societe.getId().toString() + " (" + societe.getRaisonSociale() + ")");
                }

                //Rechercher la nature
                nature = natureRepository.findByIdAndSocieteId(request.getNatureId(), societe.getId()).orElse(null);
                if (nature == null) {
                    throw new EntityNotFoundException("Cette nature",
                            societe.getId().toString() + " (" + societe.getRaisonSociale() + ")");
                }
            }
        }

        //Convertir le RegisterOperationRequest en Operation
        Operation operation = operationMapper.registerToEntity(request);
        operation.setNature(nature);
        operation.setTiers(tiers);
        operation.setUtilisateur(utilisateur);

        //Enregister et convertir en Dto
        return operationMapper.toDto(operationRepository.save(operation));
    }

}

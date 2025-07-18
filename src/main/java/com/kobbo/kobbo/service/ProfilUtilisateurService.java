package com.kobbo.kobbo.service;

import com.kobbo.kobbo.dto.profilUtilisateur.request.RegisterProfilUtilisateurRequest;
import com.kobbo.kobbo.dto.profilUtilisateur.response.ProfilUtilisateurDto;
import com.kobbo.kobbo.dto.profilUtilisateur.response.ProfilUtilisateurResponse;
import com.kobbo.kobbo.dto.societe.response.SocieteDto;
import com.kobbo.kobbo.dto.societe.response.SocieteProfilUtilisateurDto;
import com.kobbo.kobbo.entity.ProfilUtilisateur;
import com.kobbo.kobbo.entity.Societe;
import com.kobbo.kobbo.exception.DuplicateEntryException;
import com.kobbo.kobbo.exception.EntityNotFoundException;
import com.kobbo.kobbo.mapper.ProfilUtilisateurMapper;
import com.kobbo.kobbo.mapper.SocieteMapper;
import com.kobbo.kobbo.repository.ProfilUtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ProfilUtilisateurService {
    private final ProfilUtilisateurRepository profilUtilisateurRepository;
    private final ProfilUtilisateurMapper profilUtilisateurMapper;
    private final SocieteMapper societeMapper;
    private final SocieteService societeService;

    public ProfilUtilisateurDto createProfilUtilisateur(RegisterProfilUtilisateurRequest request, UUID societeId) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        //Contrôler le doublon sur l'intitulé
        if (getProfilUtilisateurByLibelleAndSocieteId(request.getLibelle(), societe.getId()) != null) {
            throw new DuplicateEntryException("Le profil " + request.getLibelle(),
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        ProfilUtilisateur profilUtilisateur = profilUtilisateurMapper.toEntity(request);
        profilUtilisateur.setSociete(societe);

        return profilUtilisateurMapper.toDto(profilUtilisateurRepository.save(profilUtilisateur));
    }

    public ProfilUtilisateur getProfilUtilisateurByLibelleAndSocieteId(String intitule, UUID id) {
        return profilUtilisateurRepository.findByLibelleAndSocieteId(intitule, id).orElse(null);
    }

    public SocieteProfilUtilisateurDto getAllProfilUtilisateurBySocieteId(UUID societeId, Pageable pageable) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        SocieteDto societeDto = societeMapper.toDto(societe);

        Page<ProfilUtilisateurResponse> profilUtilisateurResponsePage = profilUtilisateurRepository
                .findBySocieteId(societeId, pageable)
                .map(profilUtilisateurMapper::toProfilUtilisateurResponse);

        return new SocieteProfilUtilisateurDto(societeDto, profilUtilisateurResponsePage);
    }

    public ProfilUtilisateurDto getProfilUtilisateurByIdAndSocieteId(Long profilUtilisateurId, UUID societeId) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        ProfilUtilisateur profilUtilisateur = profilUtilisateurRepository.findByIdAndSocieteId(profilUtilisateurId, societeId).orElse(null);
        if (profilUtilisateur == null) {
            throw new EntityNotFoundException("Ce profil ",
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        return profilUtilisateurMapper.toDto(profilUtilisateur);
    }

    public ProfilUtilisateurDto modifyProfilUtilisateurByIdAndSocieteId(Long profilUtilisateurId, UUID societeId, RegisterProfilUtilisateurRequest request) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        //Vérifier que la nature existe
        ProfilUtilisateur profilUtilisateur = profilUtilisateurRepository.findByIdAndSocieteId(profilUtilisateurId, societeId).orElse(null);
        if (profilUtilisateur == null) {
            throw new EntityNotFoundException("Ce profil ",
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        //Contrôler le doublon sur l'intitulé dans cette société
        ProfilUtilisateur profilUtilisateurDuplicated = getProfilUtilisateurByLibelleAndSocieteId(request.getLibelle(), societe.getId());

        if (profilUtilisateurDuplicated != null && !profilUtilisateurDuplicated.getId().equals(profilUtilisateurId)) {
            throw new DuplicateEntryException("Le profil " + request.getLibelle(),
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        profilUtilisateurMapper.update(request, profilUtilisateur);

        return profilUtilisateurMapper.toDto(profilUtilisateurRepository.save(profilUtilisateur));
    }

    public void deleteProfilUtilisateur(Long profilUtilisateurId, UUID societeId) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        //Vérifier que la nature existe
        ProfilUtilisateur profilUtilisateur = profilUtilisateurRepository.findByIdAndSocieteId(profilUtilisateurId, societeId).orElse(null);
        if (profilUtilisateur == null) {
            throw new EntityNotFoundException("Ce profil ",
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        profilUtilisateurRepository.deleteById(profilUtilisateur.getId());
    }
}

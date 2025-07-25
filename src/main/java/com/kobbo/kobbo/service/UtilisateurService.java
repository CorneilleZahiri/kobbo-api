package com.kobbo.kobbo.service;

import com.kobbo.kobbo.dto.societe.response.SocieteDto;
import com.kobbo.kobbo.dto.societe.response.SocieteUtilisateurDto;
import com.kobbo.kobbo.dto.utilisateur.request.RegisterUtilisateurRequest;
import com.kobbo.kobbo.dto.utilisateur.request.UpdateUtilisateurRequest;
import com.kobbo.kobbo.dto.utilisateur.response.UtilisateurDto;
import com.kobbo.kobbo.dto.utilisateur.response.UtilisateurResponse;
import com.kobbo.kobbo.entity.Role;
import com.kobbo.kobbo.entity.Societe;
import com.kobbo.kobbo.entity.Utilisateur;
import com.kobbo.kobbo.exception.DuplicateEntryException;
import com.kobbo.kobbo.exception.EntityNotFoundException;
import com.kobbo.kobbo.mapper.SocieteMapper;
import com.kobbo.kobbo.mapper.UtilisateurMapper;
import com.kobbo.kobbo.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;
    private final SocieteMapper societeMapper;
    private final SocieteService societeService;
    private final RoleService roleService;

    @Transactional
    public UtilisateurDto createUtilisateur(RegisterUtilisateurRequest request, UUID societeId, Role role) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        //Contrôler le doublon sur l'email
        if (getUtilisateurByEmailAndSocieteId(request.getEmail(), societe.getId()) != null) {
            throw new DuplicateEntryException("L'utilisateur " + request.getEmail(),
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        Utilisateur utilisateur = utilisateurMapper.toEntity(request);
        utilisateur.setRole(role);

        return utilisateurMapper.toDto(utilisateurRepository.save(utilisateur));
    }

    @Transactional
    public Utilisateur getUtilisateurByEmailAndSocieteId(String email, UUID societeId) {
        return utilisateurRepository.findByEmailAndSocieteId(email, societeId).orElse(null);
    }

    @Transactional
    public SocieteUtilisateurDto getAllUtilisateurBySocieteId(UUID societeId, Pageable pageable) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        SocieteDto societeDto = societeMapper.toDto(societe);

        Page<UtilisateurResponse> utilisateurResponsePage = utilisateurRepository
                .findBySocieteId(societeId, pageable)
                .map(utilisateurMapper::toUtilisateurResponse);

        return new SocieteUtilisateurDto(societeDto, utilisateurResponsePage);
    }

    @Transactional
    public UtilisateurDto getUtilisateurByIdAndSocieteId(UUID utilisateurId, UUID societeId) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        Utilisateur utilisateur = utilisateurRepository.findByIdAndSocieteId(utilisateurId, societeId).orElse(null);
        if (utilisateur == null) {
            throw new EntityNotFoundException("Cet utilisateur",
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        return utilisateurMapper.toDto(utilisateur);
    }

    @Transactional
    public UtilisateurDto modifyUtilisateurByIdAndSocieteId(UUID utilisateurId, UUID societeId, UpdateUtilisateurRequest request) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);
        //Rechercher le profil utilisateur dans la société
        Role role = roleService.getRoleByIdAndSocieteId(request.getRoleId(), societeId);

        //Vérifier que l'utilisateur existe
        Utilisateur utilisateur = utilisateurRepository.findByIdAndSocieteId(utilisateurId, societeId).orElse(null);
        if (utilisateur == null) {
            throw new EntityNotFoundException("Cet utilisateur ",
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        //Contrôler le doublon sur l'email dans cette société
        Utilisateur utilisateurDuplicated = getUtilisateurByEmailAndSocieteId(request.getEmail(), societe.getId());

        if (utilisateurDuplicated != null && !utilisateurDuplicated.getId().equals(utilisateurId)) {
            throw new DuplicateEntryException("L'utilisateur " + request.getEmail(),
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        utilisateurMapper.update(request, utilisateur);
        utilisateur.setRole(role);

        return utilisateurMapper.toDto(utilisateurRepository.save(utilisateur));
    }

    @Transactional
    public void deleteUtilisateur(UUID utilisateurId, UUID societeId) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        //Vérifier que l'utilisateur existe
        Utilisateur utilisateur = utilisateurRepository.findByIdAndSocieteId(utilisateurId, societeId).orElse(null);
        if (utilisateur == null) {
            throw new EntityNotFoundException("Cet utilisateur",
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        utilisateurRepository.deleteById(utilisateur.getId());
    }
}

package com.kobbo.kobbo.service;

import com.kobbo.kobbo.dto.utilisateur.request.RegisterUtilisateurRequest;
import com.kobbo.kobbo.dto.utilisateur.request.UpdateUtilisateurRequest;
import com.kobbo.kobbo.dto.utilisateur.response.UtilisateurDto;
import com.kobbo.kobbo.entity.Utilisateur;
import com.kobbo.kobbo.exception.DuplicateEntryException;
import com.kobbo.kobbo.exception.EntityNotFoundException;
import com.kobbo.kobbo.mapper.SocieteMapper;
import com.kobbo.kobbo.mapper.UtilisateurMapper;
import com.kobbo.kobbo.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
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
    public UtilisateurDto createUtilisateur(RegisterUtilisateurRequest request) {
        //Contrôler le doublon sur l'email
        if (getUtilisateurByEmail(request.getEmail()) != null) {
            throw new DuplicateEntryException("L'utilisateur ", request.getEmail());
        }

        Utilisateur utilisateur = utilisateurMapper.toEntity(request);

        return utilisateurMapper.toDto(utilisateurRepository.save(utilisateur));
    }

    @Transactional
    public Utilisateur getUtilisateurByEmail(String email) {
        return utilisateurRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public UtilisateurDto getUtilisateurById(UUID utilisateurId) {

        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).orElse(null);
        if (utilisateur == null) {
            throw new EntityNotFoundException("Cet utilisateur", utilisateurId);
        }

        return utilisateurMapper.toDto(utilisateur);
    }

    @Transactional
    public UtilisateurDto updateUtilisateurById(UUID utilisateurId, UpdateUtilisateurRequest request) {
        //Vérifier que l'utilisateur existe
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).orElse(null);
        if (utilisateur == null) {
            throw new EntityNotFoundException("Cet utilisateur ", utilisateurId);
        }

        //Contrôler le doublon sur l'email dans cette société
        Utilisateur utilisateurDuplicated = getUtilisateurByEmail(request.getEmail());

        if (utilisateurDuplicated != null && !utilisateurDuplicated.getId().equals(utilisateur.getId())) {
            throw new DuplicateEntryException("L'utilisateur ", request.getEmail());
        }

        utilisateurMapper.update(request, utilisateur);

        return utilisateurMapper.toDto(utilisateurRepository.save(utilisateur));
    }

    @Transactional
    public void deleteUtilisateur(UUID utilisateurId) {
        //Vérifier que l'utilisateur existe
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).orElse(null);
        if (utilisateur == null) {
            throw new EntityNotFoundException("Cet utilisateur", utilisateurId);
        }

        utilisateurRepository.deleteById(utilisateur.getId());
    }
}

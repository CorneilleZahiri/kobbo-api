package com.kobbo.kobbo.service;

import com.kobbo.kobbo.dto.utilisateur.request.RegisterUtilisateurRequest;
import com.kobbo.kobbo.dto.utilisateur.request.UpdateUtilisateurMotDePasse;
import com.kobbo.kobbo.dto.utilisateur.request.UpdateUtilisateurRequest;
import com.kobbo.kobbo.dto.utilisateur.response.UtilisateurDto;
import com.kobbo.kobbo.entity.Utilisateur;
import com.kobbo.kobbo.exception.DuplicateEntryException;
import com.kobbo.kobbo.exception.EntityNotFoundException;
import com.kobbo.kobbo.mapper.UtilisateurMapper;
import com.kobbo.kobbo.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UtilisateurService implements UserDetailsService {
    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UtilisateurDto createUtilisateur(RegisterUtilisateurRequest request) {
        //Contrôler le doublon sur l'email
        if (getUtilisateurByEmail(request.getEmail()) != null) {
            throw new DuplicateEntryException("L'utilisateur ", request.getEmail());
        }

        Utilisateur utilisateur = utilisateurMapper.toEntity(request);
        //Crypter le mot de passe
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));

        return utilisateurMapper.toDto(utilisateurRepository.save(utilisateur));
    }

    @Transactional
    public Utilisateur getUtilisateurByEmail(String email) {
        return utilisateurRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public Utilisateur getUtilisateurById(UUID utilisateurId) {

        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).orElse(null);
        if (utilisateur == null) {
            throw new EntityNotFoundException("Cet utilisateur", utilisateurId);
        }

        return utilisateur;
    }

    @Transactional
    public UtilisateurDto updateUtilisateurById(UUID utilisateurId, UpdateUtilisateurRequest request) {
        //Vérifier que l'utilisateur existe
        Utilisateur utilisateur = this.getUtilisateurById(utilisateurId);

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
        Utilisateur utilisateur = this.getUtilisateurById(utilisateurId);

        utilisateurRepository.deleteById(utilisateur.getId());
    }

    public void updateMotDePasse(UUID utilisateurId, UpdateUtilisateurMotDePasse updateUtilisateurMotDePasse) {
        //Vérifier que l'utilisateur existe
        Utilisateur utilisateur = this.getUtilisateurById(utilisateurId);

        //Vérifier si l'ancien mot est réellement saisi par l'utilisateur propriétaire
        if (!utilisateur.getMotDePasse().equals(updateUtilisateurMotDePasse.getAncienMotDePasse())) {
            throw new IllegalArgumentException("L'ancien mot de passe est incorrecte");
        }

        //Modifier
        utilisateur.setMotDePasse(updateUtilisateurMotDePasse.getNouveauMotDePasse());
        utilisateurRepository.save(utilisateur);
    }

    public UtilisateurDto me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = (UUID) authentication.getPrincipal();

        Utilisateur utilisateur = getUtilisateurById(userId);

        return utilisateurMapper.toDto(utilisateur);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Utilisateur utilisateur = getUtilisateurByEmail(email);

        return new User(
                utilisateur.getEmail(),
                utilisateur.getMotDePasse(),
                Collections.emptyList());
    }
}

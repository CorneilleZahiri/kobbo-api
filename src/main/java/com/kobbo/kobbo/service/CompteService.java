package com.kobbo.kobbo.service;

import com.kobbo.kobbo.config.AuthUtils;
import com.kobbo.kobbo.dto.comptes.response.CompteDto;
import com.kobbo.kobbo.dto.societe.request.RegisterSocieteRequest;
import com.kobbo.kobbo.entity.Compte;
import com.kobbo.kobbo.entity.Role;
import com.kobbo.kobbo.entity.Societe;
import com.kobbo.kobbo.entity.Utilisateur;
import com.kobbo.kobbo.exception.DuplicateEntryException;
import com.kobbo.kobbo.mapper.CompteMapper;
import com.kobbo.kobbo.repository.ComptesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CompteService {
    private final SocieteService societeService;
    private final AuthUtils authUtils;
    private final RoleService roleService;
    private final ComptesRepository comptesRepository;
    private final CompteMapper compteMapper;
    private final UtilisateurService utilisateurService;

    private final String ROLE_PROPRIETAIRE = "PROPRIETAIRE";

    @Transactional
    public CompteDto createCompte(RegisterSocieteRequest request) {
        //1- Create Societe
        Societe societe = societeService.createSociete(request);

        //2- Create Role
        Role role = roleService.createRole(ROLE_PROPRIETAIRE, societe);

        //3- Utilisateur
        UUID utilisateurId = authUtils.getCurrentUserId();
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(utilisateurId);

        //Vérifier si l'utilisateur a déjà un compte dans cette société
        Compte compte = comptesRepository.findByUtilisateurIdAndRoleId(utilisateurId, role.getId()).orElse(null);
        if (compte != null) {
            throw new DuplicateEntryException("L'utilisateur " + utilisateur.getNom(),
                    role.getLibelle() + " dans la société " + role.getSociete().getRaisonSociale());
        }

        //4- Créer compte société
        compte = new Compte();
        compte.setRole(role);
        compte.setUtilisateur(utilisateur);
        compte.setActif(true);

        return compteMapper.toDto(comptesRepository.save(compte));
    }
}

package com.kobbo.kobbo.service;

import com.kobbo.kobbo.config.AuthUtils;
import com.kobbo.kobbo.dto.comptes.response.CompteDto;
import com.kobbo.kobbo.dto.societe.request.RegisterSocieteRequest;
import com.kobbo.kobbo.entity.Compte;
import com.kobbo.kobbo.entity.Role;
import com.kobbo.kobbo.entity.Societe;
import com.kobbo.kobbo.mapper.CompteMapper;
import com.kobbo.kobbo.mapper.SocieteMapper;
import com.kobbo.kobbo.repository.ComptesRepository;
import com.kobbo.kobbo.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CompteService {
    private final SocieteService societeService;
    private final SocieteMapper societeMapper;
    private final AuthUtils authUtils;
    private final RoleRepository roleRepository;
    private final ComptesRepository comptesRepository;
    private final CompteMapper compteMapper;
    private final UtilisateurService utilisateurService;
    
    private final String ROLE_PROPRIETAIRE = "PROPRIETAIRE";

    @Transactional
    public CompteDto createCompte(RegisterSocieteRequest request) {
        //1- Create Societe
        Societe societe = societeMapper.societeDtoToEntity(societeService.createSociete(request));

        //2- Create Role
        Role role = new Role();
        role.setLibelle(ROLE_PROPRIETAIRE);
        role.setSociete(societe);
        roleRepository.save(role);

        //3- Utilisateur
        UUID utilisateurId = authUtils.getCurrentUserId();

        //4- Créer compte société
        Compte compte = new Compte();
        compte.setRole(role);
        compte.setUtilisateur(utilisateurService.getUtilisateurById(utilisateurId));

        return compteMapper.toDto(comptesRepository.save(compte));
    }
}

package com.kobbo.kobbo.service;

import com.kobbo.kobbo.config.AuthUtils;
import com.kobbo.kobbo.config.JwtConfig;
import com.kobbo.kobbo.dto.comptes.request.CompteRequest;
import com.kobbo.kobbo.dto.comptes.response.CompteDto;
import com.kobbo.kobbo.dto.comptes.response.CompteResponse;
import com.kobbo.kobbo.dto.jwt.response.TokenPairResponse;
import com.kobbo.kobbo.dto.societe.request.RegisterSocieteRequest;
import com.kobbo.kobbo.entity.Compte;
import com.kobbo.kobbo.entity.Role;
import com.kobbo.kobbo.entity.Societe;
import com.kobbo.kobbo.entity.Utilisateur;
import com.kobbo.kobbo.exception.DuplicateEntryException;
import com.kobbo.kobbo.exception.EntityNotFoundException;
import com.kobbo.kobbo.exception.InvalideArgumentException;
import com.kobbo.kobbo.mapper.CompteMapper;
import com.kobbo.kobbo.repository.ComptesRepository;
import com.kobbo.kobbo.repository.JwtRedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;
    private final JwtRedisRepository jwtRedisRepository;
    private final JwtRedisService jwtRedisService;

    private final String ROLE_PROPRIETAIRE = "PROPRIETAIRE";

    @Transactional
    public CompteDto createCompte(RegisterSocieteRequest request) {
        //1- Create Societe
        Societe societe = societeService.createSociete(request);

        //2- Create Role
        Role role = roleService.createRole(ROLE_PROPRIETAIRE, societe);

        //Utilisateur connecté
        Utilisateur utilisateur = getUtilisateurConnecte();

        //Vérifier si l'utilisateur a déjà un compte dans cette société
        Compte compte = comptesRepository.findByUtilisateurIdAndRoleId(utilisateur.getId(), role.getId()).orElse(null);
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

    @Transactional
    public List<CompteResponse> listCompte() {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(getUtilisateurConnecte().getId());

        return comptesRepository
                .findByUtilisateurId(utilisateur.getId())
                .stream().map(compteMapper::toCompteResponse).toList();
    }

    @Transactional
    public TokenPairResponse selectCompte(CompteRequest request) {
        //Vérifier si le token de connexion a été utilisé pour sélectionner une société
        jwtRedisService.verifyIfTokenIsUsedToSelect();

        // 1 - hasSociete = false ?
        verifyIfIsPossibleToSelectCompte();

        // 2 - compteId exists and belong to user
        Compte compte = verifyIfCompteExistsAndBelongToUser(request);

        // Générer le jeton enrichir. Il va contenir en plus de l'user, compteId, role
        Jwt accessToken = jwtService.generateAccessToken(getUtilisateurConnecte(),
                true, compte.getId(), compte.getRole().getLibelle());

        Jwt refreshToken = jwtService.generateRefreshToken(getUtilisateurConnecte(),
                true, compte.getId(), compte.getRole().getLibelle());

        return new TokenPairResponse(accessToken, refreshToken);
    }

    @Transactional
    public void verifyIfIsPossibleToSelectCompte() {
        // 1 - hasSociete = false ?
        if (authUtils.getCurrentUser().hasSociete()) {
            throw new InvalideArgumentException("Le jeton utilisé ne permet pas la sélection " +
                    "d'un compte société car il a déjà servi à faire de sélection.");
        }
    }

    @Transactional
    public Compte verifyIfCompteExistsAndBelongToUser(CompteRequest request) {
        // 2 - compteId existe ?
        Compte compte = comptesRepository.findById(request.getId()).orElse(null);
        if (compte == null) {
            throw new EntityNotFoundException("Le compte ", request.getId());
        }

        // 3 - appartient à l'user connecté ?
        if (!compte.getUtilisateur().getId().equals(authUtils.getCurrentUser().getUserId())) {
            throw new InvalideArgumentException("L'utilisateur connecté n'a jamais été affecté à cette société");
        }

        // 4 - actif = true ?
        if (!compte.getActif()) {
            throw new InvalideArgumentException("Le compte a été désactivé pour l'utilisateur connecté");
        }

        return compte;
    }

    @Transactional
    public Utilisateur getUtilisateurConnecte() {
        UUID utilisateurId = authUtils.getCurrentUser().getUserId();
        return utilisateurService.getUtilisateurById(utilisateurId);
    }
}

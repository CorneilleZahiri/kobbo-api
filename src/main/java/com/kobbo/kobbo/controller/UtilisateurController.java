package com.kobbo.kobbo.controller;

import com.kobbo.kobbo.dto.societe.response.SocieteUtilisateurDto;
import com.kobbo.kobbo.dto.utilisateur.request.RegisterUtilisateurRequest;
import com.kobbo.kobbo.dto.utilisateur.request.UpdateUtilisateurRequest;
import com.kobbo.kobbo.dto.utilisateur.response.UtilisateurDto;
import com.kobbo.kobbo.entity.ProfilUtilisateur;
import com.kobbo.kobbo.service.ProfilUtilisateurService;
import com.kobbo.kobbo.service.UtilisateurService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/societes")
@AllArgsConstructor
public class UtilisateurController {
    private final UtilisateurService utilisateurService;
    private final ProfilUtilisateurService profilUtilisateurService;

    @PostMapping("/{societeId}/profils/{profilId}/utilisateurs")
    public ResponseEntity<UtilisateurDto> createUtilisateur(@PathVariable(name = "societeId") UUID societeId,
                                                            @PathVariable(name = "profilId") Long profilId,
                                                            @Valid @RequestBody RegisterUtilisateurRequest request,
                                                            UriComponentsBuilder uriComponentsBuilder) {

        ProfilUtilisateur profilUtilisateur = profilUtilisateurService.getProfilUtilisateurByIdAndSocieteId(profilId, societeId);
        UtilisateurDto utilisateurDto = utilisateurService.createUtilisateur(request, societeId, profilUtilisateur);

        URI location = uriComponentsBuilder.path("/societes/{societeId}/profils/{profilId}/utilisateurs/{id}")
                .buildAndExpand(profilUtilisateur.getSociete().getId(), profilUtilisateur.getId(),
                        utilisateurDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(utilisateurDto);
    }

    @GetMapping("/{id}/utilisateurs")
    public ResponseEntity<SocieteUtilisateurDto> listUtilisateur(@PathVariable(name = "id") UUID id,
                                                                 @RequestParam(required = false, name = "page", defaultValue = "0") int page,
                                                                 @RequestParam(required = false, name = "size", defaultValue = "15") int size,
                                                                 @RequestParam(required = false, name = "sort", defaultValue = "nom") String sortBy,
                                                                 @RequestParam(required = false, name = "direction", defaultValue = "asc") String direction) {

        if (!Set.of("nom", "email").contains(sortBy.toLowerCase())) {
            sortBy = "nom"; // Au cas où le frontEnd saisie une autre value que prévue
        }

        if (!Set.of("asc", "desc").contains(direction.toLowerCase())) {
            direction = "asc"; // Au cas où le frontEnd saisie une autre value que prévue
        }

        Sort.Direction dir = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));

        return ResponseEntity.ok(utilisateurService.getAllUtilisateurBySocieteId(id, pageable));
    }

    @GetMapping("/{societeId}/utilisateurs/{utilisateurId}")
    public ResponseEntity<UtilisateurDto> getUtilisateurByIdAndSocieteId(@PathVariable(name = "utilisateurId") UUID utilisateurId,
                                                                         @PathVariable(name = "societeId") UUID societeId) {
        return ResponseEntity.ok(utilisateurService.getUtilisateurByIdAndSocieteId(utilisateurId, societeId));
    }

    @PutMapping("/{societeId}/utilisateurs/{utilisateurId}")
    public ResponseEntity<UtilisateurDto> modifyUtilisateurByIdAndSocieteId(@PathVariable(name = "utilisateurId") UUID utilisateurId,
                                                                            @PathVariable(name = "societeId") UUID societeId,
                                                                            @Valid @RequestBody UpdateUtilisateurRequest request) {
        return ResponseEntity.ok(utilisateurService.modifyUtilisateurByIdAndSocieteId(utilisateurId, societeId, request));
    }

    @DeleteMapping("/{societeId}/utilisateurs/{utilisateurId}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable(name = "utilisateurId") UUID utilisateurId,
                                                  @PathVariable(name = "societeId") UUID societeId) {
        utilisateurService.deleteUtilisateur(utilisateurId, societeId);

        return ResponseEntity.noContent().build();
    }
}

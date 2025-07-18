package com.kobbo.kobbo.controller;

import com.kobbo.kobbo.dto.profilUtilisateur.request.RegisterProfilUtilisateurRequest;
import com.kobbo.kobbo.dto.profilUtilisateur.response.ProfilUtilisateurDto;
import com.kobbo.kobbo.dto.societe.response.SocieteProfilUtilisateurDto;
import com.kobbo.kobbo.service.ProfilUtilisateurService;
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
public class ProfilUtilisateurController {
    private final ProfilUtilisateurService profilUtilisateurService;

    @PostMapping("/{id}/profils")
    public ResponseEntity<ProfilUtilisateurDto> createProfilUtilisateur(@PathVariable(name = "id") UUID societeId,
                                                                        @Valid @RequestBody RegisterProfilUtilisateurRequest request,
                                                                        UriComponentsBuilder uriComponentsBuilder) {

        ProfilUtilisateurDto profilUtilisateurDto = profilUtilisateurService.createProfilUtilisateur(request, societeId);

        URI location = uriComponentsBuilder.path("/societes/{id}/profiles/{id}")
                .buildAndExpand(profilUtilisateurDto.getSocieteDto().getId(), profilUtilisateurDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(profilUtilisateurDto);
    }

    @GetMapping("/{id}/profiles")
    public ResponseEntity<SocieteProfilUtilisateurDto> listNature(@PathVariable(name = "id") UUID societeId,
                                                                  @RequestParam(required = false, name = "page", defaultValue = "0") int page,
                                                                  @RequestParam(required = false, name = "size", defaultValue = "15") int size,
                                                                  @RequestParam(required = false, name = "sort", defaultValue = "libelle") String sortBy,
                                                                  @RequestParam(required = false, name = "direction", defaultValue = "asc") String direction) {

        if (!sortBy.equalsIgnoreCase("libelle")) {
            sortBy = "libelle"; // Au cas où le frontEnd saisie une autre value que prévue
        }

        if (!Set.of("asc", "desc").contains(direction.toLowerCase())) {
            direction = "asc"; // Au cas où le frontEnd saisie une autre value que prévue
        }

        Sort.Direction dir = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));

        return ResponseEntity.ok(profilUtilisateurService.getAllProfilUtilisateurBySocieteId(societeId, pageable));
    }

    @GetMapping("/{societeId}/profiles/{profileId}")
    public ResponseEntity<ProfilUtilisateurDto> getProfilUtilisateurByIdAndSocieteId(@PathVariable(name = "profileId") Long profileId,
                                                                                     @PathVariable(name = "societeId") UUID societeId) {
        return ResponseEntity.ok(profilUtilisateurService.getProfilUtilisateurByIdAndSocieteId(profileId, societeId));
    }

    @PutMapping("/{societeId}/profiles/{profileId}")
    public ResponseEntity<ProfilUtilisateurDto> modifyProfilUtilisateurByIdAndSocieteId(@PathVariable(name = "profileId") Long profileId,
                                                                                        @PathVariable(name = "societeId") UUID societeId,
                                                                                        @Valid @RequestBody RegisterProfilUtilisateurRequest request) {
        return ResponseEntity.ok(profilUtilisateurService.modifyProfilUtilisateurByIdAndSocieteId(profileId, societeId, request));
    }

    @DeleteMapping("/{societeId}/profiles/{profileId}")
    public ResponseEntity<Void> deleteProfilUtilisateur(@PathVariable(name = "profileId") Long profileId,
                                                        @PathVariable(name = "societeId") UUID societeId) {
        profilUtilisateurService.deleteProfilUtilisateur(profileId, societeId);

        return ResponseEntity.noContent().build();
    }
}

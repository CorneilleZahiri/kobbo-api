package com.kobbo.kobbo.controller;

import com.kobbo.kobbo.dto.utilisateur.request.RegisterUtilisateurRequest;
import com.kobbo.kobbo.dto.utilisateur.request.UpdateUtilisateurMotDePasse;
import com.kobbo.kobbo.dto.utilisateur.request.UpdateUtilisateurRequest;
import com.kobbo.kobbo.dto.utilisateur.response.UtilisateurDto;
import com.kobbo.kobbo.mapper.UtilisateurMapper;
import com.kobbo.kobbo.service.UtilisateurService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/utilisateurs")
@AllArgsConstructor
public class UtilisateurController {
    private final UtilisateurService utilisateurService;
    private final UtilisateurMapper utilisateurMapper;

    @PostMapping
    public ResponseEntity<UtilisateurDto> createUtilisateur(@Valid @RequestBody RegisterUtilisateurRequest request,
                                                            UriComponentsBuilder uriComponentsBuilder) {

        UtilisateurDto utilisateurDto = utilisateurService.createUtilisateur(request);

        URI location = uriComponentsBuilder.path("/utilisateurs/{id}")
                .buildAndExpand(utilisateurDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(utilisateurDto);
    }

    @GetMapping
    public ResponseEntity<List<UtilisateurDto>> listUtilisateur() {
        return ResponseEntity.ok().body(utilisateurService.listUtilisateur());
    }

    @GetMapping("/{utilisateurId}")
    public ResponseEntity<UtilisateurDto> getUtilisateurByIdAndSocieteId(@PathVariable(name = "utilisateurId") UUID utilisateurId) {
        return ResponseEntity.ok(utilisateurMapper.toDto(
                utilisateurService.getUtilisateurById(utilisateurId)));
    }

    @PutMapping("/{utilisateurId}")
    public ResponseEntity<UtilisateurDto> modifyUtilisateurById(@PathVariable(name = "utilisateurId") UUID utilisateurId,
                                                                @Valid @RequestBody UpdateUtilisateurRequest request) {
        return ResponseEntity.ok(utilisateurService.updateUtilisateurById(utilisateurId, request));
    }

    @DeleteMapping("/{utilisateurId}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable(name = "utilisateurId") UUID utilisateurId) {
        utilisateurService.deleteUtilisateur(utilisateurId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("{id}/change-password")
    public ResponseEntity<Void> updateMotDePasse(@PathVariable UUID id,
                                                 @RequestBody UpdateUtilisateurMotDePasse updateUtilisateurMotDePasse) {
        utilisateurService.updateMotDePasse(id, updateUtilisateurMotDePasse);

        return ResponseEntity.noContent().build();
    }
}

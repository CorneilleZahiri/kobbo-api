package com.kobbo.kobbo.controller;

import com.kobbo.kobbo.dto.utilisateur.request.RegisterUtilisateurRequest;
import com.kobbo.kobbo.dto.utilisateur.request.UpdateUtilisateurRequest;
import com.kobbo.kobbo.dto.utilisateur.response.UtilisateurDto;
import com.kobbo.kobbo.service.RoleService;
import com.kobbo.kobbo.service.UtilisateurService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/utilisateurs")
@AllArgsConstructor
public class UtilisateurController {
    private final UtilisateurService utilisateurService;
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<UtilisateurDto> createUtilisateur(@Valid @RequestBody RegisterUtilisateurRequest request,
                                                            UriComponentsBuilder uriComponentsBuilder) {

        UtilisateurDto utilisateurDto = utilisateurService.createUtilisateur(request);

        URI location = uriComponentsBuilder.path("/utilisateurs/{id}")
                .buildAndExpand(utilisateurDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(utilisateurDto);
    }
    
    @GetMapping("/{utilisateurId}")
    public ResponseEntity<UtilisateurDto> getUtilisateurByIdAndSocieteId(@PathVariable(name = "utilisateurId") UUID utilisateurId) {
        return ResponseEntity.ok(utilisateurService.getUtilisateurById(utilisateurId));
    }

    @PutMapping("/{utilisateurId}")
    public ResponseEntity<UtilisateurDto> modifyUtilisateurById(@PathVariable(name = "utilisateurId") UUID utilisateurId,
                                                                @Valid @RequestBody UpdateUtilisateurRequest request) {
        return ResponseEntity.ok(utilisateurService.modifyUtilisateurById(utilisateurId, request));
    }

    @DeleteMapping("/{utilisateurId}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable(name = "utilisateurId") UUID utilisateurId) {
        utilisateurService.deleteUtilisateur(utilisateurId);

        return ResponseEntity.noContent().build();
    }
}

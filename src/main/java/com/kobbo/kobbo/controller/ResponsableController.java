package com.kobbo.kobbo.controller;

import com.kobbo.kobbo.dto.responsable.request.RegisterResponsableRequest;
import com.kobbo.kobbo.dto.responsable.response.ResponsableDto;
import com.kobbo.kobbo.dto.societe.response.SocieteResponsableDto;
import com.kobbo.kobbo.service.ResponsableService;
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
public class ResponsableController {
    private final ResponsableService responsableService;

    @PostMapping("/{id}/responsables")
    public ResponseEntity<ResponsableDto> createResponsable(@PathVariable(name = "id") UUID SocieteId,
                                                            @Valid @RequestBody RegisterResponsableRequest request,
                                                            UriComponentsBuilder uriComponentsBuilder) {
        ResponsableDto responsableDto = responsableService.createResponsable(request, SocieteId);

        URI location = uriComponentsBuilder.path("/societes/{SocieteId}/responsables/{responsableId}")
                .buildAndExpand(responsableDto.getSocieteDto().getId(), responsableDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(responsableDto);
    }

    @GetMapping("/{id}/responsables")
    public ResponseEntity<SocieteResponsableDto> listResponsable(@PathVariable(name = "id") UUID id,
                                                                 @RequestParam(required = false, name = "page", defaultValue = "0") int page,
                                                                 @RequestParam(required = false, name = "size", defaultValue = "15") int size,
                                                                 @RequestParam(required = false, name = "sort", defaultValue = "nom") String sortBy,
                                                                 @RequestParam(required = false, name = "direction", defaultValue = "asc") String direction) {

        if (!Set.of("nom", "fonction").contains(sortBy.toLowerCase())) {
            sortBy = "nom"; // Au cas où le frontEnd saisie une autre value que prévue
        }

        if (!Set.of("asc", "desc").contains(direction.toLowerCase())) {
            direction = "asc"; // Au cas où le frontEnd saisie une autre value que prévue
        }

        Sort.Direction dir = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));

        return ResponseEntity.ok(responsableService.getAllResponsableBySocieteId(id, pageable));
    }

    @GetMapping("/{societeId}/responsables/{responsableId}")
    public ResponseEntity<ResponsableDto> getResponsableByIdAndSocieteId(@PathVariable(name = "responsableId") UUID responsableId,
                                                                         @PathVariable(name = "societeId") UUID societeId) {
        return ResponseEntity.ok(responsableService.getResponsableByIdAndSocieteId(responsableId, societeId));
    }

    @PutMapping("/{societeId}/responsables/{responsableId}")
    public ResponseEntity<ResponsableDto> modifyNatureByIdAndSocieteId(@PathVariable(name = "responsableId") UUID responsableId,
                                                                       @PathVariable(name = "societeId") UUID societeId,
                                                                       @Valid @RequestBody RegisterResponsableRequest request) {
        return ResponseEntity.ok(responsableService.modifyResponsableByIdAndSocieteId(responsableId, societeId, request));
    }

    @DeleteMapping("/{societeId}/responsables/{responsableId}")
    public ResponseEntity<Void> deleteNature(@PathVariable(name = "responsableId") UUID responsableId,
                                             @PathVariable(name = "societeId") UUID societeId) {
        responsableService.deleteResponsable(responsableId, societeId);

        return ResponseEntity.noContent().build();
    }
}

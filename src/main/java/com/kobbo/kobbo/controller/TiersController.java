package com.kobbo.kobbo.controller;

import com.kobbo.kobbo.dto.societe.response.SocieteTiersDto;
import com.kobbo.kobbo.dto.tiers.request.RegisterTiersRequest;
import com.kobbo.kobbo.dto.tiers.response.TiersDto;
import com.kobbo.kobbo.service.TiersService;
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
public class TiersController {
    private final TiersService tiersService;

    @PostMapping("/{id}/tiers")
    public ResponseEntity<TiersDto> createTiers(@PathVariable(name = "id") UUID SocieteId,
                                                @Valid @RequestBody RegisterTiersRequest request,
                                                UriComponentsBuilder uriComponentsBuilder) {
        TiersDto tiersDto = tiersService.createTiers(request, SocieteId);

        URI location = uriComponentsBuilder.path("/societes/{SocieteId}/tiers/{tiersId}")
                .buildAndExpand(tiersDto.getSocieteDto().getId(), tiersDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(tiersDto);
    }

    @GetMapping("/{id}/tiers")
    public ResponseEntity<SocieteTiersDto> listTiers(@PathVariable(name = "id") UUID id,
                                                     @RequestParam(required = false, name = "page", defaultValue = "0") int page,
                                                     @RequestParam(required = false, name = "size", defaultValue = "15") int size,
                                                     @RequestParam(required = false, name = "sort", defaultValue = "nom") String sortBy,
                                                     @RequestParam(required = false, name = "direction", defaultValue = "asc") String direction) {

        if (!Set.of("nom", "nature", "contact").contains(sortBy.toLowerCase())) {
            sortBy = "nom"; // Au cas où le frontEnd saisie une autre value que prévue
        }

        if (!Set.of("asc", "desc").contains(direction.toLowerCase())) {
            direction = "asc"; // Au cas où le frontEnd saisie une autre value que prévue
        }

        Sort.Direction dir = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));

        return ResponseEntity.ok(tiersService.getAllTiersBySocieteId(id, pageable));
    }

    @GetMapping("/{societeId}/tiers/{tiersId}")
    public ResponseEntity<TiersDto> getTiersByIdAndSocieteId(@PathVariable(name = "tiersId") UUID tiersId,
                                                             @PathVariable(name = "societeId") UUID societeId) {
        return ResponseEntity.ok(tiersService.getTiersByIdAndSocieteId(tiersId, societeId));
    }

    @PutMapping("/{societeId}/tiers/{tiersId}")
    public ResponseEntity<TiersDto> modifyTiersByIdAndSocieteId(@PathVariable(name = "tiersId") UUID tiersId,
                                                                @PathVariable(name = "societeId") UUID societeId,
                                                                @Valid @RequestBody RegisterTiersRequest request) {
        return ResponseEntity.ok(tiersService.modifyTiersByIdAndSocieteId(tiersId, societeId, request));
    }

    @DeleteMapping("/{societeId}/tiers/{tiersId}")
    public ResponseEntity<Void> deleteTiers(@PathVariable(name = "tiersId") UUID tiersId,
                                            @PathVariable(name = "societeId") UUID societeId) {
        tiersService.deleteTiers(tiersId, societeId);

        return ResponseEntity.noContent().build();
    }
}

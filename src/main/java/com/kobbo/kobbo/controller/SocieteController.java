package com.kobbo.kobbo.controller;

import com.kobbo.kobbo.dto.societe.request.RegisterSocieteRequest;
import com.kobbo.kobbo.dto.societe.response.SocieteDto;
import com.kobbo.kobbo.entity.Societe;
import com.kobbo.kobbo.mapper.SocieteMapper;
import com.kobbo.kobbo.service.SocieteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
public class SocieteController {
    private final SocieteService societeService;
    private final SocieteMapper societeMapper;

    @PostMapping
    public ResponseEntity<SocieteDto> createSociete(@Valid @RequestBody RegisterSocieteRequest request,
                                                    UriComponentsBuilder uriComponentsBuilder) {
        SocieteDto societeDto = societeService.createSociete(request);

        //Générer le lien (location)
        URI location = uriComponentsBuilder
                .path("/societes/{id}")
                .buildAndExpand(societeDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(societeDto);
    }

    @GetMapping
    public ResponseEntity<Page<SocieteDto>> listSociete(@RequestParam(required = false, name = "page", defaultValue = "0") int page,
                                                        @RequestParam(required = false, name = "size", defaultValue = "15") int size,
                                                        @RequestParam(required = false, name = "sort", defaultValue = "raisonSociale") String sortBy,
                                                        @RequestParam(required = false, name = "direction", defaultValue = "asc") String direction) {

        if (!Set.of("raisonSociale", "email").contains(sortBy.toLowerCase())) {
            sortBy = "raisonSociale"; // Au cas où le frontEnd saisie une autre value que prévue
        }

        if (!Set.of("asc", "desc").contains(direction.toLowerCase())) {
            direction = "asc"; // Au cas où le frontEnd saisie une autre value que prévue
        }

        Sort.Direction dir = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));

        return ResponseEntity.ok(societeService.listSociete(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocieteDto> getSocieteById(@PathVariable UUID id) {
        Societe societe = societeService.getSocieteById(id);

        return ResponseEntity.ok(societeMapper.toDto(societe));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SocieteDto> updateSociete(@PathVariable UUID id,
                                                    @Valid @RequestBody RegisterSocieteRequest request) {
        Societe societe = societeService.updateSociete(id, request);

        return ResponseEntity.ok(societeMapper.toDto(societe));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSociete(@PathVariable(name = "id") UUID id) {
        societeService.deleteSociete(id);

        return ResponseEntity.noContent().build();
    }
}

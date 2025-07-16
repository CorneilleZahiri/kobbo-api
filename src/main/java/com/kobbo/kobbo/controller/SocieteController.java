package com.kobbo.kobbo.controller;

import com.kobbo.kobbo.dto.societe.RegisterSocieteRequest;
import com.kobbo.kobbo.dto.societe.SocieteDto;
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
    public ResponseEntity<SocieteDto> creerSociete(@Valid @RequestBody RegisterSocieteRequest request,
                                                   UriComponentsBuilder uriComponentsBuilder) {
        //Convertir le request en entité
        SocieteDto societeDto = societeService.creerSociete(societeMapper.toEntity(request));


        //Générer le lien (location)
        URI location = uriComponentsBuilder
                .path("/societes/{id}")
                .buildAndExpand(societeDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(societeDto);
    }

    @GetMapping
    public ResponseEntity<Page<SocieteDto>> listeSociete(@RequestParam(required = false, name = "page", defaultValue = "0") int page,
                                                         @RequestParam(required = false, name = "size", defaultValue = "2") int size,
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

        return ResponseEntity.ok(societeService.listeSociete(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocieteDto> getSocieteById(@PathVariable UUID id) {
        return ResponseEntity.ok(societeService.getSocieteById(id));
    }
}

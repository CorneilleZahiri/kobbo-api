package com.kobbo.kobbo.controller;

import com.kobbo.kobbo.dto.nature.request.RegisterNatureRequest;
import com.kobbo.kobbo.dto.nature.response.NatureDto;
import com.kobbo.kobbo.dto.societe.response.SocieteNatureDto;
import com.kobbo.kobbo.mapper.NatureMapper;
import com.kobbo.kobbo.service.NatureService;
import com.kobbo.kobbo.service.SocieteService;
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
public class NatureController {
    private final NatureService natureService;
    private final SocieteService societeService;
    private final NatureMapper natureMapper;

    @PostMapping("/{id}/natures")
    public ResponseEntity<NatureDto> createNature(@PathVariable(name = "id") UUID id,
                                                  @Valid @RequestBody RegisterNatureRequest request,
                                                  UriComponentsBuilder uriComponentsBuilder) {

        NatureDto natureDto = natureService.createNature(request, id);

        URI location = uriComponentsBuilder.path("/societes/{id}/natures/{id}")
                .buildAndExpand(natureDto.getSocieteDto().getId(), natureDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(natureDto);

    }

    @GetMapping("/{id}/natures")
    public ResponseEntity<SocieteNatureDto> listNature(@PathVariable(name = "id") UUID id,
                                                       @RequestParam(required = false, name = "page", defaultValue = "0") int page,
                                                       @RequestParam(required = false, name = "size", defaultValue = "15") int size,
                                                       @RequestParam(required = false, name = "sort", defaultValue = "intitule") String sortBy,
                                                       @RequestParam(required = false, name = "direction", defaultValue = "asc") String direction) {

        if (!sortBy.equalsIgnoreCase("intitule")) {
            sortBy = "intitule"; // Au cas où le frontEnd saisie une autre value que prévue
        }

        if (!Set.of("asc", "desc").contains(direction.toLowerCase())) {
            direction = "asc"; // Au cas où le frontEnd saisie une autre value que prévue
        }

        Sort.Direction dir = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));

        return ResponseEntity.ok(natureService.getAllNatureBySocieteId(id, pageable));
    }

    @GetMapping("/{societeId}/natures/{natureId}")
    public ResponseEntity<NatureDto> getNatureByIdAndSocieteId(@PathVariable(name = "natureId") Long natureId,
                                                               @PathVariable(name = "societeId") UUID societeId) {
        return ResponseEntity.ok(natureService.getNatureByIdAndSocieteId(natureId, societeId));
    }


}

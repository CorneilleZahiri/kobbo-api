package com.kobbo.kobbo.controller;

import com.kobbo.kobbo.dto.nature.NatureDto;
import com.kobbo.kobbo.dto.nature.RegisterNatureRequest;
import com.kobbo.kobbo.mapper.NatureMapper;
import com.kobbo.kobbo.service.NatureService;
import com.kobbo.kobbo.service.SocieteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/societes")
@AllArgsConstructor
public class NatureController {
    private final NatureService natureService;
    private final SocieteService societeService;
    private final NatureMapper natureMapper;

    @PostMapping("/{id}/natures")
    public ResponseEntity<NatureDto> creerNature(@PathVariable(name = "id") UUID id,
                                                 @Valid @RequestBody RegisterNatureRequest request,
                                                 UriComponentsBuilder uriComponentsBuilder) {

        NatureDto natureDto = natureService.createNature(request, id);

        URI location = uriComponentsBuilder.path("/societes/{id}")
                .buildAndExpand(natureDto.getSocieteDto().getId())
                .toUri();

        return ResponseEntity.created(location).body(natureDto);

    }

}

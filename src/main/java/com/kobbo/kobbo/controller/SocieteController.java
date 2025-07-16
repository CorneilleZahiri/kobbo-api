package com.kobbo.kobbo.controller;

import com.kobbo.kobbo.dto.societe.RegisterSocieteRequest;
import com.kobbo.kobbo.dto.societe.SocieteDto;
import com.kobbo.kobbo.mapper.SocieteMapper;
import com.kobbo.kobbo.service.SocieteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/societes")
@AllArgsConstructor
public class SocieteController {
    private final SocieteService societeService;
    private final SocieteMapper societeMapper;

    @GetMapping
    public List<SocieteDto> listeSociete() {
        return societeService.listeSociete();
    }

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
}

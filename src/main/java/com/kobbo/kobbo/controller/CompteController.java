package com.kobbo.kobbo.controller;

import com.kobbo.kobbo.dto.comptes.response.CompteDto;
import com.kobbo.kobbo.dto.societe.request.RegisterSocieteRequest;
import com.kobbo.kobbo.service.CompteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/comptes")
@AllArgsConstructor
public class CompteController {
    private final CompteService compteService;

    @PostMapping
    public ResponseEntity<CompteDto> createCompte(@Valid @RequestBody RegisterSocieteRequest request,
                                                  UriComponentsBuilder uriComponentsBuilder) {
        CompteDto compteDto = compteService.createCompte(request);

        URI location = uriComponentsBuilder.path("/comptes/{id}")
                .buildAndExpand(compteDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(compteDto);
    }
}

package com.kobbo.kobbo.controller;

import com.kobbo.kobbo.dto.nature.NatureDto;
import com.kobbo.kobbo.dto.nature.RegisterNatureRequest;
import com.kobbo.kobbo.entity.Nature;
import com.kobbo.kobbo.mapper.NatureMapper;
import com.kobbo.kobbo.service.NatureService;
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
@RequestMapping("/natures")
@AllArgsConstructor
public class NatureController {
    private final NatureService natureService;
    private final NatureMapper natureMapper;

    @PostMapping
    public ResponseEntity<NatureDto> creerNature(@Valid @RequestBody RegisterNatureRequest natureRequest,
                                                 UriComponentsBuilder uriComponentsBuilder) {

        Nature nature = natureMapper.toEntity(natureRequest);
        NatureDto natureDto = natureService.creerNature(nature);

        URI location = uriComponentsBuilder.path("/natures/{id}")
                .buildAndExpand(natureDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(natureDto);

    }

}

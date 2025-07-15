package com.kobbo.kobbo.service;

import com.kobbo.kobbo.dto.nature.NatureDto;
import com.kobbo.kobbo.entity.Nature;
import com.kobbo.kobbo.mapper.NatureMapper;
import com.kobbo.kobbo.repository.NatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NatureService {
    private final NatureRepository natureRepository;
    private final NatureMapper natureMapper;

    public NatureDto creerNature(Nature nature) {
        Nature natureSaved = natureRepository.save(nature);
        return natureMapper.toDto(natureSaved);
    }
}

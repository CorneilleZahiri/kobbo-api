package com.kobbo.kobbo.dto.nature.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class NatureResponse {
    private Long id;
    private String intitule;
}
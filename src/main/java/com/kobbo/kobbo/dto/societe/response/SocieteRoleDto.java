package com.kobbo.kobbo.dto.societe.response;

import com.kobbo.kobbo.dto.role.response.RoleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@Builder
public class SocieteRoleDto {
    private SocieteDto societeDto;
    private Page<RoleResponse> roleResponsePage;
}

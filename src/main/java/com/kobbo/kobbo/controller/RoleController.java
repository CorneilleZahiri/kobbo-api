package com.kobbo.kobbo.controller;

import com.kobbo.kobbo.dto.role.request.RegisterRoleRequest;
import com.kobbo.kobbo.dto.role.response.RoleDto;
import com.kobbo.kobbo.dto.societe.response.SocieteRoleDto;
import com.kobbo.kobbo.entity.Role;
import com.kobbo.kobbo.mapper.RoleMapper;
import com.kobbo.kobbo.service.RoleService;
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
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @PostMapping("/{id}/roles")
    public ResponseEntity<RoleDto> createRole(@PathVariable(name = "id") UUID societeId,
                                              @Valid @RequestBody RegisterRoleRequest request,
                                              UriComponentsBuilder uriComponentsBuilder) {

        RoleDto roleDto = roleService.createRole(request, societeId);

        URI location = uriComponentsBuilder.path("/societes/{id}/roles/{id}")
                .buildAndExpand(roleDto.getSocieteDto().getId(), roleDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(roleDto);
    }

    @GetMapping("/{id}/roles")
    public ResponseEntity<SocieteRoleDto> listRole(@PathVariable(name = "id") UUID societeId,
                                                   @RequestParam(required = false, name = "page", defaultValue = "0") int page,
                                                   @RequestParam(required = false, name = "size", defaultValue = "15") int size,
                                                   @RequestParam(required = false, name = "sort", defaultValue = "libelle") String sortBy,
                                                   @RequestParam(required = false, name = "direction", defaultValue = "asc") String direction) {

        if (!sortBy.equalsIgnoreCase("libelle")) {
            sortBy = "libelle"; // Au cas où le frontEnd saisie une autre value que prévue
        }

        if (!Set.of("asc", "desc").contains(direction.toLowerCase())) {
            direction = "asc"; // Au cas où le frontEnd saisie une autre value que prévue
        }

        Sort.Direction dir = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));

        return ResponseEntity.ok(roleService.getAllRoleBySocieteId(societeId, pageable));
    }

    @GetMapping("/{societeId}/roles/{roleId}")
    public ResponseEntity<RoleDto> getRoleByIdAndSocieteId(@PathVariable(name = "roleId") UUID roleId,
                                                           @PathVariable(name = "societeId") UUID societeId) {
        Role role = roleService.getRoleByIdAndSocieteId(roleId, societeId);
        return ResponseEntity.ok(roleMapper.toDto(role));
    }

    @PutMapping("/{societeId}/roles/{roleId}")
    public ResponseEntity<RoleDto> modifyRoleByIdAndSocieteId(@PathVariable(name = "roleId") UUID roleId,
                                                              @PathVariable(name = "societeId") UUID societeId,
                                                              @Valid @RequestBody RegisterRoleRequest request) {
        return ResponseEntity.ok(roleService.modifyRoleByIdAndSocieteId(roleId, societeId, request));
    }

    @DeleteMapping("/{societeId}/roles/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable(name = "roleId") UUID roleId,
                                           @PathVariable(name = "societeId") UUID societeId) {
        roleService.deleteRole(roleId, societeId);

        return ResponseEntity.noContent().build();
    }
}

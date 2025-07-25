package com.kobbo.kobbo.service;

import com.kobbo.kobbo.dto.role.request.RegisterRoleRequest;
import com.kobbo.kobbo.dto.role.response.RoleDto;
import com.kobbo.kobbo.dto.role.response.RoleResponse;
import com.kobbo.kobbo.dto.societe.response.SocieteDto;
import com.kobbo.kobbo.dto.societe.response.SocieteRoleDto;
import com.kobbo.kobbo.entity.Role;
import com.kobbo.kobbo.entity.Societe;
import com.kobbo.kobbo.exception.DuplicateEntryException;
import com.kobbo.kobbo.exception.EntityNotFoundException;
import com.kobbo.kobbo.mapper.RoleMapper;
import com.kobbo.kobbo.mapper.SocieteMapper;
import com.kobbo.kobbo.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final SocieteMapper societeMapper;
    private final SocieteService societeService;

    @Transactional
    public RoleDto createRole(RegisterRoleRequest request, UUID societeId) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        //Contrôler le doublon sur l'intitulé
        if (getRoleByLibelleAndSocieteId(request.getLibelle(), societe.getId()) != null) {
            throw new DuplicateEntryException("Le rôle " + request.getLibelle(),
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        Role role = roleMapper.toEntity(request);
        role.setSociete(societe);

        return roleMapper.toDto(roleRepository.save(role));
    }

    @Transactional
    public Role getRoleByLibelleAndSocieteId(String intitule, UUID id) {
        return roleRepository.findByLibelleAndSocieteId(intitule, id).orElse(null);
    }

    @Transactional
    public SocieteRoleDto getAllRoleBySocieteId(UUID societeId, Pageable pageable) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        SocieteDto societeDto = societeMapper.toDto(societe);

        Page<RoleResponse> roleResponsePage = roleRepository
                .findBySocieteId(societeId, pageable)
                .map(roleMapper::toRoleResponse);

        return new SocieteRoleDto(societeDto, roleResponsePage);
    }

    @Transactional
    public Role getRoleByIdAndSocieteId(Long roleId, UUID societeId) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        Role role = roleRepository.findByIdAndSocieteId(roleId, societeId).orElse(null);
        if (role == null) {
            throw new EntityNotFoundException("Ce rôle ",
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        return role;
    }

    @Transactional
    public RoleDto modifyRoleByIdAndSocieteId(Long roleId, UUID societeId, RegisterRoleRequest request) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        //Vérifier que la nature existe
        Role role = roleRepository.findByIdAndSocieteId(roleId, societeId).orElse(null);
        if (role == null) {
            throw new EntityNotFoundException("Ce rôle ",
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        //Contrôler le doublon sur l'intitulé dans cette société
        Role roleDuplicated = getRoleByLibelleAndSocieteId(request.getLibelle(), societe.getId());

        if (roleDuplicated != null && !roleDuplicated.getId().equals(roleId)) {
            throw new DuplicateEntryException("Le rôle " + request.getLibelle(),
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        roleMapper.update(request, role);

        return roleMapper.toDto(roleRepository.save(role));
    }

    @Transactional
    public void deleteRole(Long roleId, UUID societeId) {
        //Vérifier l'existence de Société
        Societe societe = societeService.getSocieteById(societeId);

        //Vérifier que la nature existe
        Role role = roleRepository.findByIdAndSocieteId(roleId, societeId).orElse(null);
        if (role == null) {
            throw new EntityNotFoundException("Ce rôle ",
                    societeId.toString() + " (" + societe.getRaisonSociale() + ")");
        }

        roleRepository.deleteById(role.getId());
    }
}

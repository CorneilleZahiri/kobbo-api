package com.kobbo.kobbo.repository;

import com.kobbo.kobbo.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByIdAndSocieteId(Long roleId, UUID societeId);

    Optional<Role> findByLibelleAndSocieteId(String intitule, UUID societeId);

    Page<Role> findBySocieteId(UUID societeId, Pageable pageable);
}

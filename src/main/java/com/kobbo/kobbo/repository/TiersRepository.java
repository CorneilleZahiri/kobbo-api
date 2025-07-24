package com.kobbo.kobbo.repository;

import com.kobbo.kobbo.entity.Tiers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TiersRepository extends JpaRepository<Tiers, UUID> {
    Page<Tiers> findBySocieteId(UUID SocieteId, Pageable pageable);

    Optional<Tiers> findByIdAndSocieteId(UUID tiersId, UUID societeId);
}

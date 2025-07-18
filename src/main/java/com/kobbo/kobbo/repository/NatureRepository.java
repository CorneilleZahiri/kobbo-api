package com.kobbo.kobbo.repository;

import com.kobbo.kobbo.entity.Nature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NatureRepository extends JpaRepository<Nature, Long> {
    Optional<Nature> findByIdAndSocieteId(Long natureId, UUID societeId);

    Optional<Nature> findByIntituleAndSocieteId(String intitule, UUID id);

    Page<Nature> findBySocieteId(UUID id, Pageable pageable);
}

package com.kobbo.kobbo.repository;

import com.kobbo.kobbo.entity.Responsable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ResponsableRepository extends JpaRepository<Responsable, UUID> {
    Page<Responsable> findBySocieteId(UUID SocieteId, Pageable pageable);

    Optional<Responsable> findByIdAndSocieteId(UUID responsableId, UUID societeId);
}

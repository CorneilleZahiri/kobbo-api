package com.kobbo.kobbo.repository;

import com.kobbo.kobbo.entity.Nature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NatureRepository extends JpaRepository<Nature, UUID> {
//    Optional<Nature> findById(UUID natureId);
//
//    Optional<Nature> findByIntituleAndSocieteId(String intitule, UUID id);
//
//    Page<Nature> findBySocieteId(UUID id, Pageable pageable);
}

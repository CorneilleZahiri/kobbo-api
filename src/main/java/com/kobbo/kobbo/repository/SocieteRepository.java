package com.kobbo.kobbo.repository;

import com.kobbo.kobbo.entity.Societe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SocieteRepository extends JpaRepository<Societe, UUID> {
    Optional<Societe> findByEmail(String email);
}

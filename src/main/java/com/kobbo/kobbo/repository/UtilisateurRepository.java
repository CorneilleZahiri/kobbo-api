package com.kobbo.kobbo.repository;

import com.kobbo.kobbo.entity.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);

    @Query("SELECT u FROM Utilisateur u WHERE u.permission.role.societe.id = :societeId")
    Page<Utilisateur> findBySocieteId(@Param("societeId") UUID id, Pageable pageable);

    Optional<Utilisateur> findById(UUID id);

    void deleteById(UUID id);
}

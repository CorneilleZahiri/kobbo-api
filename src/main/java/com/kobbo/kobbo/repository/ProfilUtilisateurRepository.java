package com.kobbo.kobbo.repository;

import com.kobbo.kobbo.entity.ProfilUtilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfilUtilisateurRepository extends JpaRepository<ProfilUtilisateur, Long> {
    Optional<ProfilUtilisateur> findByIdAndSocieteId(Long profilUtilisateurId, UUID societeId);

    Optional<ProfilUtilisateur> findByLibelleAndSocieteId(String intitule, UUID societeId);

    Page<ProfilUtilisateur> findBySocieteId(UUID societeId, Pageable pageable);
}

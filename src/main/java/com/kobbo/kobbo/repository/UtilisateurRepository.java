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
    @Query("select u from Utilisateur u  WHERE u.id = :utilisateurId AND u.profilUtilisateur.societe.id = :societeId")
    Optional<Utilisateur> findByIdAndSocieteId(@Param("utilisateurId") UUID utilisateurId, @Param("societeId") UUID societeId);

    @Query("SELECT u FROM Utilisateur u WHERE u.email = :email AND u.profilUtilisateur.societe.id = :societeId")
    Optional<Utilisateur> findByEmailAndSocieteId(@Param("email") String email, @Param("societeId") UUID societeId);

    @Query("SELECT u FROM Utilisateur u WHERE u.profilUtilisateur.societe.id = :societeId")
    Page<Utilisateur> findBySocieteId(UUID id, Pageable pageable);
}

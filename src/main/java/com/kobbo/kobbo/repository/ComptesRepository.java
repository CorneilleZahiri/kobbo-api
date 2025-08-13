package com.kobbo.kobbo.repository;

import com.kobbo.kobbo.entity.Compte;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComptesRepository extends JpaRepository<Compte, UUID> {
    Optional<Compte> findByUtilisateurIdAndRoleId(UUID utilisateurId, UUID roleId);

    @EntityGraph(attributePaths = {"role", "role.societe"})
    @Query("select c from Compte c")
    List<Compte> findByUtilisateurId(UUID utilisateurId);
}

package com.kobbo.kobbo.repository;

import com.kobbo.kobbo.entity.Nature;
import com.kobbo.kobbo.entity.Societe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NatureRepository extends JpaRepository<Nature, Long> {
    Optional<Nature> findByIntituleAndSociete(String intitule, Societe societe);
}

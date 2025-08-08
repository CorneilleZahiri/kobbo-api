package com.kobbo.kobbo.repository;

import com.kobbo.kobbo.entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ComptesRepository extends JpaRepository<Compte, UUID> {
}

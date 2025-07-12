package com.kobbo.kobbo.repository;

import com.kobbo.kobbo.entity.Nature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NatureRepository extends JpaRepository<Nature, Long> {
}

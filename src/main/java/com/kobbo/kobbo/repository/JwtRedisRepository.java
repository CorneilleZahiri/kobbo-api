package com.kobbo.kobbo.repository;

import com.kobbo.kobbo.entity.JwtRedis;
import org.springframework.data.repository.CrudRepository;

public interface JwtRedisRepository extends CrudRepository<JwtRedis, String> {
}

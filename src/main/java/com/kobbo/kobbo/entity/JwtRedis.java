package com.kobbo.kobbo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@RedisHash(value = "JwtRedis")
public class JwtRedis implements Serializable {
    //L'id représente le jti
    private String id;
}

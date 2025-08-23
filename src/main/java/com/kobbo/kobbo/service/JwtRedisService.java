package com.kobbo.kobbo.service;

import com.kobbo.kobbo.config.AuthUtils;
import com.kobbo.kobbo.config.JwtConfig;
import com.kobbo.kobbo.entity.JwtRedis;
import com.kobbo.kobbo.exception.InvalideArgumentException;
import com.kobbo.kobbo.repository.JwtRedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@AllArgsConstructor
public class JwtRedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtRedisRepository jwtRedisRepository;
    private final AuthUtils authUtils;
    private final JwtConfig jwtConfig;

    public void verifyIfTokenIsUsedToSelect() {
        // Vérifier si le token a déjà été utilisé pour sélectionner un compte société
        String jti = authUtils.getCurrentUser().getJti().toString();
        if (jti != null) {
            if (jwtRedisRepository.existsById(jti)) {
                throw new InvalideArgumentException("Le jeton est invalide ou a déjà été utilisé " +
                        "pour sélectionner un compte société.");
            } else {
                // Stocker le jeton en mémoire et programmer sa suppression automatique
                saveTokenWithDynamicTTL(jti, jwtConfig.getAccessTokenExpiration());
            }
        }
    }

    private void saveTokenWithDynamicTTL(String jti, long ttlSeconds) {
        // Sauvegarder l’entité via repository
        jwtRedisRepository.save(new JwtRedis(jti));

        // Définir le TTL dynamiquement
        redisTemplate.expire(jti, Duration.ofSeconds(ttlSeconds));
    }


    // Pour éviter une éventuelle course condition (par ex. deux requêtes qui passent quasi simultanément
    // et contournent la vérification avant la sauvegarde), tu pourrais utiliser une seule commande Redis atomique
    // (SET key value NX EX ttl) au lieu de faire save puis expire.
    /***
     private void saveTokenWithDynamicTTL(String jti, long ttlSeconds) {
     Boolean success = redisTemplate.opsForValue()
     .setIfAbsent(jti, "USED", Duration.ofSeconds(ttlSeconds));

     if (Boolean.FALSE.equals(success)) {
     throw new InvalideArgumentException("Le jeton a déjà été utilisé.");
     }
     }
     ***/
}

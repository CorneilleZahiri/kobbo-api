package com.kobbo.kobbo.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, Object identifiant) {
        super(String.format("%s n'a pas été trouvé(e) avec l'identifiant %s", entityName, identifiant));
    }
}

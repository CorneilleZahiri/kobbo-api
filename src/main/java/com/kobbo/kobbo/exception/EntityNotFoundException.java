package com.kobbo.kobbo.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, Object identifiant) {
        super(String.format("%s avec l'identifiant %s n'a pas été trouvé", entityName, identifiant));
    }
}

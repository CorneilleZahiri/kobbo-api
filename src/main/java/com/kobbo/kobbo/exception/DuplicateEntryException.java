package com.kobbo.kobbo.exception;

public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException(String entityName, Object identifiant) {
        super(String.format("%s existe déjà avec l'identifiant %s", entityName, identifiant));
    }
}

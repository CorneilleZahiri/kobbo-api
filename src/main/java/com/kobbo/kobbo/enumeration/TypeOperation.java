package com.kobbo.kobbo.enumeration;

import java.util.Arrays;
import java.util.List;

public enum TypeOperation {
    DEPENSE,
    RECETTE;

    public static List<String> listTypeOperation() {
        return Arrays.stream(TypeOperation.values())
                .map(typeOperation -> typeOperation.name().toUpperCase()).toList();
    }
}

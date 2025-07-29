package com.kobbo.kobbo.enumeration;

import java.util.Arrays;
import java.util.List;

public enum Statut {
    PROVISOIRE,
    VALIDER,
    ANNULER,
    SUPPRIMER;

    public static List<String> listStatut() {
        return Arrays.stream(Statut.values()).map(Enum::name).toList();
    }
}

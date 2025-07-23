package com.kobbo.kobbo.entity;

import com.kobbo.kobbo.enumeration.Statut;
import com.kobbo.kobbo.enumeration.TypeOperation;

import java.time.LocalDate;
import java.util.UUID;

public class Operation {
    private UUID id;
    private LocalDate dateOperation;
    private Statut statut;
    private TypeOperation typeOperation;
    private String libelle;
    private Long montant;
    private String modeDePayement;
    private String referenceModeDePayement;
    

}

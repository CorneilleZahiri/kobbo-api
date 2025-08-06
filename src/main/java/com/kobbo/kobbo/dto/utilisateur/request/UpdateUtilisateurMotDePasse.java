package com.kobbo.kobbo.dto.utilisateur.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUtilisateurMotDePasse {
    private String ancienMotDePasse;
    private String nouveauMotDePasse;
}

package com.kobbo.kobbo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "comptes_societe")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "actif")
    private Boolean actif;

    @ManyToOne
    @JoinColumn(name = "utilisateurs_id")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "roles_id")
    private Role role;
}

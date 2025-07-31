package com.kobbo.kobbo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comptes_societe")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComptesSociete {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private Long id;

    @Column(name = "actif")
    private Boolean actif;

    @ManyToOne
    @JoinColumn(name = "utilisateurs_id")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "roles_id")
    private Role role;
}

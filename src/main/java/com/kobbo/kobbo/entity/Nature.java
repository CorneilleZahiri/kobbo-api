package com.kobbo.kobbo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "natures")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Nature {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "intitule")
    private String intitule;

    @ManyToOne
    @JoinColumn(name = "comptes_societe_id")
    private ComptesSociete comptesSociete;
}

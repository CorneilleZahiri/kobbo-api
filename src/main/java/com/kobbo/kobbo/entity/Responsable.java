package com.kobbo.kobbo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "responsables")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Responsable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "fonction")
    private String fonction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "societes_id")
    private Societe societe;
}

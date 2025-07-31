package com.kobbo.kobbo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Long id;

    @Column(name = "intitule")
    private String intitule;

    @ManyToOne
    @JoinColumn(name = "comptes_societe_id")
    private ComptesSociete comptesSociete;
}

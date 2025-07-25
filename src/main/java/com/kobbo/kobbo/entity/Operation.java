package com.kobbo.kobbo.entity;

import com.kobbo.kobbo.enumeration.Statut;
import com.kobbo.kobbo.enumeration.TypeOperation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "operations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_operation")
    private LocalDate dateOperation;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private Statut statut;

    @Column(name = "type_operation")
    private TypeOperation typeOperation;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "montant")
    private BigDecimal montant;

    @Column(name = "mode_de_payement")
    private String modeDePayement;

    @Column(name = "reference_mode_de_payement")
    private String referenceModeDePayement;

    @Column(name = "numero_formater")
    private String numeroFormater;

    @ManyToOne
    @JoinColumn(name = "tiers_id")
    private Tiers tiers;

    @ManyToOne
    @JoinColumn(name = "natures_id")
    private Nature nature;

    @ManyToOne
    @JoinColumn(name = "utilisateurs_id")
    private Utilisateur utilisateur;
}

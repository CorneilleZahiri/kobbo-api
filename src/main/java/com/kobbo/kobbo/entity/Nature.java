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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "intitule")
    private String intitule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "societes_id")
    private Societe societe;
}

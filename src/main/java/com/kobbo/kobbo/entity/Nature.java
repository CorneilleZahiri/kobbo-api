package com.kobbo.kobbo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "natures")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Nature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "intitule")
    private String intitule;
}

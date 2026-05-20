package com.miola.voitureshop.modele;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voiture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marque;
    private String modele;
    private String couleur;
    private String immatricule;
    private int annee;
    private double prix;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proprietaire_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Proprietaire proprietaire;
}
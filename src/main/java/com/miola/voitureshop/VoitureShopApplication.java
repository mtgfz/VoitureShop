package com.miola.voitureshop;

import com.miola.voitureshop.modele.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VoitureShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoitureShopApplication.class, args);
    }

    @Bean
    CommandLineRunner init(VoitureRepo vRepo, ProprietaireRepo pRepo) {
        return args -> {
            Proprietaire p1 = new Proprietaire(null, "Ali", "Hassan", null);
            Proprietaire p2 = new Proprietaire(null, "Najat", "Bani", null);
            pRepo.save(p1);
            pRepo.save(p2);

            vRepo.save(new Voiture(null, "Toyota", "Corolla", "Grise",  "A-1-9090", 2018, 95000, null));
            vRepo.save(new Voiture(null, "Ford",   "Fiesta",  "Rouge",  "A-2-8090", 2015, 90000, null));
            vRepo.save(new Voiture(null, "Honda",  "CRV",     "Bleu",   "A-3-7090", 2016, 140000, null));
            vRepo.save(new Voiture(null, "BMW",    "Serie3",  "Noir",   "A-4-6090", 2020, 250000, null));
            vRepo.save(new Voiture(null, "Renault","Clio",    "Blanc",  "A-5-5090", 2019, 75000, null));
        };
    }
}
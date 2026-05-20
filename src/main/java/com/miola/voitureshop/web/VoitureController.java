package com.miola.voitureshop.web;

import com.miola.voitureshop.modele.Voiture;
import com.miola.voitureshop.modele.VoitureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class VoitureController {

    @Autowired
    private VoitureRepo voitureRepo;

    @GetMapping("/voitures")
    public Iterable<Voiture> getAll() {
        return voitureRepo.findAll();
    }

    @GetMapping("/voitures/{id}")
    public Voiture getById(@PathVariable Long id) {
        return voitureRepo.findById(id).orElse(null);
    }

    @PostMapping("/voitures")
    public Voiture add(@RequestBody Voiture v) {
        return voitureRepo.save(v);
    }

    @PutMapping("/voitures/{id}")
    public Voiture update(@PathVariable Long id, @RequestBody Voiture v) {
        v.setId(id);
        return voitureRepo.save(v);
    }

    @DeleteMapping("/voitures/{id}")
    public void delete(@PathVariable Long id) {
        voitureRepo.deleteById(id);
    }

    // FILTRE par couleur
    @GetMapping("/voitures/couleur/{couleur}")
    public List<Voiture> byCouleur(@PathVariable String couleur) {
        return voitureRepo.findByCouleur(couleur);
    }

    // FILTRE par marque
    @GetMapping("/voitures/marque/{marque}")
    public List<Voiture> byMarque(@PathVariable String marque) {
        return voitureRepo.findByMarque(marque);
    }

    // FILTRE par prix
    @GetMapping("/voitures/prix")
    public List<Voiture> byPrix(@RequestParam double min, @RequestParam double max) {
        return voitureRepo.findByPrixBetween(min, max);
    }
}
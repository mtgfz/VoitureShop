package com.miola.voitureshop.modele;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.query.Param;
import java.util.List;

@RepositoryRestResource
public interface VoitureRepo extends CrudRepository<Voiture, Long> {
    List<Voiture> findByCouleur(@Param("couleur") String couleur);
    List<Voiture> findByMarque(@Param("marque") String marque);
    List<Voiture> findByPrixBetween(@Param("min") double min, @Param("max") double max);
    List<Voiture> findByAnneeGreaterThanEqual(@Param("annee") int annee);
}
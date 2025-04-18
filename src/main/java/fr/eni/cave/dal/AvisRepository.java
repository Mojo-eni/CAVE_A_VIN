package fr.eni.cave.dal;

import fr.eni.cave.bo.client.Avis;
import fr.eni.cave.bo.vin.Bouteille;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvisRepository extends JpaRepository<Avis, Integer> {
    List<Avis> findByBouteille(Bouteille bouteille);
}

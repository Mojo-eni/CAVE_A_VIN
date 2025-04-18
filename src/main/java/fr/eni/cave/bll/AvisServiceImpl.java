package fr.eni.cave.bll;

import fr.eni.cave.bo.client.Avis;
import fr.eni.cave.dal.AvisRepository;
import fr.eni.cave.dal.BouteilleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AvisServiceImpl implements AvisService {
    private BouteilleRepository bRepository;
    private AvisRepository avisRepository;
    @Override
    public Avis createAvis(int idBouteille, Avis avis) {
        if(avis == null) {
            throw new RuntimeException("L'avis ne peut pas être null");
        }
        if (idBouteille <= 0) {
            throw new RuntimeException("Identifiant n'existe pas");
        }
        return avisRepository.save(avis);
    }

    @Override
    public List<Avis> getAvisByBouteilleId(int idBouteille) {
       return avisRepository.findByBouteille(bRepository.findById(idBouteille).orElseThrow(() -> new RuntimeException("Bouteille non trouvée")));
    }

}

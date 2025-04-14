package fr.eni.cave.api;

import fr.eni.cave.bll.BouteilleServiceImpl;
import fr.eni.cave.bo.vin.Bouteille;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/caveavin/bouteilles")
public class BouteilleController {

    final private BouteilleServiceImpl bouteilleService;

    public BouteilleController(BouteilleServiceImpl bouteilleService) {
        this.bouteilleService = bouteilleService;
    }

    @GetMapping
    public ResponseEntity<List<Bouteille>> getAllBouteilles() {
        List<Bouteille> bouteilles = bouteilleService.chargerToutesBouteilles();
        if (bouteilles != null) {
            return ResponseEntity.of(Optional.of(bouteilles));
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bouteille> getBouteilleById(@PathVariable int id){
        try {
            Bouteille bouteille = bouteilleService.chargerBouteilleParId(id);
                return  new ResponseEntity<>(bouteille, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return  new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/region/{id}")
    public ResponseEntity<List<Bouteille>> getByRegion(@PathVariable int id){
        try {
            List<Bouteille> bouteilles = bouteilleService.chargerBouteillesParRegion(id);
            return  new ResponseEntity<>(bouteilles, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return  new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/couleur/{id}")
    public ResponseEntity<List<Bouteille>> getByCouleur(@PathVariable int id){
        try {
            List<Bouteille> bouteilles = bouteilleService.chargerBouteillesParCouleur(id);
            return  new ResponseEntity<>(bouteilles, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return  new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}

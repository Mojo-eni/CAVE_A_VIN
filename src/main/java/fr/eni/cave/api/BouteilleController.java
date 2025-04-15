package fr.eni.cave.api;

import fr.eni.cave.bll.BouteilleServiceImpl;
import fr.eni.cave.bo.vin.Bouteille;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity saveBouteille(@RequestBody Bouteille bouteille) {
        try {
            Bouteille savedBouteille = bouteilleService.enregistrerBouteille(bouteille);
            return new ResponseEntity<>(savedBouteille, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Erreur lors de l'enregistrement de la bouteille : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity deleteBouteille(@PathVariable int id) {
        try {
            bouteilleService.supprimerBouteille(id);
            return ResponseEntity.ok("Bouteille supprimée avec succès");
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression de la bouteille : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }
}

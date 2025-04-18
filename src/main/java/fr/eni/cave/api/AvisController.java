package fr.eni.cave.api;

import fr.eni.cave.bll.AvisServiceImpl;
import fr.eni.cave.bo.client.Avis;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/caveavin/bouteilles/{idBouteille}/avis")
public class AvisController {
    final private AvisServiceImpl avisService;
    public AvisController(AvisServiceImpl avisService) {
        this.avisService = avisService;
    }

    @PostMapping
    public ResponseEntity<Avis> createAvis(@PathVariable int idBouteille, @Valid @RequestBody Avis avis) {
        try {
            Avis createdAvis = avisService.createAvis(idBouteille, avis);
            return new ResponseEntity<>(createdAvis, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating avis: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Avis>> getAvisByBouteilleId(@PathVariable int idBouteille) {
        try {
            List<Avis> avisList = avisService.getAvisByBouteilleId(idBouteille);
            return new ResponseEntity<>(avisList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching avis: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

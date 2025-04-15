package fr.eni.cave.bll;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.eni.cave.bo.vin.Bouteille;
import fr.eni.cave.bo.vin.Couleur;
import fr.eni.cave.bo.vin.Region;
import fr.eni.cave.dal.BouteilleRepository;
import fr.eni.cave.dal.CouleurRepository;
import fr.eni.cave.dal.RegionRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BouteilleServiceImpl implements BouteilleService {
	private BouteilleRepository bRepository;
	private RegionRepository rRepository;
	private CouleurRepository cRepository;

	@Override
	public List<Bouteille> chargerToutesBouteilles() {
		return bRepository.findAll();
	}

	@Override
	public Bouteille chargerBouteilleParId(int idBouteille) {
		// Validation de l'identifiant
		if (idBouteille <= 0) {
			throw new RuntimeException("Identifiant n'existe pas");
		}

		final Optional<Bouteille> opt = bRepository.findById(idBouteille);
		if (opt.isPresent()) {
			return opt.get();
		}
		// Identifiant correspond à aucun enregistrement en base
		throw new RuntimeException("Aucune bouteille ne correspond");
	}

	@Override
	public List<Bouteille> chargerBouteillesParRegion(int idRegion) {
		final Region rDB = validerRegion(idRegion);

		final List<Bouteille> listeDB = bRepository.findByRegion(rDB);
		if (listeDB == null || listeDB.isEmpty()) {
			throw new RuntimeException("Aucune bouteille ne correspond");
		}
		return listeDB;		
	}

	private Region validerRegion(int idRegion) {
		// Valider la Region
		if (idRegion <= 0) {
			throw new RuntimeException("Identifiant n'existe pas");
		}

		final Optional<Region> opt = rRepository.findById(idRegion);
		if (opt.isPresent()) {
			return opt.get();
		}
		// Identifiant correspond à aucun enregistrement en base
		throw new RuntimeException("Aucune région ne correspond");
	}

	@Override
	public List<Bouteille> chargerBouteillesParCouleur(int idCouleur) {
		final Couleur cDB = validerCouleur(idCouleur);

		final List<Bouteille> listeDB = bRepository.findByCouleur(cDB);
		if (listeDB == null || listeDB.isEmpty()) {
			throw new RuntimeException("Aucune bouteille ne correspond");
		}
		return listeDB;		
	}

	private Couleur validerCouleur(int idCouleur) {
		// Valider la Couleur
		if (idCouleur <= 0) {
			throw new RuntimeException("Identifiant n'existe pas");
		}

		final Optional<Couleur> opt = cRepository.findById(idCouleur);
		if (opt.isPresent()) {
			return opt.get();
		}
		// Identifiant correspond à aucun enregistrement en base
		throw new RuntimeException("Aucune couleur de vin ne correspond");
	}

	public Bouteille enregistrerBouteille(Bouteille bouteille) throws BllException {
		// Validation de la bouteille
		if (bouteille == null) {
			throw new BllException("La bouteille est nulle");
		}

		if (bouteille.getNom() == null || bouteille.getNom().isEmpty()) {
			throw new BllException("Le nom de la bouteille est vide", bouteille);
		}

		if (bouteille.getRegion() == null) {
			throw new BllException("La région de la bouteille est nulle", bouteille);
		}

		if (bouteille.getCouleur() == null) {
			throw new BllException("La couleur de la bouteille est nulle", bouteille);
		}

		return bRepository.save(bouteille);
	}

	public Bouteille modifierBouteille(int id, Bouteille bouteille) throws BllException {
		// Validation de l'identifiant
		if (id <= 0) {
			throw new BllException("Identifiant n'existe pas");
		}
		final Optional<Bouteille> opt = bRepository.findById(id);
		if (opt.isPresent()) {
			bouteille.setId(id);
			bRepository.save(bouteille);
		} else {
			throw new BllException("Aucune bouteille ne correspond", bouteille);
		}
		return bouteille;
	}

	public void supprimerBouteille(int id) throws BllException {
		// Validation de l'identifiant
		if (id <= 0) {
			throw new BllException("Identifiant n'existe pas");
		}

		final Optional<Bouteille> opt = bRepository.findById(id);
		if (opt.isPresent()) {
			bRepository.delete(opt.get());
		} else {
			throw new BllException("Aucune bouteille ne correspond");
		}
	}
}

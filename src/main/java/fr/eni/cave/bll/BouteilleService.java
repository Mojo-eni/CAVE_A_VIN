package fr.eni.cave.bll;

import java.util.List;

import fr.eni.cave.bo.vin.Bouteille;

public interface BouteilleService {
	List<Bouteille> chargerToutesBouteilles();
	
	Bouteille chargerBouteilleParId(int idBouteille);

	List<Bouteille> chargerBouteillesParRegion(int idRegion);

	List<Bouteille> chargerBouteillesParCouleur(int idCouleur);

	Bouteille enregistrerBouteille(Bouteille bouteille) throws BllException;

	Bouteille modifierBouteille(int id, Bouteille bouteille) throws BllException;

	void supprimerBouteille(int idBouteille) throws BllException;
}

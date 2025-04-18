package fr.eni.cave.bll;

import fr.eni.cave.bo.client.Avis;

import java.util.List;

public interface AvisService {
    Avis createAvis(int idBouteille, Avis avis);

    List<Avis> getAvisByBouteilleId(int idBouteille);
}

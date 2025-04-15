package fr.eni.cave.bll;

import fr.eni.cave.bo.vin.Bouteille;

public class BllException extends Exception {
    private static final long serialVersionUID = 1L;
    private Bouteille bouteille;
    public BllException(String message) {
        super(message);
    }

    public BllException(String message, Bouteille bouteille) {
        super(message);
        this.bouteille = bouteille;
    }

    public BllException(String message, Throwable cause) {
        super(message, cause);
    }
}

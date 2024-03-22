package filmothequeCyril.bo;

import jakarta.persistence.Entity;

/**
 * Lombok n'arrive pas bien à gérer l'héritage
 * => on va générer les constructeurs à la main
 */
@Entity
public class Participant extends Personne{
    // pas besoin de remettre @Id car il est déjà défini dans Personne

    /*
    * Constructeurs
     */
    public Participant(long id, String nom, String prenom) {
        super(id, nom, prenom);
    }

    public Participant(String nom, String prenom) {
        super(nom, prenom);
    }

    public Participant() {
        super();
    }

    /**
    * Je surcharge la méthode toString()
     * afin d'éviter pour chaque affichage de participant (réalisateur, acteur)
     * d'utiliser | ${participant.prenom} ${participant.nom}|
     */
    @Override
    public String toString() {
        return prenom + " " + nom;
    }
}

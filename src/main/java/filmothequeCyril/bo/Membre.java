package filmothequeCyril.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import lombok.Data;

/**
 * Lombok n'arrive pas bien à gérer l'héritage
 * => on va générer les constructeurs à la main
 */
@Data
@Entity
public class Membre extends Personne{
    // pas besoin de remettre @Id car il est déjà défini dans Personne

    private String pseudo;
    @JsonIgnore // on affiche pas le mot de passe dans le JSON
    private String motDePasse;
    private boolean admin;

    /*
    * pas besoin d'avoir la liste des avis dans Membre
    * car on va toujurs accéder d'abord aux avis et ensuite au membre
    * => association unidirectionnelle portée par la classe Avis
    * List<Avis> avis = new ArrayList<>();
     */


    /*
    * Constructeurs
     */

    public Membre(long id, String nom, String prenom, String pseudo, String motDePasse) {
        super(id, nom, prenom);
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
    }

    public Membre(String nom, String prenom, String pseudo, String motDePasse, boolean admin) {
        super(nom, prenom);
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
        this.admin = admin;
    }

    public Membre() {
    }
}

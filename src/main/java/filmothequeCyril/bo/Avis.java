package filmothequeCyril.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Min(0) @Max(5) @NotNull
    public int note;
    public String commentaire;

    /*
    * ASSOCIATIONS Membre <-> Avis
     */
    //  Si on suit la cardinalité du diagramme de classe, on a Avis * -> 1 Membre
    // * -> 1   = @ManyToOne
    @ManyToOne
    private Membre membre;

    /**
     * ASSOCIATION BIDIRECTIONNELLE (plus pratique pour l'ajout d'avis)
     */
    @ManyToOne
    @JsonIgnore // POUR EVITER LA BOUCLE INFINIE LORSQU'ON AFFICHE LES FILMS (afin de ne pas avoir le Film -> Avis -> film -> etc...)
    Film film;

    /**
     * Je redéfini le setter de film
     * afin de m'assurer que qand j'ajoute un film à avis
     * j'ajoute par la même occasion l'avis au film
     **/
    public void setFilm(Film film) {
        this.film = film; // j'ajoute le film à avis
        film.getAvis().add(this); // j'ajoute par la même occasion l'avis au film
    }

    /*
     * pas besoin d'avoir le film associé à l'avis
     * car on va toujurs accéder d'abord aux film et ensuite aux avis associés
     * => association unidirectionnelle portée par la classe Film
     * Film film;
     */
}

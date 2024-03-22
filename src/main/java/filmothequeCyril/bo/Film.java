package filmothequeCyril.bo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @NotBlank // le titre doit être défini et non vide
    public String titre;
    @Min(1900) // L'année doit être supérieure ou égale à 1900
    public int annee;
    @Min(1) // La durée doit être au minimum de 1 minute
    public int duree;
    @Size(min = 20, max = 250) // Le synopsis doit faire entre 20 et 250 caractère
    public String synopsis;

    /*
    * ASSOCIATIONS
     */
    @NotNull // @NotBlank marche que pour les Strings => il faut utiliser @NotNull pour les objets
    //  Si on suit la cardinalité du diagramme de classe, on a Film * -> 1 Genre
    // * -> 1   = @ManyToOne
    @ManyToOne
    private Genre genre;
    //  Si on suit la cardinalité du diagramme de classe, on a Film 1 -> * Avis
    // 1 -> *   = @OneToMany
    @OneToMany(cascade = CascadeType.ALL) // je vais sauvegarder/modifier/supprimer les avis en même temps que les films
    @JoinColumn(name = "film_id")// si je laisse par défaut, je vais avoir une table de jointure film_avis, je préfère avoir une colonne de jointure dans la table Avis
    private List<Avis> avis = new ArrayList<>();

    //  Si on suit la cardinalité du diagramme de classe, on a Film * -> 1 Participant
    // * -> 1   = @ManyToOne
    @ManyToOne
    private Participant realisateur;

    //  Si on suit la cardinalité du diagramme de classe, on a Film * -> * Participant
    // * -> *   = @ManyToMany
    @ManyToMany
    private List<Participant> acteurs = new ArrayList<>();

    /**
     * Constructeurs
     * Doivent être déclarées explicitement pour un nombre d'argument qui
     * n'est pas "Tous les arguments" iou "Aucun argument" (lombok ne sait pas faire)
     */
    public Film(long id, String titre, int annee, int duree, String synopsis) {
        this.id = id;
        this.titre = titre;
        this.annee = annee;
        this.duree = duree;
        this.synopsis = synopsis;
    }
}

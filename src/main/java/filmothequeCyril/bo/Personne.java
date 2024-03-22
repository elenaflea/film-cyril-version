package filmothequeCyril.bo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass // en ajoutant @MappedSuperclass et en enlevant @Entity, je fait en sorte de ne pas créer de table pour la classe Personne
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // Crée une table pour chaque entité  en mettant dans chaque table toutes les données correspondantes (pas de mutualisation de donnée)
public abstract class Personne {
    @Id
    // à partir du moment où on utilise la stratégie d'héritage TABLE_PER_CLASS, on ne peut plus utiliser : strategy = GenerationType.IDENTITY
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    @NotBlank
    public String nom;
    @NotBlank
    public String prenom;

    /*
    * constructeur supplémentaire
     */
    public Personne(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }
}

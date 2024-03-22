package filmothequeCyril.bo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    @NotBlank
    public String titre;

    /*
    * constructeur supplémentaire
     */
    public Genre(String titre) {
        this.titre = titre;
    }

    /*
     * pas besoin d'avoir la liste des films dans Genre
     * car on va toujurs accéder d'abord aux film et ensuite au genre associé
     * => association unidirectionnelle portée par la classe Film
     * List<Film> films = new ArrayList<>();
     */
}

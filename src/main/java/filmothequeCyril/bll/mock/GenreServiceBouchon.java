package filmothequeCyril.bll.mock;

import filmothequeCyril.bll.IGenreService;
import filmothequeCyril.bo.Genre;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface IGenreService
 * qui va être injectée dans le controller GenreController
 *
 * @Service permet de rendre une instance de cette classe
 * disponible dans le contexte Spring (obligatoire afin d'être injectée)
 *
 * @Profile("dev")
 * Specifie que cette classe est activée UNIQUEMENT lorsqu'on a le profil "dev"
 * dans application.properties
 */
@Service
@Profile("dev")
public class GenreServiceBouchon implements IGenreService {

    /*
    * Dans l'implémentation "bouchon"
    * on gère les genres dans une liste
     */
    private  List<Genre> listeGenres = new ArrayList<>();

    /*
    * On initialise la liste des genres
     */
    public GenreServiceBouchon() {
        creerGenre(new Genre("Animation"));
        creerGenre(new Genre("Science-fiction"));
        creerGenre(new Genre("Documentaire"));
        creerGenre(new Genre("Action"));
        creerGenre(new Genre("Drame"));
    }

    // on gère un compteur pour l'id (qui simule l'auto-increment de la base de donnée)
    private int idCourant = 1;

    @Override
    public List<Genre> consulterGenres() {
        return listeGenres;
    }

    @Override
    public Genre consulterGenreParId(long id) {
        // on recherche dans la liste le genre d'id "id"
        for (Genre genre : listeGenres) {
            if (genre.getId() == id){
                return genre;
            }
        }
        // on retourne null si pas trouvé
        return null;
    }

    @Override
    public void supprimerGenreParId(long id) {
        // on recherche dans la liste l'index correspondant au genre d'id "id"
        for (int index = 0; index < listeGenres.size(); index++) {
            if (listeGenres.get(index).getId() == id){
                listeGenres.remove(index);
            }
        }
    }

    @Override
    public void creerGenre(Genre genre) {
        // on rajoute l'id au genre (tout en l'incrémentant)
        genre.setId(idCourant++);
        // on rajoute le genre à la liste
        listeGenres.add(genre);
    }
}

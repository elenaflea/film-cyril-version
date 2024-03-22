package filmothequeCyril.bll;

import filmothequeCyril.bo.Genre;

import java.util.List;

/**
 * Interface
 * Ca n'est pas instancié
 * Donc, aucun interêt de mettre @Service dedans
 */
public interface IGenreService {

    List<Genre> consulterGenres();

    Genre consulterGenreParId(long id);

    void supprimerGenreParId(long id);

    void creerGenre(Genre genre);
}

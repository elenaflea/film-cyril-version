package filmothequeCyril.bll;

import filmothequeCyril.bo.Avis;
import filmothequeCyril.bo.Film;
import filmothequeCyril.dto.SearchParam;

import java.util.List;

/**
 * Interface
 * Ca n'est pas instancié
 * Donc, aucun interêt de mettre @Service dedans
 */
public interface IFilmService {
    List<Film> consulterFilms();

    Film consulterFilmParId(long id);

    void creerFilm(Film film);

     void publierAvis(Avis avis, long idFilm);

    List<Film> rechercher(SearchParam searchParam);
}

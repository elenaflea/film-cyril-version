package filmothequeCyril.bll.jpa;

import filmothequeCyril.bo.Avis;
import filmothequeCyril.bo.Film;
import filmothequeCyril.dal.AvisRepository;
import filmothequeCyril.bll.IFilmService;
import fr.eni.tp.filmotheque.bo.*;
import filmothequeCyril.dal.FilmRepository;
import filmothequeCyril.dto.SearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implémentation de l'interface IFilmService
 * qui va être injectée dans le controller FilmController
 *
 * @Service permet de rendre une instance de cette classe
 * disponible dans le contexte Spring (obligatoire afin d'être injectée)
 *
 * @Profile("prod")
 * Specifie que cette classe est activée UNIQUEMENT lorsqu'on a le profil "prod"
 * dans application.properties
 */
@Service
@Profile("prod")
public class FilmServiceJpaImpl implements IFilmService {

	@Autowired
	private FilmRepository filmRepository;

	@Autowired
	private AvisRepository avisRepository;

	@Override
	public List<Film> consulterFilms() {
		return filmRepository.findAll();
	}

	@Override
	public Film consulterFilmParId(long id) {
		return filmRepository.findById(id).orElse(null);
	}

	@Override
	public void creerFilm(Film film) {
		filmRepository.save(film);
	}

	/**
	 * Méthode qu'on peut utiliser si on ne veut pas de relation bidirectionnelle entre Fim et Avis
	 */
	public void publierAvisENModeUnidirectionnel(Avis avis, long idFilm) {
		// 1 - je recupère le film
		Film film = consulterFilmParId(idFilm);

		// 2 - je mets à jour la liste des avis
		film.getAvis().add(avis);

		// 3 - je sauvegarde le film, si j'ai mis (cascade = CascadeType.ALL) : ca devrait ajouter l'avis en base de donnée
		filmRepository.save(film);
	}

	@Override
	public void publierAvis(Avis avis, long idFilm) {
		/** possible car on a une relation BIDIRECTIONNELLE **/
		// 1 - je met à jour l'avis avec le film (ATTENTION a bien redefinir le setFilm de Avis pour mettre à jour la liste des films)
		avis.setFilm(filmRepository.findById(idFilm).orElse(null));

		// 2 - je sauvegarde l'avis, si j'ai mis (cascade = CascadeType.ALL) : ca devrait ajouter l'avis en base de donnée
		avisRepository.save(avis);
	}

	@Override
	public List<Film> rechercher(SearchParam searchParam) {
		// return filmRepository.findByTitreStartingWithOrGenreTitreStartingWithOrRealisateurPrenomStartingWithOrRealisateurNomStartingWith(searchParam.getSearch(),searchParam.getSearch(), searchParam.getSearch(), searchParam.getSearch());
		return filmRepository.recherche(searchParam);
	}
}

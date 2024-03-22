package filmothequeCyril.bll.mock;

import filmothequeCyril.bll.IGenreService;
import filmothequeCyril.bll.IParticipantService;
import filmothequeCyril.bo.*;
import filmothequeCyril.bll.IFilmService;
import fr.eni.tp.filmotheque.bo.*;
import filmothequeCyril.dto.SearchParam;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface IFilmService
 * qui va être injectée dans le controller FilmController
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
public class FilmServiceBouchon implements IFilmService {
	// Attributs statiques pour gérer les valeurs à afficher et simuler les données
	// en base

	// on référence nos services des Genres et des participants
	private IGenreService genreService;
	private IParticipantService participantService;

	private static List<Film> lstFilms = new ArrayList<>();
	private static List<Genre> lstGenres;
	private static List<Participant> lstParticipants = new ArrayList<>();
	private static int indexFilms = 1;
	private static int indexAvis = 1;

	public FilmServiceBouchon(IGenreService genreService, IParticipantService participantService) {
		this.genreService = genreService;
		this.participantService = participantService;
		lstGenres = genreService.consulterGenres();
		lstParticipants = participantService.consulterParticipants();
		simulationCoucheDALetDB();
	}

	@Override
	public List<Film> consulterFilms() {
		return lstFilms;
	}

	/**
	 * @return film si id correspond
	 * @return null si inconnu
	 */
	@Override
	public Film consulterFilmParId(long id) {
		return lstFilms.stream().filter(item -> item.getId() == id).findAny().orElse(null);
	}


	public List<Genre> consulterGenres() {
		return lstGenres;
	}


	public List<Participant> consulterParticipants() {
		return lstParticipants;
	}


	public Genre consulterGenreParId(long id) {
		return lstGenres.stream().filter(item -> item.getId() == id).findAny().orElse(null);
	}

	@Override
	public void creerFilm(Film film) {
		// Sauvegarde du film
		film.setId(indexFilms++);
		lstFilms.add(film);
	}
	@Override
	public void publierAvis(Avis avis, long idFilm) {
		Film filmSelectionne = consulterFilmParId(idFilm);
		if (filmSelectionne != null) {
			avis.setId(indexAvis++);
			filmSelectionne.getAvis().add(avis);
		}

	}

	@Override
	public List<Film> rechercher(SearchParam searchParam) {
		return null;
	}


	/**
	 * Cette méthode permet de simuler le stockage en base de données et la remontée
	 * d'information
	 */
	public void simulationCoucheDALetDB() {


		// Création de la liste de films
		// 4 films
		Film jurassicPark = new Film(indexFilms++, "Jurassic Park", 1993, 128,
				"Le film raconte l'histoire d'un milliardaire et son équipe de généticiens parvenant à ramener à la vie des dinosaures grâce au clonage.");
		jurassicPark.setGenre(lstGenres.get(1));
		jurassicPark.setRealisateur(participantService.consulterParticipantParId(1));
		// Associer les acteurs
		jurassicPark.getActeurs().add(participantService.consulterParticipantParId(4));
		jurassicPark.getActeurs().add(participantService.consulterParticipantParId(5));
		lstFilms.add(jurassicPark);

		Film theFly = new Film(indexFilms++, "The Fly", 1986, 95,
				"Il s'agit de l'adaptation cinématographique de la nouvelle éponyme de l'auteur George Langelaan.");
		theFly.setGenre(lstGenres.get(1));
		theFly.setRealisateur(participantService.consulterParticipantParId(2));
		// Associer les acteurs
		theFly.getActeurs().add(participantService.consulterParticipantParId(5));
		theFly.getActeurs().add(participantService.consulterParticipantParId(6));
		lstFilms.add(theFly);

		Film theBFG = new Film(indexFilms++, "The BFG", 2016, 117,
				"Le Bon Gros Géant est un géant bien différent des autres habitants du Pays des Géants.");
		theBFG.setGenre(lstGenres.get(4));
		theBFG.setRealisateur(participantService.consulterParticipantParId(1));
		// Associer les acteurs
		theBFG.getActeurs().add(participantService.consulterParticipantParId(7));
		theBFG.getActeurs().add(participantService.consulterParticipantParId(8));
		lstFilms.add(theBFG);


		Film bienvenueChezLesChtis = new Film(indexFilms++, "Bienvenue chez les Ch'tis", 2008, 106,
				"Philippe Abrams est directeur de la poste de Salon-de-Provence. Il est marié à Julie, dont le caractère dépressif lui rend la vie impossible. Pour lui faire plaisir, Philippe fraude afin d'obtenir une mutation sur la Côte d'Azur. Mais il est démasqué: il sera muté à Bergues, petite ville du Nord.");
		bienvenueChezLesChtis.setGenre(lstGenres.get(4));
		bienvenueChezLesChtis.setRealisateur(participantService.consulterParticipantParId(3));
		// Associer les acteurs
		bienvenueChezLesChtis.getActeurs().add(participantService.consulterParticipantParId(3));
		bienvenueChezLesChtis.getActeurs().add(participantService.consulterParticipantParId(9));
		lstFilms.add(bienvenueChezLesChtis);

		// Création d'un membre et un avis
		Membre membre1 = new Membre(1, "Baille", "Anne-Lise", "abaille@campus-eni.fr", null);
		Avis avis = new Avis(1, 4, "On rit du début à la fin", membre1, null);
		bienvenueChezLesChtis.getAvis().add(avis);
	}
}

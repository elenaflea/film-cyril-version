package filmothequeCyril.bll.jpa;

import filmothequeCyril.bll.IGenreService;
import filmothequeCyril.bo.Genre;
import filmothequeCyril.dal.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implémentation de l'interface IGenreService
 * qui va être injectée dans le controller GenreController
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
public class GenreServiceJpaImpl implements IGenreService {
    /*
    * j'injecte le repository qui va me servir à interagir avec la base de donnée
     */
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<Genre> consulterGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre consulterGenreParId(long id) {
        return genreRepository.findById(id).get();
    }

    @Override
    public void supprimerGenreParId(long id) {
        /*
        * TODO : rajouter des validations pour s'assurer que le genre n'est pas referencé par des films
         */
        genreRepository.deleteById(id);
    }

    @Override
    public void creerGenre(Genre genre) {
        genreRepository.save(genre);
    }
}

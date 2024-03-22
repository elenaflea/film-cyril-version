package filmothequeCyril.converter;

import filmothequeCyril.bll.IGenreService;
import filmothequeCyril.bo.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * On définit une classe GenreConverter
 * Qui implémente org.springframework.core.convert.converter.Converter
 * et qui spécifie
 * comment Spring va pouvoir générer une instance de Genre (format Java)
 * à partir d'un paramètre au format texte envoyé dans la requête HTTP
 */

@Component // attention à bien mettre le converter dans le contexte Spring
public class GenreConverter implements Converter<String, Genre> {

    @Autowired
    private IGenreService genreService;

    /**
     * Comment est-ce qu'on passe d'un id de genre au format texte
     * à une instance de Genre?
     */
    @Override
    public Genre convert(String idAuFormatTexte) {
        // on se sert du service pour recupérer le genre correspondant à l'id passé en paramètre
        return genreService.consulterGenreParId(Long.parseLong(idAuFormatTexte));
    }
}

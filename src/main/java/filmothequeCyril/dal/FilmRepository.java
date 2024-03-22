package filmothequeCyril.dal;

import filmothequeCyril.bo.Film;
import filmothequeCyril.dto.SearchParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * JPARepository est une interface génerique qui prends 2 paramètres :
 * - le type de l'entité qu'on veut gérer, ici : Film
 * - le type de l'attribut @Id (clé primaire) de cette entité , ici : Long
 */
// Spring va AUTOMATIQUEMENT créer une classe qui implémente cette interface avec les méthodes save()/findAll()/etc...
// et la rendre disponible dans le contexte Spring comme un bean
public interface FilmRepository extends JpaRepository<Film,Long> {

    /**
     * Si j'ai juste besoin de faire une recherche sur un seul critère (barre de recherche unique)
     * Un query method peut faire l'affaire
     *
     * par contre :
     * - le nom de méthode est très blong
     * - on ne peut pas faire de recherche sur des prénoms ou noms d'acteurs
     * - si jamais on veut mettre des filtres optionnels, il va falloir crééer autant de méthodes que de filtres (et même plus)
     */
    public List<Film> findByTitreStartingWithOrGenreTitreStartingWithOrRealisateurPrenomStartingWithOrRealisateurNomStartingWith(String titre, String genre, String realisateurPrenom, String realisateurNom);


    /**
     * Pour une recherche un peu plus complexe, on va utiliser une Named Query en JPQL
     * */
    @Query(
            // on fait d'abord la recerche correspondant au terme de recherche
            "select f from Film f " +

            // je fais une jointure explicite externe sur la table des acteurs afin de pouvoir rechercher également dans leur prénom/nom
            "left outer join f.acteurs a " +

            /****** PARAMETRES DE RECHERCHE **********/
            "where (f.titre like :#{#param.search}% or " +
            "f.genre.titre like :#{#param.search}% or " +
            "f.realisateur.prenom like :#{#param.search}% or " +
            "f.realisateur.nom like :#{#param.search}% or " +
            "a.prenom like :#{#param.search}% or " +
            "a.nom like :#{#param.search}%)" +

            /****** PARAMETRES DE FILTRES **********/
            // et ensuite, si il y a un filtre par pays de naissance, on ajoute une clause de recherche suppleémentaire
            // soit le filtre n'est pas mis soit le champs retourné correspond au filtre
            // filtre sur le genre
            "AND ( :#{#param.genre} is NULL OR :#{#param.genre} = 0 OR :#{#param.genre} = f.genre.id ) " +
            // filtre sur la durée min
            "AND ( :#{#param.dureeMin} is NULL OR :#{#param.dureeMin} < f.duree ) " +
            // filtre sur la durée max
            "AND ( :#{#param.dureeMax} is NULL OR :#{#param.dureeMax} > f.duree ) " +
            // filtre sur la année min
            "AND ( :#{#param.anneeMin} is NULL OR :#{#param.anneeMin} < f.annee ) " +
            // filtre sur la année max
            "AND ( :#{#param.anneeMax} is NULL OR :#{#param.anneeMax} > f.annee ) "
                    )
    List<Film> recherche(SearchParam param);
}

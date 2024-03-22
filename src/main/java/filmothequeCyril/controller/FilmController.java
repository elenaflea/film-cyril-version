package filmothequeCyril.controller;

import filmothequeCyril.bll.IFilmService;
import filmothequeCyril.bll.IGenreService;
import filmothequeCyril.bll.IParticipantService;
import filmothequeCyril.bo.Avis;
import filmothequeCyril.bo.Film;
import filmothequeCyril.bo.Genre;
import filmothequeCyril.bo.Participant;
import filmothequeCyril.dto.SearchParam;
import filmothequeCyril.security.UtilisateurSpringSecurity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/films") // toutes les urls de mon controller vont être accessibles avec le prefixe : /films
public class FilmController {

    // on crée un attribut de type IFilmService qui va être auto-injecté par Spring
    /* si on veut injecter un service, il faut penser à mettre une instance de classe dans le coontexte Spring
    * (en ajoutant @Service avant le nom de la classe) */
    @Autowired
    private IFilmService filmService;
    @Autowired
    private IGenreService genreService;
    @Autowired
    private IParticipantService participantService;

    /**
     * Méthodes @ModelAttribute
     * Va faire en sorte que pour TOUS les templates référencés par mon controller
     * les attributs suivants vont être disponible dans le modèle
     */

    // dans tous les templates : "listeGenres" va être accessible et prendre la valeur de l'appel : genreService.consulterGenres();
    @ModelAttribute("listeGenres")
    public  List<Genre> listeGenres(){
        return genreService.consulterGenres();
    }

    // dans tous les templates : "listeParticipants" va être accessible et prendre la valeur de l'appel : participantService.consulterParticipants();
    @ModelAttribute("listeParticipants")
    public  List<Participant> listeParticipants(){
        return participantService.consulterParticipants();
    }


    /**
     * est appelé lorsque j'accède à l'url : http://localhost:8080/films
     * va mettre dans le model la liste des films
     * afin de pouvoir l'afficher dans la table HTML de mon template
     * avec th:each
     */
    @GetMapping
    public String getFilms(SearchParam searchParam, Model model){

        // Si jamais j'ai un paramètre de recherche
        if (searchParam.getSearch() != null){
            // je mets dans le modèle les films possèdant le titre donné
            model.addAttribute("listeFilms", filmService.rechercher(searchParam));
        }
        // sinon, je mets dans le modèle tous les films
        else{
            model.addAttribute("listeFilms", filmService.consulterFilms());
        }

        /*
            je retourne le template films.html
            je vais pouvoir accéder à mon attribut listeFilms dans le template
            via la syntaxe ${listeFilms}
         */
        return "films";
    }


    @GetMapping("/creer")
    public String getFilmCreation(Model model){

        // afin d'utiliser th:object="${film}" dans mon template, je dois initialiser dans le modèle un attribut "genre" de type Genre
        model.addAttribute("film", new Film());

        return "filmCreation";
    }

    @PostMapping("/creer")
    public String postFilmCreation(@Valid Film film, BindingResult bindingResult, Model model){

        // si la validation échoue
        if (bindingResult.hasErrors()){
            // je redirige sur le template
            return "filmCreation";
        }

       // sinon, si c'est OK, on ajoute le film
        filmService.creerFilm(film);

        // on redirige en GET (nouvelle requête) sur la page qui liste tous les films
        return "redirect:/films";
    }


    /**
     * est appelé lorsque j'accède à l'url : http://localhost:8080/films/{idFilm}
     * va mettre dans le model le film qui correspond à l'id passé en paramètre
     * afin de pouvoir l'afficher dans le HTML de mon template
     *
     * Pour recupérer l'id depuis l'url
     * on utilise un argument @PathVariable("idFilm") long idFilm
     */
    @GetMapping("/{idFilm}")
    public String getFilmDetail(@PathVariable("idFilm") long idFilm, Model model){

        // je mets dans le modèle un attribut "film" qui va contenir le film correspondant à l'id : idFilm
        model.addAttribute("film", filmService.consulterFilmParId(idFilm));
        /*
            je retourne le template filmDetail.html
            je vais pouvoir accéder à mon attribut film dans le template
            via la syntaxe ${film}
         */
        return "filmDetail";
    }

    /**
     * est appelé lorsque j'accède à l'url : http://localhost:8080/films/{idFilm}/avis
     * va mettre dans le model le film qui correspond à l'id passé en paramètre
     * afin de pouvoir l'afficher dans le HTML de mon template
     *
     * Pour recupérer l'id depuis l'url
     * on utilise un argument @PathVariable("idFilm") long idFilm
     */
    @GetMapping("/{idFilm}/avis")
    public String getFilmAvisForm(@PathVariable("idFilm") long idFilm, Model model){

        // je mets dans le modèle un attribut "film" qui va contenir le film correspondant à l'id : idFilm
        model.addAttribute("film", filmService.consulterFilmParId(idFilm));
        model.addAttribute("avis", new Avis());
        /*
            je retourne le template filmDetail.html
            je vais pouvoir accéder à mon attribut film dans le template
            via la syntaxe ${film}
         */
        return "avis";
    }

    /**
     * est appelé lorsque je valide le formulaire de création d'avis
     *
     * On va récupérer :
     * - l'avis rempli par le formulaire
     * - l'id du film correspondant à l'url
     * - l'utilisateur connecté avec @Aute
     *
     * va mettre dans le model le film qui correspond à l'id passé en paramètre
     * afin de pouvoir l'afficher dans le HTML de mon template
     *
     * Pour recupérer l'id depuis l'url
     * on utilise un argument @PathVariable("idFilm") long idFilm
     */
    @PostMapping("/{idFilm}/avis")
    public String postFilmAvisForm(@Valid Avis avis, BindingResult bindingResult, @PathVariable("idFilm") long idFilm, @AuthenticationPrincipal UtilisateurSpringSecurity utilisateurConnecte){

        // si l'avis n'est pas valide
        if (bindingResult.hasErrors()){
            // je renvoie vers le template de création d'avis
            return "avis";
        }

        /**  sinon **/

        // 1- j'ajoute le membre connecté à l'avis
        avis.setMembre(utilisateurConnecte.getMembre());

        // 2 - je crée l'avis
        filmService.publierAvis(avis, idFilm);

        // 3 - je redirige ensuite sur la page de détail du film
        return "redirect:/films/" + idFilm;
    }


}

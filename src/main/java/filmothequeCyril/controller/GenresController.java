package filmothequeCyril.controller;

import filmothequeCyril.bll.IGenreService;
import filmothequeCyril.bo.Genre;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/genres") // toutes les urls de mon controller vont être accessibles avec le prefixe : /membres
public class GenresController {

    // on crée un attribut de type IGenreService qui va être auto-injecté par Spring
    /* si on veut injecter un service, il faut penser à mettre une instance de classe dans le coontexte Spring
     * (en ajoutant @Service avant le nom de la classe) */
    @Autowired
    private IGenreService genreService;


    @GetMapping
    public String getGenres(Model model){
        // afin d'utiliser th:object="${genre}" dans mon template, je dois initialiser dans le modèle un attribut "genre" de type Genre
        model.addAttribute("genre", new Genre());
        // on ajoute également la liste des genres au modèle (recupérées grâce au service)
        model.addAttribute("listeGenres", genreService.consulterGenres());
        // je retourne le template
        return "genres";
    }

    @PostMapping
    public String postGenres(@Valid Genre genre, BindingResult bindingResult, Model model){
        // si jamais la validation échoue
        if (bindingResult.hasErrors()){
            // on ajoute la liste des genres au modèle (recupérées grâce au service)
            model.addAttribute("listeGenres", genreService.consulterGenres());
            // on renvoie vers le template
            return "genres";
        }
        // sinon, on crée un nouveau genre
        genreService.creerGenre(genre);

        // si tout se passe bien, on redirige vers la page des genres
        return "redirect:/genres";
    }

    /**
     * Il n'y a pas de deleteMapping pour les genres
     * => je suis obligé d'uriliser un POST
     */
    @PostMapping("/{id}/supprimer")
    public String deleteGenre(@PathVariable("id") long id){
        // on supprime le genre
        genreService.supprimerGenreParId(id);

        // si tout se passe bien, on redirige vers la page des genres
        return "redirect:/genres";
    }


}

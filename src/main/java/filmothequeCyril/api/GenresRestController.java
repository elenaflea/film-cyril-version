package filmothequeCyril.api;

import filmothequeCyril.bll.IGenreService;
import filmothequeCyril.bo.Genre;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/genres") // toutes les urls de mon controller vont être accessibles avec le prefixe : /membres
public class GenresRestController {

    // on crée un attribut de type IGenreService qui va être auto-injecté par Spring
    /* si on veut injecter un service, il faut penser à mettre une instance de classe dans le coontexte Spring
     * (en ajoutant @Service avant le nom de la classe) */
    @Autowired
    private IGenreService genreService;


    @GetMapping
    public List<Genre> getGenres(){
        return genreService.consulterGenres();
    }

    @PostMapping
    public void postGenres(@RequestBody @Valid Genre genre){
        // on crée un nouveau genre
        genreService.creerGenre(genre);
    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable("id") long id){
        // on supprime le genre
        genreService.supprimerGenreParId(id);
    }

}

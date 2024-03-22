package filmothequeCyril.api;

import filmothequeCyril.bll.IMembreService;
import filmothequeCyril.bo.Membre;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/membres") // toutes les urls de mon controller vont être accessibles avec le prefixe : /membres
public class MembresRestController {

    // on crée un attribut de type IMembreService qui va être auto-injecté par Spring
    /* si on veut injecter un service, il faut penser à mettre une instance de classe dans le coontexte Spring
     * (en ajoutant @Service avant le nom de la classe) */
    @Autowired
    private IMembreService membreService;


    @GetMapping
    public List<Membre> getMembres(){
        return membreService.consulterMembres();
    }

    @PostMapping
    public void postMembres(@RequestBody @Valid Membre membre){
        // on essaye de créer un nouveau membre
        try{
            membreService.creerMembre(membre);
        }
        // si on a une exception : on envoie une erreur 401
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }


    @DeleteMapping("/{id}")
    public void deleteMembre(@PathVariable("id") long id){
        // on supprime le membre
        membreService.supprimerMembreParId(id);
    }

}


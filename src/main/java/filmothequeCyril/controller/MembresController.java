package filmothequeCyril.controller;

import filmothequeCyril.bll.IMembreService;
import filmothequeCyril.bo.Membre;
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
@RequestMapping("/membres") // toutes les urls de mon controller vont être accessibles avec le prefixe : /membres
public class MembresController {

    // on crée un attribut de type IMembreService qui va être auto-injecté par Spring
    /* si on veut injecter un service, il faut penser à mettre une instance de classe dans le coontexte Spring
     * (en ajoutant @Service avant le nom de la classe) */
    @Autowired
    private IMembreService membreService;


    @GetMapping
    public String getMembres(Model model){
        // afin d'utiliser th:object="${membre}" dans mon template, je dois initialiser dans le modèle un attribut "membre" de type Membre
        model.addAttribute("membre", new Membre());
        // on ajoute également la liste des membres au modèle (recupérées grâce au service)
        model.addAttribute("listeMembres", membreService.consulterMembres());
        // je retourne le template
        return "membres";
    }

    @PostMapping
    public String postMembres(@Valid Membre membre, BindingResult bindingResult, Model model){
        // si jamais la validation échoue
        if (bindingResult.hasErrors()){
            // on ajoute la liste des membres au modèle (recupérées grâce au service)
            model.addAttribute("listeMembres", membreService.consulterMembres());
            // on renvoie vers le template
            return "membres";
        }
        // sinon, on crée un nouveau membre
        try{
            membreService.creerMembre(membre);
        }
        catch(Exception e){
            model.addAttribute("exception", e.getMessage());
            // on ajoute la liste des membres au modèle (recupérées grâce au service)
            model.addAttribute("listeMembres", membreService.consulterMembres());
            return "membres";
        }


        // si tout se passe bien, on redirige vers la page des membres
        return "redirect:/membres";
    }

    /**
     * Il n'y a pas de deleteMapping pour les membres
     * => je suis obligé d'uriliser un POST
     */
    @PostMapping("/{id}/supprimer")
    public String deleteMembre(@PathVariable("id") long id){
        // on supprime le membre
        membreService.supprimerMembreParId(id);

        // si tout se passe bien, on redirige vers la page des membres
        return "redirect:/membres";
    }


}


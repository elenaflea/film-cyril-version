package filmothequeCyril.controller;

import filmothequeCyril.bll.IParticipantService;
import filmothequeCyril.bo.Participant;
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
@RequestMapping("/participants") // toutes les urls de mon controller vont être accessibles avec le prefixe : /membres
public class ParticipantsController {


    // on crée un attribut de type IParticipantService qui va être auto-injecté par Spring
    /* si on veut injecter un service, il faut penser à mettre une instance de classe dans le coontexte Spring
     * (en ajoutant @Service avant le nom de la classe) */
    @Autowired
    private IParticipantService participantService;


    @GetMapping
    public String getParticipants(Model model){
        // afin d'utiliser th:object="${participant}" dans mon template, je dois initialiser dans le modèle un attribut "participant" de type Participant
        model.addAttribute("participant", new Participant());
        // on ajoute également la liste des participants au modèle (recupérées grâce au service)
        model.addAttribute("listeParticipants", participantService.consulterParticipants());
        // je retourne le template
        return "participants";
    }

    @PostMapping
    public String postParticipants(@Valid Participant participant, BindingResult bindingResult, Model model){
        // si jamais la validation échoue
        if (bindingResult.hasErrors()){
            // on ajoute la liste des participants au modèle (recupérées grâce au service)
            model.addAttribute("listeParticipants", participantService.consulterParticipants());
            // on renvoie vers le template
            return "participants";
        }
        // sinon, on crée un nouveau participant
        participantService.creerParticipant(participant);

        // si tout se passe bien, on redirige vers la page des participants
        return "redirect:/participants";
    }

    /**
     * Il n'y a pas de deleteMapping pour les participants
     * => je suis obligé d'uriliser un POST
     */
    @PostMapping("/{id}/supprimer")
    public String deleteParticipant(@PathVariable("id") long id){
        // on supprime le participant
        participantService.supprimerParticipantParId(id);

        // si tout se passe bien, on redirige vers la page des participants
        return "redirect:/participants";
    }


}

package filmothequeCyril.api;

import filmothequeCyril.bll.IParticipantService;
import filmothequeCyril.bo.Participant;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/participants") // toutes les urls de mon controller vont être accessibles avec le prefixe : /membres
public class ParticipantsRestController {


    // on crée un attribut de type IParticipantService qui va être auto-injecté par Spring
    /* si on veut injecter un service, il faut penser à mettre une instance de classe dans le coontexte Spring
     * (en ajoutant @Service avant le nom de la classe) */
    @Autowired
    private IParticipantService participantService;


    @GetMapping
    public List<Participant> getParticipants(){
        return participantService.consulterParticipants();
    }

    @PostMapping
    public void postParticipants(@RequestBody @Valid Participant participant){
        // on crée un nouveau participant
        participantService.creerParticipant(participant);
    }


    @DeleteMapping("/{id}")
    public void deleteParticipant(@PathVariable("id") long id){
        // on supprime le participant
        participantService.supprimerParticipantParId(id);
    }


}

package filmothequeCyril.bll.jpa;

import filmothequeCyril.bll.IParticipantService;
import filmothequeCyril.bo.Participant;
import filmothequeCyril.dal.ParticipantRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implémentation de l'interface IParticipantService
 * qui va être injectée dans le controller ParticipantController
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
public class ParticipantServiceJpaImpl implements IParticipantService {

    // @Autowired : pas besoin car PasswordEncoder est injecté dans le constructeur
    private ParticipantRepository participantRepository;

    /*
    * On initialise la liste des participants
     */
    public ParticipantServiceJpaImpl(ParticipantRepository participantRepository) {

        this.participantRepository = participantRepository;

        /**
         * A la création du service, on vérifie si un participnt existe dans l'application.
         * Si aucun n'existe, on créer des participants de base
         */
        if (participantRepository.findAll().size() == 0){
            creerParticipant(new Participant("Spielberg", "Steven"));
            creerParticipant(new Participant("Cronenberg", "David"));
            creerParticipant(new Participant("Boon", "Dany"));
            creerParticipant(new Participant("Attenborough", "Richard"));
            creerParticipant(new Participant("Davis", "Geena"));
            creerParticipant(new Participant("Rylance", "Mark"));
            creerParticipant(new Participant("Merad", "Kad"));
        }
    }

    @Override
    public List<Participant> consulterParticipants() {

        return participantRepository.findAll();
    }

    @Override
    public Participant consulterParticipantParId(long id) {
       return participantRepository.findById(id).orElse(null);
    }

    @Override
    public void supprimerParticipantParId(long id) {
       participantRepository.deleteById(id);
    }

    @Override
    public void creerParticipant(Participant participant) {
       participantRepository.save(participant);
    }
}

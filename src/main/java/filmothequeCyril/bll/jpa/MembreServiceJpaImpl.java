package filmothequeCyril.bll.jpa;

import filmothequeCyril.bll.IMembreService;
import filmothequeCyril.bo.Membre;
import filmothequeCyril.dal.MembreRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implémentation de l'interface IMembreService
 * qui va être injectée dans le controller MembreController
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
public class MembreServiceJpaImpl implements IMembreService {

    // @Autowired : pas besoin car PasswordEncoder est injecté dans le constructeur
    private MembreRepository membreRepository;
    // @Autowired : pas besoin car PasswordEncoder est injecté dans le constructeur
    private PasswordEncoder passwordEncoder;

    /*
    Dans le constructeur
    * On initialise la liste des membres
    on injecte PasswordEncoder passwordEncoder
     */
    public MembreServiceJpaImpl(PasswordEncoder passwordEncoder, MembreRepository membreRepository) throws Exception{
        this.passwordEncoder = passwordEncoder;
        this.membreRepository = membreRepository;

        /**
         * A la création du service, on vérifie si un membre existe dans l'application.
         * Si aucun n'existe, on créer au moins un utilisateur admin pour pouvoir se connecter
         */
        if (membreRepository.findAll().size() == 0){
            creerMembre(new Membre("Mace", "Cyril", "admin","admin", true));
        }

    }

    @Override
    public List<Membre> consulterMembres() {
        return membreRepository.findAll();
    }

    @Override
    public Membre consulterMembreParId(long id) {
       return membreRepository.findById(id).orElse(null); // si on utilise .orElse(null); à la place de .get(), ca ne plante pas si l'utilisateur n'existe pas mais ca renvoie null
    }

    @Override
    public Membre consulterMembreParPseudo(String pseudo) {
        // je vais chercher le membre qui correspond au pseudo
        for (Membre membre : consulterMembres()) {
            if (membre.getPseudo().equals(pseudo)) {
                // lorsque je trouve le membre, je le renvoie
                return membre;
            }
        }
        // si le membre n'est pas trouvé, je renvoie null
        return null;
    }

    @Override
    public void supprimerMembreParId(long id) {
        membreRepository.deleteById(id);
    }

    @Override
    public void creerMembre(Membre membre) throws Exception {

        // VALIDATION : je vais vérifier qu'un membre avec le même pseudo n'existe pas déjà en base
        // TODO : lancer un autre type d'exception plus specifique qui sera reprise par le template
        if (consulterMembreParPseudo(membre.getPseudo()) != null){
            throw new Exception("Erreur : Le pseudo est déjà utilisé par quelqu'un d'autre");
        }

        // NE PAS OUBLIER de mettre à jour le mot de passe en l'encodant
        membre.setMotDePasse(passwordEncoder.encode(membre.getMotDePasse()));

        // on sauvegarde le membre en base de donnée
        membreRepository.save(membre);
    }
}

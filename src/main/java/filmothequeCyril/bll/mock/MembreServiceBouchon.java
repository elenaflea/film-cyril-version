package filmothequeCyril.bll.mock;

import filmothequeCyril.bll.IMembreService;
import filmothequeCyril.bo.Membre;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface IMembreService
 * qui va être injectée dans le controller MembreController
 *
 * @Service permet de rendre une instance de cette classe
 * disponible dans le contexte Spring (obligatoire afin d'être injectée)
 *
 * @Profile("dev")
 * Specifie que cette classe est activée UNIQUEMENT lorsqu'on a le profil "dev"
 * dans application.properties
 */
@Service
@Profile("dev")
public class MembreServiceBouchon implements IMembreService {

    /*
    * Dans l'implémentation "bouchon"
    * on gère les membres dans une liste
     */
    private  List<Membre> listeMembres = new ArrayList<>();

    // @Autowired : pas besoin car PasswordEncoder est injecté dans le constructeur
    private PasswordEncoder passwordEncoder;

    /*
    Dans le constructeur
    * On initialise la liste des membres
    on injecte PasswordEncoder passwordEncoder
     */
    public MembreServiceBouchon(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        creerMembre(new Membre("Mace", "Cyril", "cyril","Pa$$w0rd", true));
        creerMembre(new Membre("Martin", "Aurélie", "membre", "Pa$$w0rd", false));

    }



    // on gère un compteur pour l'id (qui simule l'auto-increment de la base de donnée)
    private int idCourant = 1;

    @Override
    public List<Membre> consulterMembres() {
        return listeMembres;
    }

    @Override
    public Membre consulterMembreParId(long id) {
        // on recherche dans la liste le membre d'id "id"
        for (Membre membre : listeMembres) {
            if (membre.getId() == id){
                return membre;
            }
        }
        // on retourne null si pas trouvé
        return null;
    }

    @Override
    public Membre consulterMembreParPseudo(String pseudo) {
        // je vais chercher le membre qui correspond au pseudo
        for (Membre membre : listeMembres) {
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
        // on recherche dans la liste l'index correspondant au membre d'id "id"
        for (int index = 0; index < listeMembres.size(); index++) {
            if (listeMembres.get(index).getId() == id){
                listeMembres.remove(index);
            }
        }
    }

    @Override
    public void creerMembre(Membre membre) {
        // on rajoute l'id au membre (tout en l'incrémentant)
        membre.setId(idCourant++);

        // NE PAS OUBLIER de mettre à jour le mot de passe en l'encodant
        membre.setMotDePasse(passwordEncoder.encode(membre.getMotDePasse()));

        // on rajoute le membre à la liste
        listeMembres.add(membre);
    }
}

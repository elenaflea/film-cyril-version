package filmothequeCyril.security;

import filmothequeCyril.bll.IMembreService;
import filmothequeCyril.bo.Membre;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Je crée un service qui implémente l'interface UserDetailsService
 * Je vais devoir implémenter une méthode loadUserByUsername
 * qui spécifie "comment je vais chercher les utilisateurs à partir d'un pseudo" ?
 */
@Service
public class ServiceGestionUtilisateursSpringSecurity implements UserDetailsService {

    private IMembreService membreService;

    /**
     * A la place de @Autowired, on utilise une injection par constructeur
     * Ca permet de s'assurer de l'ordre d'instantitation des services (MembreService doit être instancié AVANT ServiceGestionUtilisateursSpringSecurity)
     */
    public ServiceGestionUtilisateursSpringSecurity(IMembreService membreService) {
        this.membreService = membreService;
    }

    /**
     * LoadByUserName
     * Spécifie où je vais chercher mon utilisateur Spring Security à partir d'un membre
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        Membre membre = membreService.consulterMembreParPseudo(username);

        // si jamais on a trouvé le membre
        if (membre != null){
            // on renvoie l'utilisateur Spring Security correspondant
            return new UtilisateurSpringSecurity(membre);
        }
        // sinon, on lève une exception (utilisateur non trouvé) qui sera affichée dans le formulaire
        throw new UsernameNotFoundException(username);
    }
}
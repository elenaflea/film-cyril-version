package filmothequeCyril.security;

import filmothequeCyril.bo.Membre;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Class "wrapper" qui respecte le format imposé par Spring Security (implémente l'interface UserDetails)
 * Et qui englobbe un Membre
 */
@Data
@AllArgsConstructor
public class UtilisateurSpringSecurity implements UserDetails {

    /**
     * Chaque utilisateur Spring Security correspond à un membre
     */
    private Membre membre;

    /**
     * Pour qu'on puiss utiliser dans Spring Security ma classe UtilisateurSpringSecurity
     * Je vais devoir specifier :
     */

    /*
     * Comment est-ce qu'on recupère la liste des permissions (Authority) liées à l'utilisateur ?
     * Permission qui a le nommage "ROLE_xxx" correspond au rôle admin
     * => à partir du mot de passe du membre
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // si jamais le membre associé est admin
        if (membre.isAdmin()) {
            // on retourne les rôles : admin, user
            return List.of(new SimpleGrantedAuthority("ROLE_admin"), new SimpleGrantedAuthority("ROLE_user"));
        }
        // sinon : on retourne juste le rôle : user
        return List.of(new SimpleGrantedAuthority("ROLE_user"));
    }

    /*
    * Comment est-ce qu'on recupère le mot de passe ?
    * => à partir du mot de passe du membre
     */
    @Override
    public String getPassword() {
        return membre.getMotDePasse();
    }

    /*
     * Comment est-ce qu'on recupère le username ?
     * => à partir du pseudo du membre
     */
    @Override
    public String getUsername() {
        return membre.getPseudo();
    }

    /*
     * Comment est-ce qu'on sait si le compte n'est pas expiré ?
     * => toujours vrai
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*
     * Comment est-ce qu'on sait si le compte n'est pas bloqué ?
     * => toujours vrai
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*
     * Comment est-ce qu'on sait si le mot de passe n'est pas expiré ?
     * => toujours vrai
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*
     * Comment est-ce qu'on sait si le compte est activé ?
     * => toujours vrai
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}

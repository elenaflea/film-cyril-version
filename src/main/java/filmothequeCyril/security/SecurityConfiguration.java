package filmothequeCyril.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

@Configuration // sert à definir des @Bean
@EnableWebSecurity
public class SecurityConfiguration {
    /**
     * En définissant une méthode annotée @Bean qui renvoie une instance de SecurityFilterChain
     * On va mettre dans le contexte Spring un bean de type SecurityFilterChain
     * <p>
     * => Spring Security va aller chercher dans le contexte un bean de type SecurityFilterChain
     * pour charger sa configuration d'autorisations (qui a le droit d'accéder à quoi)
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // on ignore la vérification csrf sur les requêtes d'API (pas de risque car pas de Cookies)
        http.csrf( (csrf) -> csrf.ignoringRequestMatchers("/api/**"));

        // on va autoriser les requêtes HTTP qui ont les caracteristiques suivantes :
        http.authorizeHttpRequests((authorize) -> authorize
                    /**
                     * on se base sur des requestMatcher pour déterminer quelles urls sont concernées par la configuration
                     * on peut utiliser plusieurs formats :
                     *  - url simple : .requestMatchers("/pageConnecte")
                     *  - url multiple : .requestMatchers("/page1", "page2", "/menu1/page3")
                     *  - wildcard (*) : .requestMatchers("/prive/**") // toutes les urls qui commencent par prive
                     *  - possibilité d'utiliser des expresssions régulières avec RegexRequestMatcher : https://www.freecodecamp.org/news/practical-regex-guide-with-real-life-examples/
                     */

                    // pour accéder à la page d'ajout d'avis, on doit être connnecté (.authenticated())
                    // on utilise une expression régulière pour ca : regexMatcher("/films/.*/avis")
                    .requestMatchers(regexMatcher("/films/.*/avis")).authenticated()
                    // pour accéder aux pages de gestion de Genres, Membres, de Participants et de Films : il faut avoir le rôle admin (.hasRole("admin"), implique qu'on doit être également connnecté)
                   // .requestMatchers("/genres", "/membres", "/participants","/films/creer").hasRole("admin")
                    // pour toutes les autres requêtes ("/**") , on laisse passer (.permitAll())
                    .requestMatchers("/**").permitAll()
                )
                // on fait une authentification basique (utilisateur / mot de passe)
                .httpBasic(Customizer.withDefaults())
                // avec le formulaire par défaut proposé par Spring
                .formLogin(Customizer.withDefaults());
        return http.build();
    }


    @Bean // on définit un bean pour l'utilitaire d'encryption de mot de passe
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** On a pas besoin du bean car on utilise ServiceGestionUtilisateursSpringSecurity
     * Lorsque Spring voit un bean de type InMemoryUserDetailsManager dans le contexte Spring
     * Il sait qu'il doit l'utiliser pour aller chercher les utilisateurs

    @Bean // on définit un bean pour la gestion des utilisateurs en mémoire
    public InMemoryUserDetailsManager userDetailsService() {

        // On gère une liste d'utilisateur Spring Security (interface UserDetails)
        List<UserDetails> userDetailsList = new ArrayList<>();
        // Cette liste on la remplit avec 2 utilisateurs :
        // un utilisateur avec le username : "membre", mot de passe "membre123", rôle : "user"
        userDetailsList.add(
                User.withUsername("membre")
                .password(passwordEncoder().encode("membre123"))
                .roles("user").build());
        // un utilisateur avec le username : "admin", mot de passe "admin123", rôles : "admin", "user"
        userDetailsList.add(User.withUsername("admin").password(passwordEncoder().encode("admin123"))
                .roles("admin", "user").build());
        return new InMemoryUserDetailsManager(userDetailsList);
    }
     */
}
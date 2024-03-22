package filmothequeCyril.dal;

import filmothequeCyril.bo.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPARepository est une interface génerique qui prends 2 paramètres :
 * - le type de l'entité qu'on veut gérer, ici : Participant
 * - le type de l'attribut @Id (clé primaire) de cette entité , ici : Long
 */
// Spring va AUTOMATIQUEMENT créer une classe qui implémente cette interface avec les méthodes save()/findAll()/etc...
// et la rendre disponible dans le contexte Spring comme un bean
public interface ParticipantRepository extends JpaRepository<Participant,Long> {}

package filmothequeCyril.bll;

import filmothequeCyril.bo.Participant;

import java.util.List;

/**
 * Interface
 * Ca n'est pas instancié
 * Donc, aucun interêt de mettre @Service dedans
 */
public interface IParticipantService {

    List<Participant> consulterParticipants();

    Participant consulterParticipantParId(long id);

    void supprimerParticipantParId(long id);

    void creerParticipant(Participant genre);
}

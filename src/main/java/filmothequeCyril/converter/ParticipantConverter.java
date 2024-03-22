package filmothequeCyril.converter;

import filmothequeCyril.bll.IParticipantService;
import filmothequeCyril.bo.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * On définit une classe ParticipantConverter
 * Qui implémente org.springframework.core.convert.converter.Converter
 * et qui spécifie
 * comment Spring va pouvoir générer une instance de Participant (format Java)
 * à partir d'un paramètre au format texte envoyé dans la requête HTTP
 */

@Component // attention à bien mettre le converter dans le contexte Spring
public class ParticipantConverter implements Converter<String, Participant> {

    @Autowired
    private IParticipantService participantService;

    /**
     * Comment est-ce qu'on passe d'un id de partiicpant au format texte
     * à une instance de participant?
     */
    @Override
    public Participant convert(String idAuFormatTexte) {
        // on se sert du service pour recupérer le genre correspondant à l'id passé en paramètre
        return participantService.consulterParticipantParId(Long.parseLong(idAuFormatTexte));
    }
}

package filmothequeCyril.dto;

import lombok.Data;

/**
 * Cette classe sert à regrouper les paramètres de ma recherche
 */
@Data
public class SearchParam {
    private String search;
    private String genre;
    private Integer dureeMin;
    private Integer dureeMax;
    private Integer anneeMin;
    private Integer anneeMax;
}

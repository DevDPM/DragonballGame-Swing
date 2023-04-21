package nl.pokemon.game.model.SQMs;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.util.TilesetImageContainer;

public class ConvertToSQM {

    public static BaseSQM getSQM(AreaType areaType, int sqmId) {
        return TilesetImageContainer.getSQMByIntAndArea(areaType, sqmId);
    }
}

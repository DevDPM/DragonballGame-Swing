package nl.pokemon.game.core.model.Tiles;

import nl.pokemon.game.client.enums.AreaType;
import nl.pokemon.game.bootstrap.TilesetImageContainer;

public class ConvertToSQM {

    public static BaseTile getSQM(AreaType areaType, int sqmId) {
        return TilesetImageContainer.getSQMByIntAndArea(areaType, sqmId);
    }
}

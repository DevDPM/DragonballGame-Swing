package nl.pokemon.game.core.model.Tiles;

import nl.pokemon.game.client.enums.AreaType;
import nl.pokemon.game.core.model.Elevatable;

public class FloorDownTile extends BaseTile implements Elevatable {


    @Override
    public boolean isNotWalkable() {
        return false;
    }

    @Override
    public AreaType getAreaType() {
        return AreaType.MAP;
    }

    @Override
    public int elevatingValue() {
        return -1;
    }
}

package nl.pokemon.game.model.core.model.tiles;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.core.model.Elevatable;

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

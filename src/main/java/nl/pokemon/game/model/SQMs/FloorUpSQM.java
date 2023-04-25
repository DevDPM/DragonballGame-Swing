package nl.pokemon.game.model.SQMs;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.Elevatable;

public class FloorUpSQM extends BaseSQM implements Elevatable {

    @Override
    public boolean isNotWalkable() {
        return false;
    }

    @Override
    public AreaType getAreaType() {
        return AreaType.MAP;
    }

    @Override
    public int incrementElevationNumber() {
        return 1;
    }
}

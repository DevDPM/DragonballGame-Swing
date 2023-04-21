package nl.pokemon.game.model.SQMs;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.Elevatable;

public class FloorDownSQM extends BaseSQM implements Elevatable {

    @Override
    public int getObjectNumber() {
        return 0;
    }

    @Override
    public boolean isNotWalkable() {
        return false;
    }

    @Override
    public AreaType getAreaType() {
        return AreaType.MAP;
    }

    @Override
    public int getElevateZ() {
        return -1;
    }
}

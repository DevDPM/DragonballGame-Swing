package nl.pokemon.game.model.SQMs;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Classify;
import nl.pokemon.game.model.Elevatable;

public class FloorUpSQM extends BaseSQM implements Elevatable {

    @Override
    public int getObjectNumber() {
        return 0;
    }

    @Override
    public boolean isNotWalkable() {
        return false;
    }

    @Override
    public Classify getClassify() {
        return Classify.OBSTACLE;
    }

    @Override
    public AreaType getAreaType() {
        return AreaType.MAP;
    }

    @Override
    public int getElevateZ() {
        return 1;
    }
}

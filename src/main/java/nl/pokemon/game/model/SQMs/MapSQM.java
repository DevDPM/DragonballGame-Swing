package nl.pokemon.game.model.SQMs;

import nl.pokemon.game.enums.Classify;
import nl.pokemon.game.enums.AreaType;

public class MapSQM extends BaseSQM {

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
}

package nl.pokemon.game.model.SQMs;

import nl.pokemon.game.enums.AreaType;

public class LowTerrainSQM extends BaseSQM {

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
        return AreaType.TERRAIN;
    }
}

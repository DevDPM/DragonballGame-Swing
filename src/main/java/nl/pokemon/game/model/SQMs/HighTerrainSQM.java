package nl.pokemon.game.model.SQMs;

import nl.pokemon.game.enums.AreaType;

public class HighTerrainSQM extends BaseSQM {

    @Override
    public boolean isNotWalkable() {
        return true;
    }

    @Override
    public AreaType getAreaType() {
        return AreaType.TERRAIN;
    }
}

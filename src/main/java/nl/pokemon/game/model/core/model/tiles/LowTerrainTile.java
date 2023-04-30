package nl.pokemon.game.model.core.model.tiles;

import nl.pokemon.game.enums.AreaType;

public class LowTerrainTile extends BaseTile {

    @Override
    public boolean isNotWalkable() {
        return false;
    }

    @Override
    public AreaType getAreaType() {
        return AreaType.HIGHER_TERRAIN;
    }
}

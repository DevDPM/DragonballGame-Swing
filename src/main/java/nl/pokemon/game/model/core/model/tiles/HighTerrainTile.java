package nl.pokemon.game.model.core.model.tiles;

import nl.pokemon.game.enums.AreaType;

public class HighTerrainTile extends BaseTile {

    @Override
    public boolean isNotWalkable() {
        return true;
    }

    @Override
    public AreaType getAreaType() {
        return AreaType.HIGHER_TERRAIN;
    }
}

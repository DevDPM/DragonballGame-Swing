package nl.pokemon.game.core.model.Tiles;

import nl.pokemon.game.client.enums.AreaType;

public class MapTile extends BaseTile {

    @Override
    public boolean isNotWalkable() {
        return false;
    }

    @Override
    public AreaType getAreaType() {
        return AreaType.MAP;
    }
}
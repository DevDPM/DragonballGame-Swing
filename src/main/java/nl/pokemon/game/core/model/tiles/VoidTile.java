package nl.pokemon.game.core.model.tiles;

import nl.pokemon.game.client.enums.AreaType;

public class VoidTile extends BaseTile {

    public VoidTile() {
        this.setImageIcon(null);
    }

    @Override
    public boolean isNotWalkable() {
        return true;
    }

    @Override
    public AreaType getAreaType() {
        return AreaType.MAP;
    }
}

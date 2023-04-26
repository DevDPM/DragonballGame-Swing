package nl.pokemon.game.core.model.Tiles;

import nl.pokemon.game.core.model.Grabbable;

public class ItemTile extends LowTerrainTile implements Grabbable {

    @Override
    public int receivePoints() {
        return 1;
    }
}

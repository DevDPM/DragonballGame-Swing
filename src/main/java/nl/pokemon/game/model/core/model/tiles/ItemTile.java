package nl.pokemon.game.model.core.model.tiles;

import nl.pokemon.game.model.core.model.Grabbable;

public class ItemTile extends LowTerrainTile implements Grabbable {

    @Override
    public int receivePoints() {
        return 1;
    }
}

package nl.pokemon.game.model.SQMs;

import nl.pokemon.game.model.Grabbable;

public class ItemSQM extends LowTerrainSQM implements Grabbable {

    @Override
    public int receivePoints() {
        return 1;
    }
}

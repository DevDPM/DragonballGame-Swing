package nl.pokemon.game.model.SQMs;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.players.VoidPlayerSQM;

public class SQMFactory {

    public static BaseSQM getSQMBySurface(AreaType area) {
        return switch (area) {
            case MAP -> new MapSQM();
            case PLAYER_BOTTOM -> new VoidPlayerSQM();
            case TERRAIN -> new HighTerrainSQM();
            case PLAYER_TOP -> new VoidPlayerSQM();
        };
    }
}

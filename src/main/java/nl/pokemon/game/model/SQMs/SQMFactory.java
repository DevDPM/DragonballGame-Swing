package nl.pokemon.game.model.SQMs;

import nl.pokemon.game.enums.AreaType;

public class SQMFactory {

    public static BaseSQM getSQMBySurface(AreaType area) {
        return switch (area) {
            case MAP -> new MapSQM();
            case PLAYER_BOTTOM -> new TerrainSQM();
            case TERRAIN -> new TerrainSQM();
            case PLAYER_TOP -> new TerrainSQM();
        };
    }
}

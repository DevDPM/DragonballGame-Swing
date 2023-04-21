package nl.pokemon.game.model.SQMs;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.players.VoidPlayerSQM;
import nl.pokemon.player.model.BasePlayer;

public class SQMFactory {

    public static BaseSQM getSQMBySurface(AreaType area) {
        return switch (area) {
            case MAP -> new MapSQM();
            case PLAYER_BOTTOM -> new VoidPlayerSQM();
            case TERRAIN -> new TerrainSQM();
            case PLAYER_TOP -> new VoidPlayerSQM();
        };
    }
}

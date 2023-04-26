package nl.pokemon.game.core.model.Tiles;

import nl.pokemon.game.core.model.players.VoidPlayerTile;
import nl.pokemon.game.client.enums.AreaType;

public class TileFactory {

    public static BaseTile getTileByArea(AreaType area) {
        return switch (area) {
            case MAP -> new MapTile();
            case LOWER_TERRAIN -> new LowTerrainTile();
            case PLAYER_BOTTOM -> new VoidPlayerTile();
            case HIGHER_TERRAIN -> new HighTerrainTile();
            case PLAYER_TOP -> new VoidPlayerTile();
        };
    }
}

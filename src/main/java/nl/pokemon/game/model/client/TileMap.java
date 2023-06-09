package nl.pokemon.game.model.client;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.core.model.tiles.BaseTile;
import nl.pokemon.game.model.core.model.tiles.TileFactory;

public final class TileMap {

    public static final int MAX_X = 21;
    public static final int MAX_Y = 21;
    private BaseTile[][] tileMap;

    public TileMap(AreaType area) {
        this.tileMap = new BaseTile[MAX_Y][MAX_X];
        for (int y = 0; y < tileMap[0].length; y++) {
            for (int x = 0; x < tileMap.length; x++) {
                BaseTile tile = TileFactory.getTileByArea(area);
                tile.setIndexX(x);
                tile.setIndexY(y);
                tile.updateSQM();
                tileMap[y][x] = tile;
            }
        }
    }

    public BaseTile[][] getTileMap() {
        return tileMap;
    }
}

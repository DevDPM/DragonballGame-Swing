package nl.pokemon.game.client.model.output;

import nl.pokemon.game.client.enums.AreaType;

import java.util.HashMap;
import java.util.Map;

public class FullTileMap {

    public static final int START_Z = -1;
    public static final int MAX_Z = 4;

    private Map<Integer, Map<AreaType, TileMap>> viewMap = new HashMap<>();

    public FullTileMap() {
        for (int z = START_Z; z <= MAX_Z; z++) {
            viewMap.putAll(createElevationLayer(z));
        }
    }

    private Map<Integer, Map<AreaType, TileMap>> createElevationLayer(int z) {
        Map<Integer, Map<AreaType, TileMap>> elevationLayer = new HashMap<>();
        elevationLayer.put(z, createAreaLayer());
        return elevationLayer;
    }

    private Map<AreaType, TileMap> createAreaLayer() {
        Map<AreaType, TileMap> areaLayer = new HashMap<>();

        for (AreaType area : AreaType.values()) {
            areaLayer.put(area, new TileMap(area));
        }
        return areaLayer;
    }

    public Map<Integer, Map<AreaType, TileMap>> getFullTileMap() {
        return viewMap;
    }
}

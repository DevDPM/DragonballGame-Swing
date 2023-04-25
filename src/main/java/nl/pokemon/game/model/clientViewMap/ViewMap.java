package nl.pokemon.game.model.clientViewMap;

import nl.pokemon.game.enums.AreaType;

import java.util.HashMap;
import java.util.Map;

public class ViewMap {

    public static final int START_Z = -1;
    public static final int MAX_Z = 4;

    private Map<Integer, Map<AreaType, GridMap>> viewMap = new HashMap<>();

    public ViewMap() {
        for (int z = START_Z; z <= MAX_Z; z++) {
            viewMap.putAll(createElevationLayer(z));
        }
    }

    private Map<Integer, Map<AreaType, GridMap>> createElevationLayer(int z) {
        Map<Integer, Map<AreaType, GridMap>> elevationLayer = new HashMap<>();
        elevationLayer.put(z, createAreaLayer());
        return elevationLayer;
    }

    private Map<AreaType, GridMap> createAreaLayer() {
        Map<AreaType, GridMap> areaLayer = new HashMap<>();

        for (AreaType area : AreaType.values()) {
            areaLayer.put(area, new GridMap(area));
        }
        return areaLayer;
    }

    public Map<Integer, Map<AreaType, GridMap>> getViewMap() {
        return viewMap;
    }
}

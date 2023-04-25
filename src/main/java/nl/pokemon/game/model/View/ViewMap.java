package nl.pokemon.game.model.View;

import nl.pokemon.game.enums.AreaType;

import java.util.HashMap;
import java.util.Map;

public class ViewMap {

    public static final int START_Z = -1;
    public static final int MAX_Z = 4;

    private Map<Integer, Map<AreaType, GridMap>> viewMap = new HashMap<>();

    public ViewMap() {
        for (int z = START_Z; z <= MAX_Z; z++) {
            viewMap.putAll(createGridLayerFloor(z));
        }
    }

    private Map<Integer, Map<AreaType, GridMap>> createGridLayerFloor(int z) {
        Map<Integer, Map<AreaType, GridMap>> gridLayerFloor = new HashMap<>();
        gridLayerFloor.put(z, createGridLayer());
        return gridLayerFloor;
    }

    private Map<AreaType, GridMap> createGridLayer() {
        Map<AreaType, GridMap> gridLayer = new HashMap<>();

        for (AreaType area : AreaType.values()) {
            gridLayer.put(area, new GridMap(area));
        }
        return gridLayer;
    }

    public Map<Integer, Map<AreaType, GridMap>> getViewMap() {
        return viewMap;
    }
}

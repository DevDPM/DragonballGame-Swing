package nl.pokemon.mapGenerator.model.View;

import nl.pokemon.game.enums.AreaType;

import java.util.HashMap;
import java.util.Map;

public class MG_ViewMap {

    public static final int START_Z = -1;
    public static final int MAX_Z = 4;

    private Map<Integer, Map<AreaType, MG_GridMap>> viewMap = new HashMap<>();

    public MG_ViewMap() {
        for (int z = START_Z; z <= MAX_Z; z++) {
            viewMap.putAll(createGridLayerFloor(z));
        }
    }

    private Map<Integer, Map<AreaType, MG_GridMap>> createGridLayerFloor(int z) {
        Map<Integer, Map<AreaType, MG_GridMap>> gridLayerFloor = new HashMap<>();
        gridLayerFloor.put(z, createGridLayer());
        return gridLayerFloor;
    }

    private Map<AreaType, MG_GridMap> createGridLayer() {
        Map<AreaType, MG_GridMap> gridLayer = new HashMap<>();

        for (AreaType area : AreaType.values()) {
            gridLayer.put(area, new MG_GridMap(area));
        }
        return gridLayer;
    }

    public Map<Integer, Map<AreaType, MG_GridMap>> getViewMap() {
        return viewMap;
    }
}

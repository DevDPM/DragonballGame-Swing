package nl.pokemon.mapGenerator.util;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.mapGenerator.model.SQMs.DevTool_BaseTile;
import nl.pokemon.mapGenerator.model.View.MG_GridMap;

import java.util.HashMap;
import java.util.Map;

public class MapConverter {

    public static Map<Integer, Map<AreaType, int[][]>> convertBaseSQMMapToIntMap(Map<Integer, Map<AreaType, MG_GridMap>> newMap) {
        Map<Integer, Map<AreaType, int[][]>> newIntMap = new HashMap<>();

        for (Map.Entry<Integer, Map<AreaType, MG_GridMap>> integerMapEntry : newMap.entrySet()) {
            // per elevation

            Map<AreaType, int[][]> newArea = new HashMap<>();

            for (Map.Entry<AreaType, MG_GridMap> areaTypeMGBaseSQMEntry : integerMapEntry.getValue().entrySet()) {
                // per areaType

                int[][] gridInt = new int[areaTypeMGBaseSQMEntry.getValue().getGridMap()[0].length][areaTypeMGBaseSQMEntry.getValue().getGridMap().length];

                for (int y = 0; y < areaTypeMGBaseSQMEntry.getValue().getGridMap()[0].length; y++) {
                    for (int x = 0; x < areaTypeMGBaseSQMEntry.getValue().getGridMap().length; x++) {
                        DevTool_BaseTile[][] baseSQMGrid = areaTypeMGBaseSQMEntry.getValue().getGridMap();
                        gridInt[y][x] = baseSQMGrid[y][x].getSqmId();
                    }
                }
                newArea.put(areaTypeMGBaseSQMEntry.getKey(), gridInt);
            }
            newIntMap.put(integerMapEntry.getKey(), newArea);
        }
        return newIntMap;
    }
}

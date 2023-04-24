package nl.pokemon.game.util;

import nl.pokemon.game.enums.AreaType;

import java.util.*;

public class FullMap {

    private static final Map<Integer, Map<AreaType, int[][]>> viewMap = new HashMap<>();

    public static Map<Integer, Map<AreaType, int[][]>> getViewMap() {
        return viewMap;
    }

    public static void bootstrapFullMap() {
        viewMap.putAll(JsonDeserialize.deserializeJsonFromFullMap(viewMap));
    }

    public static boolean setNewValueToPosition(AreaType areaType, int x, int y, int z, int newValue) {
        Map<AreaType, int[][]> firstMap = viewMap.get(z);
        int[][] intMap = firstMap.get(areaType);
        intMap[y][x] = newValue;

        if (intMap[y][x] == newValue)
            return true;

        return false;
    }


    public static int fullMapWidth() {
        return viewMap.get(0).get(AreaType.MAP)[0].length;
    }

    public static int fullMapHeight() {
        return viewMap.get(0).get(AreaType.MAP).length;
    }
}

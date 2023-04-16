package nl.pokemon.game.util;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.View.ViewMap;

import java.util.HashMap;
import java.util.Map;

public class FULL_MAP {

    private static final Map<Integer, Map<AreaType, int[][]>> viewMap = new HashMap<>();

    public static Map<Integer, Map<AreaType, int[][]>> getViewMap() {
        createMap();
        return viewMap;
    }

    private static void createMap() {
        for (int z = ViewMap.START_Z; z <= ViewMap.MAX_Z; z++) {
            Map<AreaType, int[][]> surfaceMap = new HashMap<>();
            for (AreaType area : AreaType.values()) {
                surfaceMap.put(area, getMapByAreaAndElevation(area, z));
            }
            viewMap.put(z, surfaceMap);
        }
    }

    private static int[][] getMapByAreaAndElevation(AreaType areaType, int z) {
        if (areaType.equals(AreaType.MAP) && z == -1) {
            return new int[][]{
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            };
        }
        if (areaType.equals(AreaType.TERRAIN) && z == -1) {
            return new int[][]{
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 3, 0, 0, 0, 0, 0, 3, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 3, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 3, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            };
        }
        if (areaType.equals(AreaType.MAP) && z == 0) {
            return new int[][]{
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            };
        }
        if (areaType.equals(AreaType.TERRAIN) && z == 0) {
            return new int[][]{
                    {0, 0, 0, 0, 0, 0, 0, 0, 4, 0},
                    {0, 0, 4, 0, 0, 0, 0, 0, 4, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 4, 0},
                    {0, 0, 0, 0, 0, 3, 0, 0, 4, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 4, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 4, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 4, 0},
                    {0, 0, 0, 0, 0, 0, 0, 3, 4, 0},
                    {0, 0, 4, 0, 0, 0, 0, 0, 4, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 4, 0},
            };
        }
        if (areaType.equals(AreaType.MAP) && z == 1) {
            return new int[][]{
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            };
        }
        if (areaType.equals(AreaType.TERRAIN) && z == 1) {
            return new int[][]{
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 4, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 4, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            };
        }
        return new int[][] {{}};
    }

    public static int fullMapWidth() {
        return viewMap.get(0).get(AreaType.MAP)[0].length;
    }

    public static int fullMapHeight() {
        return viewMap.get(0).get(AreaType.MAP).length;
    }

}

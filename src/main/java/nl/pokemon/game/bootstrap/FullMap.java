package nl.pokemon.game.bootstrap;

import nl.pokemon.game.domain.User;
import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.core.model.MapCoordination;
import nl.pokemon.game.model.core.model.tiles.BaseTile;

import java.util.*;

public class FullMap {

    private static final Map<Integer, Map<AreaType, int[][]>> viewMap = new HashMap<>();

    public static Map<Integer, Map<AreaType, int[][]>> getViewMap() {
        return viewMap;
    }

    public static void bootstrapFullMap() {
        viewMap.putAll(JsonDeserialize.deserializeJsonFromFullMap(viewMap));
    }

    public static boolean updateUserPosition(User user) {
        MapCoordination mapCoordination = user.getMapCoordination();
        int x = mapCoordination.getX();
        int y = mapCoordination.getY();
        int z = mapCoordination.getZ();
        AreaType areaType = mapCoordination.getAreaType();
        int userId = user.getId();

        Map<AreaType, int[][]> firstMap = viewMap.get(z);
        int[][] intMap = firstMap.get(areaType);
        intMap[y][x] = userId;

        if (intMap[y][x] == userId)
            return true;

        return false;
    }

    public static boolean setItemToPosition(MapCoordination mapCoordination, BaseTile item) {
        int x = mapCoordination.getX();
        int y = mapCoordination.getY();
        int z = mapCoordination.getZ();
        AreaType areaType = mapCoordination.getAreaType();
        int itemId = item.getSqmId();

        Map<AreaType, int[][]> firstMap = viewMap.get(z);
        int[][] intMap = firstMap.get(areaType);
        intMap[y][x] = itemId;

        if (intMap[y][x] == itemId)
            return true;

        return false;
    }

    public static boolean erasePosition(MapCoordination mapCoordination) {
        int x = mapCoordination.getX();
        int y = mapCoordination.getY();
        int z = mapCoordination.getZ();
        AreaType areaType = mapCoordination.getAreaType();

        Map<AreaType, int[][]> firstMap = viewMap.get(z);
        int[][] intMap = firstMap.get(areaType);
        intMap[y][x] = 0;

        if (intMap[y][x] == 0)
            return true;

        return false;
    }

    public static int fullMapWidth() {
        return viewMap.get(0).get(AreaType.MAP)[0].length;
    }

    public static int fullMapHeight() {
        return viewMap.get(0).get(AreaType.MAP).length;
    }

    public static void print() {
        for (Map.Entry<Integer, Map<AreaType, int[][]>> integerMapEntry : viewMap.entrySet()) {
            System.out.println(integerMapEntry.getKey());
            for (Map.Entry<AreaType, int[][]> areaTypeEntry : integerMapEntry.getValue().entrySet()) {
                System.out.println(areaTypeEntry.getKey());
                for (int y = 0; y < areaTypeEntry.getValue()[0].length; y++) {
                    for (int x = 0; x < areaTypeEntry.getValue().length; x++) {
                        System.out.print(areaTypeEntry.getValue()[y][x] + ", ");
                    }
                }
                System.out.println();
            }
        }
    }
}

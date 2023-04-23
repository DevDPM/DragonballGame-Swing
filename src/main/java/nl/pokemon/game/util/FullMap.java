package nl.pokemon.game.util;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.pokemon.game.enums.AreaType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonRootName("full_map")
public class FullMap {

    private static final Map<Integer, Map<AreaType, int[][]>> viewMap = new HashMap<>();

    private List<Map<Integer, List<Map<AreaType, int[][]>>>> fullMapLayers;

    public static Map<Integer, Map<AreaType, int[][]>> getViewMap() {
        return viewMap;
    }

    public static boolean setNewValueToPosition(AreaType areaType, int x, int y, int z, int newValue) {
        Map<AreaType, int[][]> firstMap = viewMap.get(z);
        int[][] intMap = firstMap.get(areaType);
        intMap[y][x] = newValue;

        if (intMap[y][x] == newValue)
            return true;

        return false;
    }

    public void convertIntermediateToViewMap(List<Map<Integer, List<Map<AreaType, int[][]>>>> deserializedFullMap) {

        Map<Integer, List<Map<AreaType, int[][]>>> deserializedMap = deserializedFullMap.get(0);

        for (Map.Entry<Integer, List<Map<AreaType, int[][]>>> key : deserializedMap.entrySet()) {
            viewMap.put(key.getKey(), key.getValue().get(0));
        }

        for (Map.Entry<Integer, Map<AreaType, int[][]>> integer : viewMap.entrySet()) {
            for (Map.Entry<AreaType, int[][]> areatype : integer.getValue().entrySet()) {
                for (int y = 0; y < areatype.getValue()[0].length; y++) {
                    for (int x = 0; x < areatype.getValue().length; x++) {
                    }
                }
            };
        }
    }

    public static void deserializeJsonMapToIntermediate() {

        File jsonFile = new File("./src/main/resources/fullmap/fullmap.json");

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        try {
            FullMap resultData = mapper.readValue(jsonFile, FullMap.class);
            resultData.convertIntermediateToViewMap(resultData.getFullMapLayers());
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public List<Map<Integer, List<Map<AreaType, int[][]>>>> getFullMapLayers() {
        return fullMapLayers;
    }

    public static int fullMapWidth() {
        return viewMap.get(0).get(AreaType.MAP)[0].length;
    }

    public static int fullMapHeight() {
        return viewMap.get(0).get(AreaType.MAP).length;
    }
}

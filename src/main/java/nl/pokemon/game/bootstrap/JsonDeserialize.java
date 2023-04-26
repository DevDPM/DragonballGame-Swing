package nl.pokemon.game.bootstrap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import nl.pokemon.game.client.enums.AreaType;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class JsonDeserialize {

    private static final String FILE_PATH = "./src/main/resources/fullmap/fullmap.json";

//    public static void main(String[] args) throws IOException {
//        Map<Integer, Map<AreaType, int[][]>> viewMap = new HashMap<>();
//        Map<AreaType, int[][]> area1 = new HashMap<>();
//        area1.put(AreaType.MAP, new int[10][10]);
//        area1.put(AreaType.TERRAIN, new int[10][10]);
//
//        viewMap.put(-1, area1);
//        viewMap.put(0, area1);
//        viewMap.put(1, area1);
//
//        Gson gson = new Gson();
//        String result = gson.toJson(viewMap);
//        System.out.println(result);
//        System.out.println();
//
//        Type type = new TypeToken<Map<Integer, Map<AreaType, int[][]>>>(){}.getType();
//
//        String jsonString = new String(Files.readAllBytes(Paths.get("./src/main/resources/fullmap/fullmap.json")), StandardCharsets.UTF_8);
//        Map<Integer, Map<AreaType, int[][]>> myMap = gson.fromJson(jsonString, type);
//
//
//        serializeFullMapToJson(viewMap);
//
//        Map<Integer, Map<AreaType, int[][]>> newViewMap = new HashMap<>();
//        newViewMap = deserializeJsonFromFullMap(newViewMap);
//        System.out.println(newViewMap.get(-1));
//    }

    public static Map<Integer, Map<AreaType, int[][]>> deserializeJsonFromFullMap(Map<Integer, Map<AreaType, int[][]>> viewMap) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<Integer, Map<AreaType, int[][]>>>(){}.getType();
        String jsonString = "";
        try {
            jsonString = Files.readString(Paths.get(FILE_PATH));
        } catch (IOException e) {
            e.getMessage();
        }
        viewMap.putAll(gson.fromJson(jsonString, type));
        return viewMap;
    }
}
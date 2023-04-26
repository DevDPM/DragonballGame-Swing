package nl.pokemon.mapGenerator.util;

import com.google.gson.Gson;
import nl.pokemon.game.client.enums.AreaType;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class JsonSerialize {

    private static final String FILE_PATH = "./src/main/resources/fullmap/fullmap.json";

    public static void serializeFullMapToJson(Map<Integer, Map<AreaType, int[][]>> saveMap) {
        Gson gson = new Gson();
        String result = gson.toJson(saveMap);
        try {
            // delete all content from file
            new FileOutputStream(FILE_PATH).close();

            // write new json string to file
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
            writer.write(result);
            writer.close();
        } catch (IOException e) {
            e.getMessage();
        }

    }


}
package nl.pokemon.mapGenerator.util;

import nl.pokemon.game.client.enums.AreaType;
import nl.pokemon.mapGenerator.model.View.MG_GridMap;
import nl.pokemon.mapGenerator.model.View.MG_ViewMap;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class GENERATE_NEW_MAP {

    public static void main(String[] args) {

        // "-1": {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int elev = MG_ViewMap.START_Z; elev <= MG_ViewMap.MAX_Z; elev++) {
            // "-1": {
            // xx
            // },
            sb.append("\"" + elev + "\": {");

            int count = 0;
            for (AreaType areaType : AreaType.values()) {
                // "MAP":
                count++;

                sb.append("\""+ areaType.name() + "\": [");
                for (int y = 1; y <= MG_GridMap.MAX_X; y++) {
                    sb.append("[");
                    for (int i = 1; i <= MG_GridMap.MAX_X; i++) {
                        if (i != MG_GridMap.MAX_X) {
                            sb.append("0,");
                        } else {
                            sb.append("0");
                        }
                    }
                    if (y != MG_GridMap.MAX_X) {
                        sb.append("], ");
                    } else if (count != AreaType.values().length){
                        sb.append("]],");
                    } else {
                        sb.append("]]");
                    }
                }
            }
            if (elev != MG_ViewMap.MAX_Z) {
                sb.append("},");
            } else {
                sb.append("}");
            }
        }
        sb.append("}");

        System.out.println(sb);

        final String FILE_PATH = "./src/main/resources/fullmap/test_map.json";

        try {
            // delete all content from file
            new FileOutputStream(FILE_PATH).close();

            // write new json string to file
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
            writer.write(String.valueOf(sb));
            writer.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }
}

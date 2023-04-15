package nl.pokemon.game.util;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.SQMs.*;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SQMObjects {

    private static Map<AreaType, Map<Integer, BaseSQM>> SQMByArea = new HashMap<>();
    private static AtomicInteger ID_SQM;

    public static BaseSQM getSQMByIntAndArea(AreaType area, int sqmId) {
        return getSQMByArea(area).get(sqmId);
    }

    public static Map<AreaType, Map<Integer, BaseSQM>> getSQMByArea() {
        return SQMByArea;
    }

    private static String getRootFolderByArea(AreaType area) {
        return switch(area) {
            case MAP -> "src/main/resources/images/map/";
            case TERRAIN -> "src/main/resources/images/terrain/";
        };
    }

    public static void bootstrap() {

        for (AreaType area : AreaType.values()) {

            ID_SQM = new AtomicInteger(1);
            Map<Integer, BaseSQM> SQMMap = new HashMap<>();
            String path = getRootFolderByArea(area);

            File file = new File(path);
            File[] fileArray = file.listFiles();

            assert fileArray != null;
            SQMMap.put(0, new MapSQM());
            for (File fileName : fileArray) {

                System.out.println(fileName.getName());
                if (!fileName.getName().contains(".") && fileName.getName().equals("elevate")) {

                    File elevationFilePath = new File(fileName.getPath());
                    File[] elevationFileArray = elevationFilePath.listFiles();

                    assert elevationFileArray != null;
                    SQMMap.put(0, new MapSQM());
                    for (File elevationFile : elevationFileArray) {
                        BaseSQM sqm;
                        if (elevationFile.getName().contains("up")) {
                            sqm = new FloorUpSQM();
                            sqm.setImageIcon(new ImageIcon(elevationFile.getPath()));
                        } else {
                            sqm = new FloorDownSQM();
                            sqm.setImageIcon(new ImageIcon(elevationFile.getPath()));
                        }
                        SQMMap.put(ID_SQM.getAndIncrement(), sqm);
                    }
                    continue;
                }

                BaseSQM sqm = SQMFactory.getSQMBySurface(area);
                sqm.setImageIcon(new ImageIcon(fileName.getPath()));
                sqm.updateSQM();
                SQMMap.put(ID_SQM.getAndIncrement(), sqm);
            }
            SQMByArea.put(area, SQMMap);
        }
    }

    public static Map<Integer, BaseSQM> getSQMByArea(AreaType area) {
        return SQMByArea.get(area);
    }

    public static void printSQMList() {
        SQMObjects.getSQMByArea().forEach((key, value) -> {
            System.out.println(key.name());
            value.forEach((keyy, valuee) -> {
                System.out.println(keyy +" -> " + valuee.getClass().getName() + " / " + valuee.getImageIcon());
            });
        });
    }
}

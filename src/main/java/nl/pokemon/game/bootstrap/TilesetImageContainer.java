package nl.pokemon.game.bootstrap;

import nl.pokemon.game.core.model.tiles.*;
import nl.pokemon.game.client.enums.AreaType;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TilesetImageContainer {

    private static Map<AreaType, Map<Integer, BaseTile>> SQMByArea = new HashMap<>();
    private static AtomicInteger ID_SQM;

    public static BaseTile getTileByIntAndArea(AreaType area, int sqmId) {
        return getSQMByArea(area).get(sqmId);
    }

    public static Map<AreaType, Map<Integer, BaseTile>> getSQMByArea() {
        return SQMByArea;
    }

    private static String getRootFolderByArea(AreaType area) {
        return switch(area) {
            case MAP -> "src/main/resources/images/map/";
            case LOWER_TERRAIN -> "src/main/resources/images/lower-terrain/";
            case PLAYER_BOTTOM -> "src/main/resources/images/entity/ash/";
            case HIGHER_TERRAIN -> "src/main/resources/images/higher-terrain/";
            case PLAYER_TOP -> "src/main/resources/images/entity/ash/";
        };
    }

    public static void bootstrap() {

        for (AreaType area : AreaType.values()) {

            ID_SQM = new AtomicInteger(1);
            Map<Integer, BaseTile> SQMMap = new HashMap<>();
            String path = getRootFolderByArea(area);

            File file = new File(path);
            File[] fileArray = file.listFiles();

            assert fileArray != null;
            SQMMap.put(0, new MapTile());
            for (File fileName : fileArray) {

                // additional 'work-around' due to design flaw
                if (fileName.getPath().contains("higher-terrain") && isElevationImageAndInsert(SQMMap, fileName)) continue;
                if (fileName.getPath().contains("lower-terrain") && isGrabbableImageAndInsert(SQMMap, fileName)) continue;

                BaseTile sqm = TileFactory.getTileByArea(area);
                ImageIcon icon = new ImageIcon(fileName.getPath());
                sqm.setSqmSizeX(icon.getIconWidth()/ BaseTile.SQM_PIXEL_WIDTH_X);
                sqm.setSqmSizeY(icon.getIconHeight()/ BaseTile.SQM_PIXEL_HEIGHT_Y);
                sqm.setImageIcon(icon);
                sqm.updateSQM();

                int id = Integer.parseInt(fileName.getName().replaceAll("\\D+",""));
                sqm.setSqmId(id);
                SQMMap.put(id, sqm);
            }
            SQMByArea.put(area, SQMMap);
        }
    }

    private static void insertFileImageIntoSqmContainer(File file, BaseTile sqm, Map<Integer, BaseTile> SQMMap) {

        String fileStringName = file.getName();
        ImageIcon icon = new ImageIcon(file.getPath());

        sqm.setSqmSizeX((int) Math.ceil((1.0 * icon.getIconWidth()) / (1.0 * BaseTile.SQM_PIXEL_WIDTH_X)));
        sqm.setSqmSizeY((int) Math.ceil((1.0 * icon.getIconHeight()) / (1.0 * BaseTile.SQM_PIXEL_HEIGHT_Y)));
        sqm.setImageIcon(icon);

        int id = Integer.parseInt(fileStringName.substring(0, fileStringName.indexOf("_")).replaceAll("\\D+", ""));
        sqm.setSqmId(id);

        SQMMap.put(id, sqm);
    }

    private static boolean isGrabbableImageAndInsert(Map<Integer, BaseTile> SQMMap, File fileName) {
        if (!fileName.getName().contains(".") && fileName.getName().equals("grabbable")) {
            File[] elevationFileArray = getFiles(fileName);
            assert elevationFileArray != null;
            SQMMap.put(0, new MapTile());
            for (File elevationFile : elevationFileArray) {
                BaseTile sqm = new ItemTile();
                insertFileImageIntoSqmContainer(elevationFile, sqm, SQMMap);
            }
            return true;
        }
        return false;
    }

    private static boolean isElevationImageAndInsert(Map<Integer, BaseTile> SQMMap, File fileName) {
        if (!fileName.getName().contains(".") && fileName.getName().equals("elevate")) {
            File[] elevationFileArray = getFiles(fileName);
            assert elevationFileArray != null;
            SQMMap.put(0, new MapTile());
            for (File elevationFile : elevationFileArray) {
                BaseTile sqm;
                if (elevationFile.getName().contains("up")) {
                    sqm = new FloorUpTile();
                } else {
                    sqm = new FloorDownTile();
                }
                insertFileImageIntoSqmContainer(elevationFile, sqm, SQMMap);
            }
            return true;
        }
        return false;
    }

    private static File[] getFiles(File fileName) {
        File elevationFilePath = new File(fileName.getPath());
        return elevationFilePath.listFiles();
    }



    public static Map<Integer, BaseTile> getSQMByArea(AreaType area) {
        return SQMByArea.get(area);
    }

    public static void printSQMList() {
        TilesetImageContainer.getSQMByArea().forEach((key, value) -> {
            System.out.println(key.name());
            value.forEach((keyy, valuee) -> {
                System.out.println(keyy +" -> " + valuee.getClass().getName() + " / " + valuee.getImageIcon());
            });
        });
    }
}

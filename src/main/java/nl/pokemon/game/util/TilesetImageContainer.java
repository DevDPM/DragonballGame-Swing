package nl.pokemon.game.util;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.SQMs.*;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TilesetImageContainer {

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
            case PLAYER_BOTTOM -> "src/main/resources/images/entity/ash/";
            case TERRAIN -> "src/main/resources/images/terrain/";
            case PLAYER_TOP -> "src/main/resources/images/entity/ash/";
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

                if (isElevationImage(SQMMap, fileName)) continue;
                if (isHouseImage(SQMMap, fileName, area)) continue;
                if (isNatureImage(SQMMap, fileName)) continue;
                if (isTreeImage(SQMMap, fileName, area)) continue;
                if (isUnwalkableImage(SQMMap, fileName, area)) continue;

                BaseSQM sqm = SQMFactory.getSQMBySurface(area);
                ImageIcon icon = new ImageIcon(fileName.getPath());
                sqm.setSqmSizeX(icon.getIconWidth()/BaseSQM.SQM_PIXEL_WIDTH_X);
                sqm.setSqmSizeY(icon.getIconHeight()/BaseSQM.SQM_PIXEL_HEIGHT_Y);
                sqm.setImageIcon(icon);
                sqm.updateSQM();
                SQMMap.put(Integer.valueOf(fileName.getName().replaceAll("\\D+","")), sqm);
            }
            SQMByArea.put(area, SQMMap);
        }
    }

    private static boolean isUnwalkableImage(Map<Integer, BaseSQM> SQMMap, File fileName, AreaType area) {
        if (!fileName.getName().contains(".") && fileName.getName().equals("unwalkable")) {

            File elevationFilePath = new File(fileName.getPath());
            File[] elevationFileArray = elevationFilePath.listFiles();

            assert elevationFileArray != null;
            SQMMap.put(0, new MapSQM());
            for (File elevationFile : elevationFileArray) {
                String fileStringName = elevationFile.getName();

                ImageIcon icon;
                BaseSQM sqm = SQMFactory.getSQMBySurface(area);

                icon = new ImageIcon(elevationFile.getPath());
                sqm.setSqmSizeX((int) Math.ceil((1.0*icon.getIconWidth())/(1.0*BaseSQM.SQM_PIXEL_WIDTH_X)));
                sqm.setSqmSizeY((int) Math.ceil((1.0*icon.getIconHeight())/(1.0*BaseSQM.SQM_PIXEL_HEIGHT_Y)));
                sqm.setImageIcon(icon);
                int id = Integer.parseInt(fileStringName.substring(0, fileStringName.indexOf("_")).replaceAll("\\D+",""));
                SQMMap.put(id, sqm);
            }
            return true;
        }
        return false;
    }

    private static boolean isNatureImage(Map<Integer, BaseSQM> SQMMap, File fileName) {
        if (!fileName.getName().contains(".") && fileName.getName().equals("walkable")) {

            File elevationFilePath = new File(fileName.getPath());
            File[] elevationFileArray = elevationFilePath.listFiles();

            assert elevationFileArray != null;
            SQMMap.put(0, new MapSQM());
            for (File elevationFile : elevationFileArray) {
                String fileStringName = elevationFile.getName();

                ImageIcon icon;
                BaseSQM sqm = new MapSQM();

                icon = new ImageIcon(elevationFile.getPath());
                sqm.setSqmSizeX((int) Math.ceil((1.0*icon.getIconWidth())/(1.0*BaseSQM.SQM_PIXEL_WIDTH_X)));
                sqm.setSqmSizeY((int) Math.ceil((1.0*icon.getIconHeight())/(1.0*BaseSQM.SQM_PIXEL_HEIGHT_Y)));
                sqm.setImageIcon(icon);
                int id = Integer.parseInt(fileStringName.substring(0, fileStringName.indexOf("_")).replaceAll("\\D+",""));
                SQMMap.put(id, sqm);
            }
            return true;
        }
        return false;
    }

    private static boolean isTreeImage(Map<Integer, BaseSQM> SQMMap, File fileName, AreaType area) {
        if (!fileName.getName().contains(".") && fileName.getName().equals("tree")) {

            File elevationFilePath = new File(fileName.getPath());
            File[] elevationFileArray = elevationFilePath.listFiles();

            assert elevationFileArray != null;
            SQMMap.put(0, new MapSQM());
            for (File elevationFile : elevationFileArray) {
                String fileStringName = elevationFile.getName();

                ImageIcon icon;
                BaseSQM sqm = SQMFactory.getSQMBySurface(area);

                icon = new ImageIcon(elevationFile.getPath());
                sqm.setSqmSizeX((int) Math.ceil((1.0*icon.getIconWidth())/(1.0*BaseSQM.SQM_PIXEL_WIDTH_X)));
                sqm.setSqmSizeY((int) Math.ceil((1.0*icon.getIconHeight())/(1.0*BaseSQM.SQM_PIXEL_HEIGHT_Y)));
                sqm.setImageIcon(icon);
                int id = Integer.parseInt(fileStringName.substring(0, fileStringName.indexOf("_")).replaceAll("\\D+",""));
                SQMMap.put(id, sqm);
            }
            return true;
        }
        return false;
    }

    private static boolean isHouseImage(Map<Integer, BaseSQM> SQMMap, File fileName, AreaType area) {
        if (!fileName.getName().contains(".") && fileName.getName().equals("house")) {

            File elevationFilePath = new File(fileName.getPath());
            File[] elevationFileArray = elevationFilePath.listFiles();

            assert elevationFileArray != null;
            SQMMap.put(0, new MapSQM());
            for (File elevationFile : elevationFileArray) {
                String fileStringName = elevationFile.getName();

                ImageIcon icon;
                BaseSQM sqm = SQMFactory.getSQMBySurface(area);

                icon = new ImageIcon(elevationFile.getPath());
                sqm.setSqmSizeX((int) Math.ceil((1.0*icon.getIconWidth())/(1.0*BaseSQM.SQM_PIXEL_WIDTH_X)));
                sqm.setSqmSizeY((int) Math.ceil((1.0*icon.getIconHeight())/(1.0*BaseSQM.SQM_PIXEL_HEIGHT_Y)));
                sqm.setImageIcon(icon);
                int id = Integer.parseInt(fileStringName.substring(0, fileStringName.indexOf("_")).replaceAll("\\D+",""));
                SQMMap.put(id, sqm);
            }
            return true;
        }
        return false;
    }

    private static boolean isElevationImage(Map<Integer, BaseSQM> SQMMap, File fileName) {
        if (!fileName.getName().contains(".") && fileName.getName().equals("elevate")) {

            File elevationFilePath = new File(fileName.getPath());
            File[] elevationFileArray = elevationFilePath.listFiles();

            assert elevationFileArray != null;
            SQMMap.put(0, new MapSQM());
            for (File elevationFile : elevationFileArray) {
                String fileStringName = elevationFile.getName();

                BaseSQM sqm;
                ImageIcon icon;
                if (elevationFile.getName().contains("up")) {
                    sqm = new FloorUpSQM();
                } else {
                    sqm = new FloorDownSQM();
                }

                icon = new ImageIcon(elevationFile.getPath());
                sqm.setSqmSizeX((int) Math.ceil((1.0*icon.getIconWidth())/(1.0*BaseSQM.SQM_PIXEL_WIDTH_X)));
                sqm.setSqmSizeY((int) Math.ceil((1.0*icon.getIconHeight())/(1.0*BaseSQM.SQM_PIXEL_HEIGHT_Y)));
                sqm.setImageIcon(icon);
                int id = Integer.parseInt(fileStringName.substring(0, fileStringName.indexOf("_")).replaceAll("\\D+",""));
                SQMMap.put(id, sqm);
            }
            return true;
        }
        return false;
    }

    public static Map<Integer, BaseSQM> getSQMByArea(AreaType area) {
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

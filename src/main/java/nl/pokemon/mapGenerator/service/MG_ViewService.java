package nl.pokemon.mapGenerator.service;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;
import nl.pokemon.mapGenerator.model.SQMs.DevTool_BaseTile;
import nl.pokemon.mapGenerator.model.SQMs.DevTool_VoidTile;
import nl.pokemon.mapGenerator.model.View.MG_GridMap;
import nl.pokemon.mapGenerator.model.View.MG_ViewMap;
import nl.pokemon.mapGenerator.view.OptionPanel;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class MG_ViewService {

    @Inject(name = "fullMap")
    private MG_ViewMap fullMap;

    @Inject(name = "clientViewMap")
    private MG_ViewMap clientViewMap;

    @Inject
    private DevTool_VoidTile voidSQM;

    @Inject
    private OptionPanel optionPanel;

    private final int MAX_X = 50;
    private final int MAX_Y = 50;

    public void moveView(Direction direction) {

        int incrementX = 0;
        int incrementY = 0;

        switch (direction) {
            case NORTH -> incrementY--;
            case EAST -> incrementX++;
            case SOUTH -> incrementY++;
            case WEST -> incrementX--;
        }

        List<Integer> elevations = new ArrayList<>(clientViewMap.getViewMap().keySet());
        Collections.reverse(elevations);

        for (int elevation : elevations) {
            Map<AreaType, MG_GridMap> layerMap = clientViewMap.getViewMap().get(elevation);
            List<AreaType> areaTypes = new ArrayList<>(layerMap.keySet());
            Collections.sort(areaTypes);

            for (AreaType areaType : areaTypes) {
                MG_GridMap areaMap = layerMap.get(areaType);
                for (int y = 0; y <= 30; y++) {
                    for (int x = 0; x <= 30; x++) {

                        DevTool_BaseTile clientViewSQM = areaMap.getGridMap()[y][x];

                        int newX = clientViewSQM.getCoordinateX() + incrementX;
                        int newY = clientViewSQM.getCoordinateY() + incrementY;

                        clientViewSQM.setCoordinateX(newX);
                        clientViewSQM.setCoordinateY(newY);

                        DevTool_BaseTile storedSQM;
                        if (newX < 0 || newY < 0
                                || newX >= fullMap.getViewMap().get(elevation).get(areaType).getGridMap().length
                                || newY >= fullMap.getViewMap().get(elevation).get(areaType).getGridMap()[0].length) {
                            storedSQM = voidSQM;
                        } else {
                            storedSQM = fullMap.getViewMap().get(elevation).get(areaType).getGridMap()[newY][newX];
                        }
                        clientViewSQM.setAreaType(storedSQM.getAreaType());
                        clientViewSQM.setSqmId(storedSQM.getSqmId());
                        clientViewSQM.setImageIcon(storedSQM.getImageIcon());
                        clientViewSQM.setSqmSizeX(storedSQM.getSqmSizeX());
                        clientViewSQM.setSqmSizeY(storedSQM.getSqmSizeY());
                        clientViewSQM.setBorderPainted(false);
                        clientViewSQM.updateSQM();
                    }
                }
            }
        }
    }

    public void changeElevation(int z) {

        List<Integer> elevations = new ArrayList<>(clientViewMap.getViewMap().keySet());
        Collections.reverse(elevations);

        for (int elevation : elevations) {
            Map<AreaType, MG_GridMap> layerMap = clientViewMap.getViewMap().get(elevation);
            List<AreaType> areaTypes = new ArrayList<>(layerMap.keySet());
            Collections.sort(areaTypes);

            for (AreaType areaType : areaTypes) {
                MG_GridMap areaMap = layerMap.get(areaType);
                for (int y = 0; y <= 30; y++) {
                    for (int x = 0; x <= 30; x++) {

                        DevTool_BaseTile sqm = areaMap.getGridMap()[y][x];
                        changeSQMOffsetByZ(elevation, sqm, z);
                        sqm.setVisible(true);
                        if (elevation > z)
                            sqm.setVisible(false);
                        if (areaType.ordinal() < optionPanel.getCurentAreaType().ordinal()) {
                            sqm.setVisible(false);
                        }
                        sqm.setBorderPainted(false);
                        sqm.updateSQM();
                    }
                }
            }
        }
    }

    private void changeSQMOffsetByZ(int currentZ, DevTool_BaseTile viewSQM, int upToZ) {
        // if z below player.z then change offset. player.z = z => standard offset, increment higher as well just in case
        int OFFSET_PIXEL_Y = 0;
        int newOffSetY;

        if (currentZ < upToZ) {
            newOffSetY = ((upToZ - currentZ) * 60) + OFFSET_PIXEL_Y;
            viewSQM.setContentAreaFilled(false);
            viewSQM.setBorderPainted(false);
            viewSQM.setEnabled(false);
        } else if (currentZ == upToZ) {
            newOffSetY = OFFSET_PIXEL_Y;
            viewSQM.setBorderPainted(true);
            viewSQM.setEnabled(true);
            viewSQM.setContentAreaFilled(true);
        } else {
            newOffSetY = ((currentZ - upToZ) * 60) + OFFSET_PIXEL_Y;
            viewSQM.setBorderPainted(false);
            viewSQM.setEnabled(false);
            viewSQM.setContentAreaFilled(false);
        }
        viewSQM.setOFFSET_PIXEL_Y(newOffSetY);
    }

    public void changeAreaType(AreaType upToAreaType) {

        List<Integer> elevations = new ArrayList<>(clientViewMap.getViewMap().keySet());
        Collections.reverse(elevations);
        int z = optionPanel.getCurrentZ();

        for (int elevation : elevations) {
            Map<AreaType, MG_GridMap> layerMap = clientViewMap.getViewMap().get(elevation);
            List<AreaType> areaTypes = new ArrayList<>(layerMap.keySet());
            Collections.sort(areaTypes);

            for (AreaType areaType : areaTypes) {
                MG_GridMap areaMap = layerMap.get(areaType);
                for (int y = 0; y <= 30; y++) {
                    for (int x = 0; x <= 30; x++) {

                        DevTool_BaseTile sqm = areaMap.getGridMap()[y][x];
                        changeSQMOffsetByZ(elevation, sqm, z);
                        sqm.setVisible(true);
                        if (elevation > optionPanel.getCurrentZ())
                            sqm.setVisible(false);
                        if (areaType.ordinal() < upToAreaType.ordinal()) {
                            sqm.setVisible(false);
                            sqm.setEnabled(false);
                        }
                        sqm.setBorderPainted(false);
                        sqm.updateSQM();
                    }
                }
            }
        }
    }
}
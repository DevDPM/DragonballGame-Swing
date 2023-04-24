package nl.pokemon.mapGenerator.view;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.View.GridMap;
import nl.pokemon.game.model.View.ViewMap;
import nl.pokemon.game.util.FullMap;
import nl.pokemon.game.util.TilesetImageContainer;
import nl.pokemon.mapGenerator.model.Editable_SQM;
import nl.pokemon.mapGenerator.model.SQMs.MG_BaseSQM;
import nl.pokemon.mapGenerator.model.View.MG_ViewMap;
import org.dpmFramework.Kickstarter;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ViewPanel extends JPanel {

    @Inject
    MG_ViewMap viewMap;

    @Inject
    OptionPanel optionPanel;

    public ViewPanel() {
        this.setLayout(null);
    }

    public void init() {
        FullMap.bootstrapFullMap();
        TilesetImageContainer.bootstrap();

        List<Integer> elevations = new ArrayList<>(FullMap.getViewMap().keySet());
        Collections.reverse(elevations);

        for (int elevation : elevations) {
            Map<AreaType, int[][]> layerMap = FullMap.getViewMap().get(elevation);
            List<AreaType> areaTypes = new ArrayList<>(layerMap.keySet());
            Collections.sort(areaTypes);

            for (AreaType areaType : areaTypes) {
                int[][] areaMap = layerMap.get(areaType);
                for (int y = areaMap[0].length-1; y >= 0; y--) {
                    for (int x = areaMap.length-1; x >= 0; x--) {
                        MG_BaseSQM sqm = viewMap.getViewMap().get(elevation).get(areaType).getGridMap()[y][x];
                        sqm.setIndexX(x);
                        sqm.setIndexY(y);
                        sqm.setAreaType(areaType);
                        sqm.setSqmId(areaMap[y][x]);
                        sqm.setImageIcon(TilesetImageContainer.getSQMByArea(areaType).get(areaMap[y][x]).getImageIcon());
                        sqm.setSqmSizeX(TilesetImageContainer.getSQMByArea(areaType).get(areaMap[y][x]).getSqmSizeX());
                        sqm.setSqmSizeY(TilesetImageContainer.getSQMByArea(areaType).get(areaMap[y][x]).getSqmSizeY());
                        sqm.setVisible(true);
                        sqm.updateSQM();
                        this.add(sqm);
                    }
                }
            }
        }
        this.setVisible(true);
    }

    public void changeElevation(int z) {
        List<Integer> elevations = new ArrayList<>(FullMap.getViewMap().keySet());
        Collections.reverse(elevations);

        for (int elevation : elevations) {
            Map<AreaType, int[][]> layerMap = FullMap.getViewMap().get(elevation);
            List<AreaType> areaTypes = new ArrayList<>(layerMap.keySet());
            Collections.sort(areaTypes);

            for (AreaType areaType : areaTypes) {
                int[][] areaMap = layerMap.get(areaType);
                for (int y = 0; y < areaMap[0].length; y++) {
                    for (int x = 0; x < areaMap.length; x++) {
                        MG_BaseSQM sqm = viewMap.getViewMap().get(elevation).get(areaType).getGridMap()[y][x];
                        changeSQMOffsetByZ(elevation, sqm, z);
                        sqm.setVisible(true);
                        if (elevation > z)
                            sqm.setVisible(false);
                        if (areaType.ordinal() < optionPanel.getCurentAreaType().ordinal()) {
                            sqm.setVisible(false);
                        }
                        sqm.updateSQM();
                    }
                }
            }
        }
    }

    private void changeSQMOffsetByZ(int currentZ, MG_BaseSQM viewSQM, int upToZ) {
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
        List<Integer> elevations = new ArrayList<>(FullMap.getViewMap().keySet());
        Collections.reverse(elevations);
        int z = optionPanel.getCurrentZ();

        for (int elevation : elevations) {
            Map<AreaType, int[][]> layerMap = FullMap.getViewMap().get(elevation);
            List<AreaType> areaTypes = new ArrayList<>(layerMap.keySet());
            Collections.sort(areaTypes);

            for (AreaType areaType : areaTypes) {
                int[][] areaMap = layerMap.get(areaType);
                for (int y = 0; y < areaMap[0].length; y++) {
                    for (int x = 0; x < areaMap.length; x++) {
                        MG_BaseSQM sqm = viewMap.getViewMap().get(elevation).get(areaType).getGridMap()[y][x];
                        changeSQMOffsetByZ(elevation, sqm, z);
                        sqm.setVisible(true);
                        if (elevation > optionPanel.getCurrentZ())
                            sqm.setVisible(false);
                        if (areaType.ordinal() < upToAreaType.ordinal()) {
                            sqm.setVisible(false);
                            sqm.setEnabled(false);
                        }
                        sqm.updateSQM();
                    }
                }
            }
        }
    }
}

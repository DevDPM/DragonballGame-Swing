package nl.pokemon.mapGenerator.service;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.util.FullMap;
import nl.pokemon.mapGenerator.model.SQMs.MG_BaseSQM;
import nl.pokemon.mapGenerator.model.View.MG_ViewMap;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class MG_ViewService {

    @Inject
    MG_ViewMap viewMap;

    private final int MAX_X = 50;
    private final int MAX_Y = 50;

    public void moveView(Direction direction) {

        int incrementIndexX = 0;
        int incrementIndexY = 0;

        switch (direction) {
            case NORTH -> incrementIndexY++;
            case EAST -> incrementIndexX--;
            case SOUTH -> incrementIndexY--;
            case WEST -> incrementIndexX++;
        }

        List<Integer> elevations = new ArrayList<>(viewMap.getViewMap().keySet());
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
                        sqm.setIndexX(sqm.getIndexX() + incrementIndexX);
                        sqm.setIndexY(sqm.getIndexY() + incrementIndexY);

                        sqm.updateSQM();
                    }
                }
            }
        }
    }
}
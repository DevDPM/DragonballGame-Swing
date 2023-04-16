package nl.pokemon.game.service;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.CurrentPlayer;
import nl.pokemon.game.model.SQMs.VoidSQM;
import nl.pokemon.game.model.View.GridMap;
import nl.pokemon.game.model.View.ViewMap;
import nl.pokemon.game.util.FULL_MAP;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ViewMapService {

    @Inject
    ViewMap viewMap;

    @Inject
    SQMService sqmService;

    @Inject
    MoveService moveService;

    @Inject
    CurrentPlayer player;

    @Inject
    VoidSQM voidSQM;

    public void updateView() {

        int START_X_MAP = player.getFDMIndexX() - ((GridMap.MAX_X - 1)/2);
        int START_Y_MAP = player.getFDMIndexY() - ((GridMap.MAX_Y - 1)/2);
        int START_Z_MAP = player.getFDMIndexZ();

        Map<Integer, Map<AreaType, GridMap>> fullMap = viewMap.getViewMap();
        List<Integer> elevations = new ArrayList<>(fullMap.keySet());
        Collections.sort(elevations);

        for (int z : elevations) {
            Map<AreaType, GridMap> viewSurfaceGridMap = viewMap.getViewMap().get(z);
            Map<AreaType, int[][]> storageSurfaceGridMap = FULL_MAP.getViewMap().get(z);

            Arrays.stream(AreaType.values()).sorted().forEach(area -> {
                BaseSQM[][] viewGridMap = viewSurfaceGridMap.get(area).getGridMap();
                int[][] storageGridMap = storageSurfaceGridMap.get(area);

                for (int y = 0, intY = START_Y_MAP; y < GridMap.MAX_Y; y++, intY++) {
                    for (int x = 0, intX = START_X_MAP; x < GridMap.MAX_X; x++, intX++) {
                        BaseSQM storedSQM;
                        if (intY < 0 || intX < 0 || intY >= FULL_MAP.fullMapHeight() || intX >= FULL_MAP.fullMapWidth()) {
                            storedSQM = voidSQM;
                        } else {
                            storedSQM = sqmService.getSQMByIntAndArea(area, storageGridMap[intY][intX]);
                        }

                        BaseSQM viewSQM = viewGridMap[y][x];
                        viewSQM.setImageIcon(storedSQM.getImageIcon());

                        if (moveService.isElevating()) {
                            setElevationToSQM(START_Z_MAP, z, area, y, x, viewSQM);
                        }
                        viewSQM.updateSQM();
                    }
                }
            });
        }
    }

    private void setElevationToSQM(int START_Z_MAP, int z, AreaType area, int y, int x, BaseSQM viewSQM) {
        if (z <= START_Z_MAP) {
            viewSQM.setVisible(true);
        } else {
            viewSQM.setVisible(false);
        }
        if (z >= 1 && x == GridMap.MAX_X - 1 && y == GridMap.MAX_Y - 1 && area.equals(AreaType.MAP)) {
            moveService.setElevating(false);
        }
    }

    public Map<Integer, Map<AreaType, GridMap>> getFullViewMap() {
        return viewMap.getViewMap();
    }

    public Map<AreaType, GridMap> getViewGridMapLayerLevel(int z) {
        return getFullViewMap().get(z);
    }

    public GridMap getViewGridMap(AreaType areaType, int z) {
        return getViewGridMapLayerLevel(z).get(areaType);
    }

    public BaseSQM getViewSQM(AreaType areaType, int x, int y, int z) {
        if (x < 0 || y < 0 || x >= GridMap.MAX_X || y >= GridMap.MAX_Y)
            return null;
        return getViewGridMap(areaType, z).getGridMap()[y][x];
    }
}

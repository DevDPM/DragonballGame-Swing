package nl.pokemon.game.service;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.SQMs.VoidSQM;
import nl.pokemon.game.model.View.GridMap;
import nl.pokemon.game.model.View.ViewMap;
import nl.pokemon.game.util.FullMap;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

@Service
public class ClientViewMap implements PropertyChangeListener {

    @Inject
    ViewMap viewMap;

    @Inject
    SQMService sqmService;

    @Inject
    MoveService moveService;

    @Inject
    PlayerService playerService;

    @Inject
    VoidSQM voidSQM;

    public void updateView() {

        int START_X_MAP = playerService.getPlayerX() - ((GridMap.MAX_X - 1)/2);
        int START_Y_MAP = playerService.getPlayerY() - ((GridMap.MAX_Y - 1)/2);
        int START_Z_MAP = playerService.getPlayerZ();

        Map<Integer, Map<AreaType, GridMap>> fullMap = viewMap.getViewMap();
        List<Integer> elevations = new ArrayList<>(fullMap.keySet());
        Collections.sort(elevations);

        for (int z : elevations) {
            Map<AreaType, GridMap> viewSurfaceGridMap = viewMap.getViewMap().get(z);
            Map<AreaType, int[][]> storageSurfaceGridMap = FullMap.getViewMap().get(z);

            Arrays.stream(AreaType.values()).sorted().forEach(area -> {

                    BaseSQM[][] viewGridMap = viewSurfaceGridMap.get(area).getGridMap();
                    int[][] storageGridMap = storageSurfaceGridMap.get(area);

                    for (int y = 0, intY = START_Y_MAP; y < GridMap.MAX_Y; y++, intY++) {
                        for (int x = 0, intX = START_X_MAP; x < GridMap.MAX_X; x++, intX++) {
                            BaseSQM storedSQM;

                            if (intY < 0 || intX < 0 || intY >= FullMap.fullMapHeight() || intX >= FullMap.fullMapWidth()) {
                                storedSQM = voidSQM;
                            } else {
                                if (area.equals(AreaType.PLAYER_BOTTOM) || area.equals(AreaType.PLAYER_TOP)) {
                                    if (storageGridMap[intY][intX] == 0) {
                                        storedSQM = sqmService.getSQMByIntAndArea(area, storageGridMap[intY][intX]);
                                    } else {
                                        storedSQM = playerService.getPlayerSQMByUserId(storageGridMap[intY][intX]);
                                    }
                                } else {
                                    storedSQM = sqmService.getSQMByIntAndArea(area, storageGridMap[intY][intX]);
                                }
                            }
                            BaseSQM viewSQM = viewGridMap[y][x];
                            viewSQM.setImageIcon(storedSQM.getImageIcon());
                            viewSQM.setSqmSizeY(storedSQM.getSqmSizeY());
                            viewSQM.setSqmSizeX(storedSQM.getSqmSizeX());
                            if (moveService.isElevating()) {
                                setVisibilityByElevation(START_Z_MAP, z, area, y, x, viewSQM);
                            }
                            viewSQM.updateSQM();

                        }
                    }
            });
        }
    }

    public void changeSQMVisibility(AreaType areaType, int x, int y, int z) {
        BaseSQM baseSQM = getViewSQM(areaType, x, y, z);
        baseSQM.setVisible(!baseSQM.isVisible());
    }

    private void setVisibilityByElevation(int playerZ, int currentZ, AreaType area, int y, int x, BaseSQM viewSQM) {
        if (currentZ <= playerZ) {
            viewSQM.setVisible(true);
        } else {
            viewSQM.setVisible(false);
        }
        if (currentZ >= 1 && x == GridMap.MAX_X - 1 && y == GridMap.MAX_Y - 1 && area.equals(AreaType.MAP)) {
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("adjust view visibility");
    }
}

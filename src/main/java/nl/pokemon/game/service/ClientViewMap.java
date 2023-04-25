package nl.pokemon.game.service;

import nl.pokemon.game.domain.User;
import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.Elevatable;
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
    PlayerService playerService;

    @Inject
    VoidSQM voidSQM;

    @Inject
    FullMapManager fullMapManager;

    public void updateView() {
        updateView(null);
    }

    public void updateView(Integer visibilityUntilZ) {

        int START_X_MAP = playerService.getPlayerX() - ((GridMap.MAX_X - 1)/2);
        int START_Y_MAP = playerService.getPlayerY() - ((GridMap.MAX_Y - 1)/2);
        int START_Z_MAP = playerService.getPlayerZ();

        Map<Integer, Map<AreaType, GridMap>> fullMap = viewMap.getViewMap();
        List<Integer> elevations = new ArrayList<>(fullMap.keySet());
        Collections.sort(elevations);

        for (int z : elevations) {
            Map<AreaType, GridMap> viewSurfaceGridMap = viewMap.getViewMap().get(z);
            Map<AreaType, int[][]> storageSurfaceGridMap = FullMap.getViewMap().get(z);

            for (AreaType area : AreaType.values()) {

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

                        // if z below player.z then change offset. player.z = z => standard offset, increment higher aswell just in case
                        changeSQMOffsetByZ(z, viewSQM, START_Z_MAP);

                        if (visibilityUntilZ != null) {
                            setVisibility(viewSQM, z <= visibilityUntilZ);
                        }
                        viewSQM.updateSQM();

                    }
                }
            }
        }
    }

    public void adjustPlayerToTopLayerByElevation(Direction direction, User player) {
        Elevatable elv = fullMapManager.isElevatingSQM(player, direction);
        if (elv != null) {
            fullMapManager.moveToTopLayer(player);
        }

    }

    public void adjustPlayerToTopLayerByTerrain(Direction direction, User player) {
        BaseSQM stepOnbaseSQM = fullMapManager.isWalkableTerrain(player, direction);
        BaseSQM currentlyOnbaseSQM = fullMapManager.isWalkableTerrain(player);
        if (stepOnbaseSQM != null || currentlyOnbaseSQM != null) {
            fullMapManager.moveToTopLayer(player);
        }
    }

    public void adjustPlayerToTopLayerByTerrain(User player) {
        BaseSQM currentlyOnbaseSQM = fullMapManager.isWalkableTerrain(player);
        if (currentlyOnbaseSQM != null) {
            fullMapManager.moveToTopLayer(player);
        } else {
            fullMapManager.moveToBottomLayer(player);
        }
    }

    private void changeSQMOffsetByZ(int currentZ, BaseSQM viewSQM, int playerZ) {
        // if z below player.z then change offset. player.z = z => standard offset, increment higher as well just in case
        int OFFSET_PIXEL_Y = -170;
        int newOffSetY;

        if (currentZ < playerZ) {
            newOffSetY = ((playerZ - currentZ) * 60) + OFFSET_PIXEL_Y;
        } else if (currentZ == playerZ) {
            newOffSetY = OFFSET_PIXEL_Y;
        } else {
            newOffSetY = ((currentZ - playerZ) * 60) + OFFSET_PIXEL_Y;
        }
        viewSQM.setOFFSET_PIXEL_Y(newOffSetY);
    }

    private void setVisibility(BaseSQM viewSQM, boolean visibility) {
        viewSQM.setVisible(visibility);
    }

    public Map<Integer, Map<AreaType, GridMap>> getFullViewMap() {
        return viewMap.getViewMap();
    }

    public Map<AreaType, GridMap> getViewGridMapLayerLevel(int z) {
        return getFullViewMap().get(z);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch (evt.getPropertyName()) {
            case "changeElevation" -> updateView((Integer) evt.getNewValue());
            case "playerMoved", "playerVisibilityChange" -> updateView();
        }
    }
}

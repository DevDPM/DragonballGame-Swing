package nl.pokemon.game.client.model;

import nl.pokemon.game.domain.User;
import nl.pokemon.game.client.enums.AreaType;
import nl.pokemon.game.client.enums.Direction;
import nl.pokemon.game.core.model.Elevatable;
import nl.pokemon.game.core.model.MapCoordination;
import nl.pokemon.game.core.model.Tiles.BaseTile;
import nl.pokemon.game.core.model.Tiles.VoidTile;
import nl.pokemon.game.core.service.FullMapService;
import nl.pokemon.game.core.service.SQMService;
import nl.pokemon.game.core.service.Player;
import nl.pokemon.game.bootstrap.FullMap;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

@Service
public class GameScreen implements PropertyChangeListener {

    @Inject
    FullTileMap fullTileMap;

    @Inject
    VoidTile voidSQM;

    @Inject
    SQMService sqmService;

    @Inject
    Player player;

    @Inject
    FullMapService fullMapService;

    public void updateView() {
        updateView(null);
    }

    public void updateView(Integer visibilityUntilZ) {
        MapCoordination userPosition = player.getUserCoordination();
        int START_X_MAP = userPosition.getX() - ((TileMap.MAX_X - 1)/2);
        int START_Y_MAP = userPosition.getY() - ((TileMap.MAX_Y - 1)/2);
        int START_Z_MAP = userPosition.getZ();

        Map<Integer, Map<AreaType, TileMap>> fullMap = fullTileMap.getFullTileMap();
        List<Integer> elevations = new ArrayList<>(fullMap.keySet());
        Collections.sort(elevations);

        for (int z : elevations) {
            Map<AreaType, TileMap> viewSurfaceGridMap = fullTileMap.getFullTileMap().get(z);
            Map<AreaType, int[][]> storageSurfaceGridMap = FullMap.getViewMap().get(z);

            for (AreaType area : AreaType.values()) {

                BaseTile[][] viewGridMap = viewSurfaceGridMap.get(area).getTileMap();
                int[][] storageGridMap = storageSurfaceGridMap.get(area);

                for (int y = 0, intY = START_Y_MAP; y < TileMap.MAX_Y; y++, intY++) {
                    for (int x = 0, intX = START_X_MAP; x < TileMap.MAX_X; x++, intX++) {
                        BaseTile storedSQM;

                        if (intY < 0 || intX < 0 || intY >= FullMap.fullMapHeight() || intX >= FullMap.fullMapWidth()) {
                            storedSQM = voidSQM;
                        } else {
                            if (area.equals(AreaType.PLAYER_BOTTOM) || area.equals(AreaType.PLAYER_TOP)) {
                                if (storageGridMap[intY][intX] == 0) {
                                    storedSQM = sqmService.getSQMByIntAndAreaOrNull(area, storageGridMap[intY][intX]);
                                } else {
                                    storedSQM = player.getUserCharacter();
                                }
                            } else {
                                if (area == AreaType.LOWER_TERRAIN && intX == 35 && intY == 53){
                                }

                                storedSQM = sqmService.getSQMByIntAndAreaOrNull(area, storageGridMap[intY][intX]);
                            }
                        }
                        BaseTile viewSQM = viewGridMap[y][x];
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
        Elevatable elv = sqmService.isElevatingSQMOrNull(player, direction);
        if (elv != null) {
            fullMapService.moveToTopLayer(player);
        }

    }

    private void changeSQMOffsetByZ(int currentZ, BaseTile viewSQM, int playerZ) {
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

    private void setVisibility(BaseTile viewSQM, boolean visibility) {
        viewSQM.setVisible(visibility);
    }

    public Map<Integer, Map<AreaType, TileMap>> getFullViewMap() {
        return fullTileMap.getFullTileMap();
    }

    public Map<AreaType, TileMap> getViewGridMapLayerLevel(int z) {
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

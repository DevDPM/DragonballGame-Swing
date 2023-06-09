package nl.pokemon.game.model.core.service;

import nl.pokemon.game.bootstrap.TilesetImageContainer;
import nl.pokemon.game.model.client.GameScreen;
import nl.pokemon.game.model.core.model.MapCoordination;
import nl.pokemon.game.domain.User;
import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.core.model.tiles.BaseTile;
import nl.pokemon.game.model.core.model.dragonballs.DragonBallContainer;
import nl.pokemon.game.bootstrap.FullMap;
import nl.pokemon.game.view.gamePanel.Counter;
import org.dpmFramework.Kickstarter;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import java.beans.PropertyChangeSupport;
import java.util.Map;

@Service
public class FullMapService {

    private PropertyChangeSupport updateElevation;

    @Inject
    private TileService tileService;

    @Inject
    private PlayerService playerService;

    @Inject
    private DragonBallContainer dragonBallContainer;

    @Inject
    private Counter Counter;

    private void init() {
        this.updateElevation = new PropertyChangeSupport(Kickstarter.getInstanceOf(FullMapService.class));
        GameScreen gameScreen = Kickstarter.getInstanceOf(GameScreen.class);
        this.updateElevation.addPropertyChangeListener(gameScreen);
    }

    public BaseTile getBaseTileByPosition(MapCoordination mapCoordination) {
        int x = mapCoordination.getX();
        int y = mapCoordination.getY();
        int z = mapCoordination.getZ();
        AreaType areaType = mapCoordination.getAreaType();

        if (x < 0 || y < 0 || x >= FullMap.fullMapWidth() || y >= FullMap.fullMapHeight())
            return null;

        if (areaType.equals(AreaType.PLAYER_BOTTOM) || areaType.equals(AreaType.PLAYER_TOP)) {
            int userId = getFullMapGridInt(areaType, z)[y][x];
            if (userId == 0)
                return TilesetImageContainer.getTileByIntAndArea(areaType, userId);
            return playerService.getUserCharacter();
        }

        int sqmId = getFullMapGridInt(areaType, z)[y][x];
        return TilesetImageContainer.getTileByIntAndArea(areaType, sqmId);
    }

    public Map<Integer, Map<AreaType, int[][]>> getFullMap() {
        return FullMap.getViewMap();
    }

    public Map<AreaType, int[][]> getFullMapLayerLevel(int z) {
        return getFullMap().get(z);
    }

    public int[][] getFullMapGridInt(AreaType areaType, int z) {
        return getFullMapLayerLevel(z).get(areaType);
    }

    public void moveToTopLayer(User user) {
        MapCoordination mapCoordination = user.getMapCoordination();

        if (FullMap.erasePosition(mapCoordination)) {
            AreaType newAreaType = AreaType.PLAYER_TOP;
            mapCoordination.setAreaType(newAreaType);
            FullMap.updateUserPosition(user);
            updateElevation.firePropertyChange("playerVisibilityChange", null, null);
        }
    }

    public void setBaseSQMToPosition(MapCoordination mapCoordination, BaseTile sqm) {
        FullMap.setItemToPosition(mapCoordination, sqm);
    }

    public void updateUserPosition(MapCoordination oldPosition, User user) {
        if (FullMap.erasePosition(oldPosition)) {
            FullMap.updateUserPosition(user);
            String fireProperty;
            if (oldPosition.getZ() != user.getMapCoordination().getZ()) {
                fireProperty = "changeElevation";
            } else {
                fireProperty = "playerMoved";
            }
            updateElevation.firePropertyChange(fireProperty, null, user.getMapCoordination().getZ());
        }
    }

    public void setUserPosition(User user) {
        FullMap.updateUserPosition(user);
        String fireProperty = "changeElevation";
        updateElevation.firePropertyChange(fireProperty, null, user.getMapCoordination().getZ());
    }
}

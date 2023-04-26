package nl.pokemon.game.service;

import nl.pokemon.game.domain.User;
import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.Elevatable;
import nl.pokemon.game.model.MapCoordination;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.SQMs.ConvertToSQM;
import nl.pokemon.game.model.SQMs.ItemSQM;
import nl.pokemon.game.model.dragonballs.DragonBallContainer;
import nl.pokemon.game.util.FullMap;
import nl.pokemon.game.view.DragonBallCounter;
import org.dpmFramework.Kickstarter;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import java.beans.PropertyChangeSupport;
import java.util.Map;

@Service
public class FullMapManager {

    private PropertyChangeSupport updateElevation;

    @Inject
    private SQMService sqmService;

    @Inject
    private UserService userService;

    @Inject
    private DragonBallContainer dragonBallContainer;

    @Inject
    private DragonBallCounter dragonBallCounter;

    private void init() {
        this.updateElevation = new PropertyChangeSupport(Kickstarter.getInstanceOf(FullMapManager.class));
        ClientViewMap clientViewMap = Kickstarter.getInstanceOf(ClientViewMap.class);
        this.updateElevation.addPropertyChangeListener(clientViewMap);
    }

    public void moveUser(User user, Direction direction) {
        int userId = user.getId();
        MapCoordination mapCoordination = user.getMapCoordination();

        Elevatable elevation = sqmService.isElevatingSQMOrNull(user, direction);

        if (elevation != null) {
            if (FullMap.erasePosition(mapCoordination)) {
                mapCoordination.setX(mapCoordination.getX() + (2 * direction.getX()));
                mapCoordination.setY(mapCoordination.getY() + (2 * direction.getY()));
                mapCoordination.setZ(mapCoordination.getZ() + elevation.incrementElevationNumber());
                mapCoordination.setAreaType(AreaType.PLAYER_BOTTOM);
                updateElevation.firePropertyChange("changeElevation", null, mapCoordination.getZ());
                FullMap.setUserToPosition(mapCoordination, user);
            }
        } else if (sqmService.isWalkableSQM(user, direction)) {
            if (FullMap.erasePosition(mapCoordination)) {
                mapCoordination.setX(mapCoordination.getX() + direction.getX());
                mapCoordination.setY(mapCoordination.getY() + direction.getY());

                if (sqmService.isWalkableTerrainOrNull(user) == null) {
                    mapCoordination.setAreaType(AreaType.PLAYER_BOTTOM);
                }

                ItemSQM itemSQM = sqmService.isDragonBallSQMOrNull(user);
                if (itemSQM != null) {
                    System.out.println("invoked");
                    userService.addPoint(itemSQM.receivePoints());
                    MapCoordination DBPosition = dragonBallContainer.getCurrentDragonball().getMapCoordination();
                    FullMap.erasePosition(DBPosition);

                    dragonBallContainer.getNextDragonBall();
                    dragonBallCounter.addDragonBall(itemSQM);
                }
                updateElevation.firePropertyChange("playerMoved", null, null);
                FullMap.setUserToPosition(mapCoordination, user);
            }
        }
    }

    public BaseSQM getBaseSQMByPosition(MapCoordination mapCoordination) {
        int x = mapCoordination.getX();
        int y = mapCoordination.getY();
        int z = mapCoordination.getZ();
        AreaType areaType = mapCoordination.getAreaType();

        if (x < 0 || y < 0 || x >= FullMap.fullMapWidth() || y >= FullMap.fullMapHeight())
            return null;

        if (areaType.equals(AreaType.PLAYER_BOTTOM) || areaType.equals(AreaType.PLAYER_TOP)) {
            int userId = getFullMapGridInt(areaType, z)[y][x];
            if (userId == 0)
                return ConvertToSQM.getSQM(areaType, userId);
            return userService.getUserCharacter();
        }

        int sqmId = getFullMapGridInt(areaType, z)[y][x];
        return ConvertToSQM.getSQM(areaType, sqmId);
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
            FullMap.setUserToPosition(mapCoordination, user);
            updateElevation.firePropertyChange("playerVisibilityChange", null, null);
        }
    }

    public void moveToBottomLayer(User user) {
        MapCoordination mapCoordination = user.getMapCoordination();

        if (FullMap.erasePosition(mapCoordination)) {
            AreaType newAreaType = AreaType.PLAYER_BOTTOM;
            FullMap.setUserToPosition(mapCoordination, user);
            updateElevation.firePropertyChange("playerVisibilityChange", null, null);
        }
    }

    public void setBaseSQMToPosition(MapCoordination mapCoordination, BaseSQM sqm) {
        FullMap.setItemToPosition(mapCoordination, sqm);
    }
}

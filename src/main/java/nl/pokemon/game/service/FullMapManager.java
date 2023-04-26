package nl.pokemon.game.service;

import nl.pokemon.game.domain.User;
import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.Elevatable;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.SQMs.ConvertToSQM;
import nl.pokemon.game.model.SQMs.ItemSQM;
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
    SQMService sqmService;

    @Inject
    private PlayerService playerService;

    @Inject
    DragonBallService dragonBallService;

    @Inject
    DragonBallCounter dragonBallCounter;

    private void init() {
        this.updateElevation = new PropertyChangeSupport(Kickstarter.getInstanceOf(FullMapManager.class));
        ClientViewMap clientViewMap = Kickstarter.getInstanceOf(ClientViewMap.class);
        this.updateElevation.addPropertyChangeListener(clientViewMap);
    }

    public boolean moveUser(User user, Direction direction) {
        int userId = user.getId();
        int currentX = user.getX();
        int currentY = user.getY();
        int currentZ = user.getZ();
        AreaType areaType = user.getAreaType();

        Elevatable elevation = sqmService.isElevatingSQM(user, direction);

        if (elevation != null) {
            if (FullMap.setNewValueToPosition(areaType, currentX, currentY, currentZ, 0)) {
                int newX = currentX + (2 * direction.getX());
                int newY = currentY + (2 * direction.getY());
                int newZ = currentZ + elevation.incrementElevationNumber();
                playerService.setNewPosition(user, newX, newY, newZ, AreaType.PLAYER_BOTTOM);
                updateElevation.firePropertyChange("changeElevation", currentZ, newZ);
                return FullMap.setNewValueToPosition(user.getAreaType(), newX, newY, newZ, userId);
            }
        } else if (sqmService.isWalkableSQM(user, direction)) {
            if (FullMap.setNewValueToPosition(areaType, currentX, currentY, currentZ, 0)) {
                int newX = currentX + direction.getX();
                int newY = currentY + direction.getY();
                if (sqmService.isWalkableTerrain(user) == null) {
                    areaType = AreaType.PLAYER_BOTTOM;
                }
                playerService.setNewPosition(user, newX, newY, currentZ, areaType);
                ItemSQM itemSQM = sqmService.stepOnDragonBall();
                if (itemSQM != null) {
                    playerService.addPoint(itemSQM.receivePoints());
                    FullMap.setNewValueToPosition(AreaType.TERRAIN, newX, newY, currentZ, 0);
                    dragonBallService.generateLocationForDragonBall();
                    dragonBallCounter.addDragonBall(itemSQM);
                }
                updateElevation.firePropertyChange("playerMoved", null, null);
                return FullMap.setNewValueToPosition(areaType, newX, newY, currentZ, userId);
            }
        }
        return false;
    }

    public BaseSQM getBaseSQMByPosition(AreaType areaType, int x, int y, int z) {
        if (x < 0 || y < 0 || x >= FullMap.fullMapWidth() || y >= FullMap.fullMapHeight())
            return null;

        if (areaType.equals(AreaType.PLAYER_BOTTOM) || areaType.equals(AreaType.PLAYER_TOP)) {
            int userId = getFullMapGridInt(areaType, z)[y][x];
            if (userId == 0)
                return ConvertToSQM.getSQM(areaType, userId);
            return playerService.getPlayerSQMByUserId(userId);
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
        int userId = user.getId();
        int currentX = user.getX();
        int currentY = user.getY();
        int currentZ = user.getZ();

        if (FullMap.setNewValueToPosition(user.getAreaType(), currentX, currentY, currentZ, 0)) {
            AreaType newAreaType = AreaType.PLAYER_TOP;
            playerService.setPlayerArea(newAreaType);
            FullMap.setNewValueToPosition(newAreaType, currentX, currentY, currentZ, userId);
            updateElevation.firePropertyChange("playerVisibilityChange", null, null);
        }
    }

    public void moveToBottomLayer(User user) {
        int userId = user.getId();
        int currentX = user.getX();
        int currentY = user.getY();
        int currentZ = user.getZ();

        if (FullMap.setNewValueToPosition(user.getAreaType(), currentX, currentY, currentZ, 0)) {
            AreaType newAreaType = AreaType.PLAYER_BOTTOM;
            playerService.setPlayerArea(newAreaType);
            FullMap.setNewValueToPosition(newAreaType, currentX, currentY, currentZ, userId);
            updateElevation.firePropertyChange("playerVisibilityChange", null, null);
        }
    }

    public void setBaseSQMToPosition(AreaType area, int x, int y, int z, BaseSQM sqm) {
        FullMap.setNewValueToPosition(area, x, y, z, sqm.getSqmId());               //////////////
    }
}

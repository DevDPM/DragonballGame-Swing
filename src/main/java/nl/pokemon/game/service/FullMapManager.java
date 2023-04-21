package nl.pokemon.game.service;

import nl.pokemon.game.domain.User;
import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.Elevatable;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.SQMs.ConvertToSQM;
import nl.pokemon.game.model.players.Ash;
import nl.pokemon.game.util.FullMap;
import org.dpmFramework.Kickstarter;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import java.beans.PropertyChangeSupport;
import java.util.Map;

@Service
public class FullMapManager {

    private PropertyChangeSupport updateElevation;

    @Inject
    private PlayerService playerService;

    private void init() {
        this.updateElevation = new PropertyChangeSupport(Kickstarter.getInstanceOf(FullMapManager.class));
        ClientViewMap clientViewMap = Kickstarter.getInstanceOf(ClientViewMap.class);
        this.updateElevation.addPropertyChangeListener(clientViewMap);
    }

    public boolean moveUserByDirection(User user, Direction direction) {
        int userId = user.getId();
        int currentX = user.getX();
        int currentY = user.getY();
        int currentZ = user.getZ();
        AreaType areaType = user.getAreaType();

        Elevatable elevation = isElevatingSQM(user, direction);

        if (elevation != null) {
            // make elevation
            if (FullMap.setNewValueToPosition(areaType, currentX, currentY, currentZ, 0)) {
                int newX = currentX + (2 * direction.getX());
                int newY = currentY + (2 * direction.getY());
                int newZ = currentZ + elevation.getElevateZ();

                playerService.setNewPosition(user, newX, newY, newZ, AreaType.PLAYER_BOTTOM);

                updateElevation.firePropertyChange("changeElevation", currentZ, newZ);
                return FullMap.setNewValueToPosition(AreaType.PLAYER_BOTTOM, newX, newY, newZ, userId);
            }
        } else if (isWalkableSQM(user, direction)) {
            if (FullMap.setNewValueToPosition(areaType, currentX, currentY, currentZ, 0)) {
                int newX = currentX + direction.getX();
                int newY = currentY + direction.getY();

                playerService.setNewPosition(user, newX, newY, currentZ, AreaType.PLAYER_BOTTOM);
                updateElevation.firePropertyChange("playerMoved", null, null);
                return FullMap.setNewValueToPosition(user.getAreaType(), newX, newY, currentZ, userId);
            }
        }
        return false;
    }

    public Elevatable isElevatingSQM(User user, Direction direction) {
        return isElevatingSQM(user.getX() + direction.getX(), user.getY() + direction.getY(), user.getZ());
    }

    private Elevatable isElevatingSQM(int x, int y, int z) {
        if (x < 0 || y < 0 || x >= FullMap.fullMapWidth() || y >= FullMap.fullMapHeight())
            return null;

        for (AreaType areaType : AreaType.values()) {
            BaseSQM baseSQM = getBaseSQMByPosition(areaType, x, y, z);
            if (baseSQM instanceof Elevatable elevation)
                return elevation;
        }
        return null;
    }

    public boolean isWalkableSQM(User user, Direction direction) {
        return isWalkableSQM(user.getX() + direction.getX(), user.getY() + direction.getY(), user.getZ());
    }

    private boolean isWalkableSQM(int x, int y, int z) {
        if (x < 0 || y < 0 || x >= FullMap.fullMapWidth() || y >= FullMap.fullMapHeight())
            return false;

        for (AreaType areaType : AreaType.values()) {
            BaseSQM baseSQM = getBaseSQMByPosition(areaType, x, y, z);

            if (baseSQM.isNotWalkable())
                return false;
        }
        return true;
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
}

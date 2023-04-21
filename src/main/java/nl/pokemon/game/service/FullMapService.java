package nl.pokemon.game.service;

import nl.pokemon.game.domain.User;
import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.Elevatable;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.SQMs.ConvertToSQM;
import nl.pokemon.game.util.FullMap;
import org.dpmFramework.Kickstarter;
import org.dpmFramework.annotation.Service;

import java.beans.PropertyChangeSupport;
import java.util.Map;

@Service
public class FullMapService {

    private PropertyChangeSupport updateElevation;

    private void init() {
        this.updateElevation = new PropertyChangeSupport(Kickstarter.getInstanceOf(FullMapService.class));
    }

    public boolean moveUserByDirection(User user, Direction direction) {
        int userId = user.getId();
        int x = user.getX();
        int y = user.getY();
        int z = user.getZ();

        for (AreaType areaType : AreaType.values()) {
            BaseSQM walkToSQM = getBaseSQMByPosition(areaType, x + direction.getX(), y + direction.getY(), z);

            if (walkToSQM == null) {
                // unable to walk on void
                return false;

            } else if (walkToSQM instanceof Elevatable elevation) {
                // make elevation
                if (FullMap.setNewValueToPosition(areaType, x, y, z, 0)) {
                    x = x + (2 * direction.getX());
                    y = y + (2 * direction.getY());
                    z = z + elevation.getElevateZ();
                    updateElevation.firePropertyChange();
                    return FullMap.setNewValueToPosition(areaType, x, y, z, userId);
                } else {
                    throw new RuntimeException("Could not erase last position during elevation in: " + this.getClass());
                }
            } else if (!walkToSQM.isNotWalkable()) {
                // make regular walking
                if (FullMap.setNewValueToPosition(areaType, x, y, z, 0)) {
                    x = x + direction.getX();
                    y = y + direction.getY();
                    return FullMap.setNewValueToPosition(areaType, x, y, z, userId);
                } else {
                    throw new RuntimeException("Could not erase last position during walking in: " + this.getClass());
                }
            } else {
                System.out.println("unexpected outcome at: " + this.getClass());
                return false;
            }
        }
        return false;
    }

    public BaseSQM getBaseSQMByPosition(AreaType areaType, int x, int y, int z) {
        if (x < 0 || y < 0 || x >= FullMap.fullMapWidth() || y >= FullMap.fullMapHeight())
            return null;

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
}

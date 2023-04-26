package nl.pokemon.game.service;

import nl.pokemon.game.domain.User;
import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.Elevatable;
import nl.pokemon.game.model.MapCoordination;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.SQMs.ItemSQM;
import nl.pokemon.game.model.SQMs.LowTerrainSQM;
import nl.pokemon.game.util.FullMap;
import nl.pokemon.game.util.TilesetImageContainer;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

@Service
public class SQMService {

    @Inject
    FullMapManager fullMapManager;

    public ItemSQM isDragonBallSQMOrNull(User user) {
        MapCoordination mapCoordination = user.getMapCoordination();
        MapCoordination newMapCoordination = new MapCoordination(mapCoordination.getX(), mapCoordination.getY(), mapCoordination.getZ(), AreaType.TERRAIN);

        BaseSQM baseSQM = fullMapManager.getBaseSQMByPosition(newMapCoordination);
        if (baseSQM instanceof ItemSQM item) {
            return item;
        } else {
            return null;
        }
    }

    public BaseSQM getSQMByIntAndAreaOrNull(AreaType area, int sqmId) {
        return TilesetImageContainer.getSQMByIntAndArea(area, sqmId);
    }

    public BaseSQM isWalkableTerrainOrNull(User user) {
        return isWalkableTerrainOrNull(user.getMapCoordination());
    }

    public BaseSQM isWalkableTerrainOrNull(User user, Direction direction) {
        return isWalkableTerrainOrNull(getMapCoordinationByDirection(user, direction));
    }

    private BaseSQM isWalkableTerrainOrNull(MapCoordination mapCoordination) {
        if (validateMapOutOfRange(mapCoordination))
            return null;

        MapCoordination newMapCoordination = new MapCoordination(mapCoordination.getX(), mapCoordination.getY(), mapCoordination.getZ(), AreaType.TERRAIN);
        BaseSQM baseSQM = fullMapManager.getBaseSQMByPosition(newMapCoordination);
        if (baseSQM instanceof LowTerrainSQM && !baseSQM.isNotWalkable()) {
            return baseSQM;
        }
        return null;
    }

    public Elevatable isElevatingSQMOrNull(User user, Direction direction) {
        return isElevatingSQMOrNull(getMapCoordinationByDirection(user, direction));
    }

    private Elevatable isElevatingSQMOrNull(MapCoordination mapCoordination) {
        if (validateMapOutOfRange(mapCoordination))
            return null;

        for (AreaType areaType : AreaType.values()) {
            mapCoordination.setAreaType(areaType);
            BaseSQM baseSQM = fullMapManager.getBaseSQMByPosition(mapCoordination);
            if (baseSQM instanceof Elevatable elevation)
                return elevation;
        }
        return null;
    }

    public boolean isWalkableSQM(User user, Direction direction) {
        return isWalkableSQM(getMapCoordinationByDirection(user, direction));
    }

    private boolean isWalkableSQM(MapCoordination mapCoordination) {
        int x = mapCoordination.getX();
        int y = mapCoordination.getY();

        if (validateMapOutOfRange(mapCoordination))
            return false;

        for (AreaType areaType : AreaType.values()) {
            mapCoordination.setAreaType(areaType);
            BaseSQM baseSQM = fullMapManager.getBaseSQMByPosition(mapCoordination);

            if (baseSQM.isNotWalkable())
                return false;
        }
        return true;
    }

    private boolean validateMapOutOfRange(MapCoordination mapCoordination) {
        int x = mapCoordination.getX();
        int y = mapCoordination.getY();
        if (x < 0 || y < 0 || x >= FullMap.fullMapWidth() || y >= FullMap.fullMapHeight())
            return true;
        return false;
    }

    private MapCoordination getMapCoordinationByDirection(User user, Direction direction) {
        MapCoordination mapCoordination = user.getMapCoordination();
        int x = mapCoordination.getX();
        int y = mapCoordination.getY();
        int z = mapCoordination.getZ();

        if (direction != null) {
            x += direction.getX();
            y += direction.getY();
        }
        return new MapCoordination(x, y, z, mapCoordination.getAreaType());
    }
}

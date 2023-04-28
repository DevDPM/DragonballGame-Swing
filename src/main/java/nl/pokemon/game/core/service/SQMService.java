package nl.pokemon.game.core.service;

import nl.pokemon.game.domain.User;
import nl.pokemon.game.client.enums.AreaType;
import nl.pokemon.game.client.enums.Direction;
import nl.pokemon.game.core.model.Elevatable;
import nl.pokemon.game.core.model.MapCoordination;
import nl.pokemon.game.core.model.Tiles.BaseTile;
import nl.pokemon.game.core.model.Tiles.ItemTile;
import nl.pokemon.game.core.model.Tiles.LowTerrainTile;
import nl.pokemon.game.bootstrap.FullMap;
import nl.pokemon.game.bootstrap.TilesetImageContainer;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

@Service
public class SQMService {

    @Inject
    private FullMapManager fullMapManager;

    public ItemTile isDragonBallSQMOrNull(User user) {
        MapCoordination mapCoordination = user.getMapCoordination();
        MapCoordination newMapCoordination = new MapCoordination(mapCoordination.getX(), mapCoordination.getY(), mapCoordination.getZ(), AreaType.LOWER_TERRAIN);

        BaseTile baseTile = fullMapManager.getBaseSQMByPosition(newMapCoordination);
        if (baseTile instanceof ItemTile item) {
            return item;
        } else {
            return null;
        }
    }

    public BaseTile getSQMByIntAndAreaOrNull(AreaType area, int sqmId) {
        return TilesetImageContainer.getSQMByIntAndArea(area, sqmId);
    }

    public BaseTile isWalkableTerrainOrNull(User user) {
        return isWalkableTerrainOrNull(user.getMapCoordination());
    }

    public BaseTile isWalkableTerrainOrNull(User user, Direction direction) {
        return isWalkableTerrainOrNull(getMapCoordinationByDirection(user, direction));
    }

    private BaseTile isWalkableTerrainOrNull(MapCoordination mapCoordination) {
        if (validateMapOutOfRange(mapCoordination))
            return null;

        MapCoordination newMapCoordination = new MapCoordination(mapCoordination.getX(), mapCoordination.getY(), mapCoordination.getZ(), AreaType.HIGHER_TERRAIN);
        BaseTile baseTile = fullMapManager.getBaseSQMByPosition(newMapCoordination);
        if (baseTile instanceof LowTerrainTile && !baseTile.isNotWalkable()) {
            return baseTile;
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
            BaseTile baseTile = fullMapManager.getBaseSQMByPosition(mapCoordination);
            if (baseTile instanceof Elevatable elevation)
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
            BaseTile baseTile = fullMapManager.getBaseSQMByPosition(mapCoordination);

            if (baseTile.isNotWalkable())
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
